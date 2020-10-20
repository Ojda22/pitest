package org.pitest.mutationtest.execute;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

import org.pitest.functional.FCollection;
import org.pitest.mutationtest.ClassMutationResults;
import org.pitest.mutationtest.MutationMetaData;
import org.pitest.mutationtest.MutationResultListener;
import org.pitest.mutationtest.build.MutationAnalysisUnit;
import org.pitest.rewriter.Serializer;
import org.pitest.util.Log;
import org.pitest.util.Unchecked;

public class MutationAnalysisExecutor {

  private static final Logger                LOG = Log.getLogger();

  private final List<MutationResultListener> listeners;
  private final ThreadPoolExecutor           executor;

  private final Semaphore sem;

  public MutationAnalysisExecutor(int numberOfThreads,
      List<MutationResultListener> listeners) {
    this.listeners = listeners;
    this.executor = new ThreadPoolExecutor(numberOfThreads, numberOfThreads,
        10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
        Executors.defaultThreadFactory());
    this.sem = new Semaphore(numberOfThreads * 3);
  }

  public void preRun() {
    signalRunStartToAllListeners();
  }

  public void postRun() {
    try {
      this.executor.shutdown();
      this.executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException e) {
      throw Unchecked.translateCheckedException(e);
    }
    signalRunEndToAllListeners();
  }

  // entry point for mutation testing
  public void run(final List<MutationAnalysisUnit> testUnits) {

    for (final MutationAnalysisUnit unit : testUnits) {
      try {
        sem.acquire();
        executor.execute(() -> {
          try {
            MutationMetaData r = unit.call();
            processResult(r);
          } catch (Exception e) {
            throw new CompletionException(e);
          } finally {
            sem.release();
          }
        });
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void processResult(MutationMetaData r)
      throws InterruptedException, ExecutionException {
    for (final MutationResultListener l : this.listeners) {
      for (final ClassMutationResults cr : r.toClassResults()) {
        l.handleMutationResult(cr);
      }
    }
  }

  private void signalRunStartToAllListeners() {
    FCollection.forEach(this.listeners,
        a -> a.runStart());
    Serializer.closeOrOpenMutationFile();
  }

  private void signalRunEndToAllListeners() {
    FCollection.forEach(this.listeners,
        a -> a.runEnd());
    Serializer.closeOrOpenMutationFile();
  }

}

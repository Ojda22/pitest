package ie.ucd.pel.pitestHOM.pitestHOM;

import org.pitest.classinfo.ClassName;
import org.pitest.coverage.TestInfo;
import org.pitest.functional.FCollection;
import org.pitest.mutationtest.build.MutationAnalysisUnit;
import org.pitest.mutationtest.build.MutationGrouper;
import org.pitest.mutationtest.build.MutationInterceptor;
import org.pitest.mutationtest.build.MutationTestUnit;
import org.pitest.mutationtest.build.TestPrioritiser;
import org.pitest.mutationtest.build.WorkerFactory;
import org.pitest.mutationtest.engine.Location;
import org.pitest.mutationtest.engine.Mutater;
import org.pitest.mutationtest.engine.MutationDetails;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.PoisonStatus;
import org.pitest.mutationtest.execute.MutationAnalysisExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.function.Function;


public class PitestHOMUtilities {

    private final MutationAnalysisExecutor mae;
    private final WorkerFactory wf;
    private final TestPrioritiser testPrioritiser;
    private final MutationInterceptor interceptor;
    private final Mutater mutater;
    private final MutationGrouper grouper;
    private final Collection<ClassName> codeClasses;

    public PitestHOMUtilities(final MutationAnalysisExecutor mae, final WorkerFactory wf,
        final TestPrioritiser testPrioritiser, final MutationInterceptor interceptor, final Mutater mutater,
        MutationGrouper grouper, Collection<ClassName> codeClasses) {
      this.mae = mae;
      this.wf = wf;
      this.testPrioritiser = testPrioritiser;
      this.interceptor = interceptor;
      this.mutater = mutater;
      this.grouper = grouper;
      this.codeClasses = codeClasses;
    }

    /**
     * Creates a nth order mutant from n first order mutants from a same class
     */
    private static MutationDetails combineMutants(MutationDetails... details) {
        Collection<Location> locations = new ArrayList<Location>();
        Collection<List<Integer>> indexesList = new ArrayList<List<Integer>>();
        Collection<String> mutatorsUniqueIds = new ArrayList<String>();
        String description = "Mutant of order : " + details.length;

        String filename = null;
        Set<Integer> lineNumbers = new HashSet<>();
        Set<Integer> blocks = new HashSet<>();

        Boolean isInFinallyBlock = false;
        Boolean poison = false;

        for (MutationDetails detail : details) {
            locations.addAll(detail.getId().getLocations());
            indexesList.addAll(detail.getId().getIndexesList());
            mutatorsUniqueIds.addAll(detail.getId().getMutators());

            if (filename == null) {
                filename = detail.getFilename();
            }
            lineNumbers.addAll(detail.getLineNumbers());
            blocks.addAll(detail.getBlocks());
            isInFinallyBlock |= detail.isInFinallyBlock();
            poison |= detail.mayPoisonJVM();
        }

        PoisonStatus poisonStatus;
        if (poison) {
            poisonStatus = PoisonStatus.MAY_POISON_JVM;
        } else {
            poisonStatus = PoisonStatus.NORMAL;
        }

        MutationIdentifier id = new MutationIdentifier(locations, indexesList, mutatorsUniqueIds);


        MutationDetails result = new MutationDetails(id, filename, description, lineNumbers,
                blocks, isInFinallyBlock, poisonStatus);
        return result;
    }

    private void runSubsetsNR(List<MutationDetails> superSet, int order) {
      int size = superSet.size();
      if (order < 0 || order > size) {
        return;
      }
      int[] indices = new int[order];
      for (int i = 0; i < order; i++) {
        indices[i] = i;
      }
      MutationDetails[] toCombine = new MutationDetails[order];
      Collection<MutationDetails> toRun = new ArrayList<MutationDetails>();
      while (true) {
        for (int i = 0; i < order; i++) {
          toCombine[i] = superSet.get(indices[i]);
        }
        if (noOverlap(Arrays.asList(toCombine))) {
          MutationDetails detail = combineMutants(toCombine);
          final List<TestInfo> testDetails = this.testPrioritiser.assignTests(detail);
          detail.addTestsInOrder(testDetails);
          toRun.add(detail);
          if (toRun.size() > 10000) {
            interceptor.intercept(toRun, this.mutater);
            final List<MutationAnalysisUnit> tus = new ArrayList<>();
            for (final Collection<MutationDetails> ms : this.grouper.groupMutations(
                this.codeClasses, toRun)) {
              tus.add(makeUnanalysedUnit(ms));
            }
            toRun.clear();
            this.mae.run(tus);
          }
        }
        int r = order - 1;
        for (int m = size; r >= 0 && indices[r] == --m; r--) { }
        if (r == -1) {
          interceptor.intercept(toRun, this.mutater);
          final List<MutationAnalysisUnit> tus = new ArrayList<>();
          for (final Collection<MutationDetails> ms : this.grouper.groupMutations(
              this.codeClasses, toRun)) {
            tus.add(makeUnanalysedUnit(ms));
          }
          toRun.clear();
          this.mae.run(tus);
          return;
        }
        for (int c = indices[r]; r < order;) {
          indices[r++] = ++c;
        }
      }
    }

    private void runSubsets(List<MutationDetails> superSet, int n, int idx, Collection<MutationDetails> current,
                            Collection<MutationDetails> toRun) {
        //successful stop clause
        if (current.size() == n) {
            if (noOverlap(current)) {
                MutationDetails detail = combineMutants(current.toArray(new MutationDetails[0]));
                final List<TestInfo> testDetails = this.testPrioritiser
                        .assignTests(detail);
                detail.addTestsInOrder(testDetails);
                //List<ClassName> testNames = FCollection.map(testDetails,
                        //TestInfo.toDefiningClassName());
                //List<MutationDetails> d = Collections.singletonList(detail);
                //interceptor.intercept(d, mutater);
                //mae.run(Collections.singletonList((MutationAnalysisUnit)new MutationTestUnit(d, testNames, wf)));
                toRun.add(detail);
                if (toRun.size() > 10000) {
                  interceptor.intercept(toRun, this.mutater);
                  final List<MutationAnalysisUnit> tus = new ArrayList<>();
                  for (final Collection<MutationDetails> ms : this.grouper.groupMutations(
                    this.codeClasses, toRun)) {
                    tus.add(makeUnanalysedUnit(ms));
                  }
                  toRun.clear();
                  this.mae.run(tus);
                }
            }
            return;
        }
        //unsuccessful stop clause
        if (idx == superSet.size()) {
            return;
        }
        MutationDetails x = superSet.get(idx);
        current.add(x);
        //"guess" x is in the subset
        runSubsets(superSet, n, idx + 1, current, toRun);
        current.remove(x);
        //"guess" x is not in the subset
        runSubsets(superSet, n, idx + 1, current, toRun);
    }

    /**
     * Generates and runs mutants up to order n
     */
    public void runMutantsOfOrder(List<MutationDetails> fom, Collection<Integer> orders) {
        if (!orders.isEmpty() && ! fom.isEmpty()) {
          for (Integer i : orders) {
            if (i != 1) {
              //runSubsets(fom, i, 0, new HashSet<MutationDetails>(), new ArrayList<MutationDetails>());
              runSubsetsNR(fom,i);
            }
          }
        }
    }


    private static boolean noOverlap(Collection<MutationDetails> mutants) {
        List<Integer> l = FCollection.map(mutants, (a) -> a.getFirstIndex());
        Set<Integer> s = new HashSet<>(l);

        return l.size() == s.size();
    }

    public static Collection<MutationDetails> createMutantsOfOrder(List<MutationDetails> fom, int n) {
        List<MutationDetails> res = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            createSubsets(fom, i, 0, new HashSet<MutationDetails>(), res);
        }
        return res;
    }

    public static void createSubsets(List<MutationDetails> superSet, int n, int idx,
                                     Collection<MutationDetails> current, Collection<MutationDetails> res) {
        //successful stop clause
        if (current.size() == n) {
            if (noOverlap(current)) {
                res.add( combineMutants(current.toArray(new MutationDetails[0])));
            }
            return;
        }
        //unsuccessful stop clause
        if (idx == superSet.size()) {
            return;
        }
        MutationDetails x = superSet.get(idx);
        current.add(x);
        //"guess" x is in the subset
        createSubsets(superSet, n, idx + 1, current, res);
        current.remove(x);
        //"guess" x is not in the subset
        createSubsets(superSet, n, idx + 1, current, res);
    }

    public MutationAnalysisUnit makeUnanalysedUnit(
      final Collection<MutationDetails> needAnalysis) {
      final Set<ClassName> uniqueTestClasses = new HashSet<>();
      FCollection.flatMapTo(needAnalysis, mutationDetailsToTestClass(),
          uniqueTestClasses);

      return new MutationTestUnit(needAnalysis, uniqueTestClasses,
          this.wf);
    }

    public static Function<MutationDetails, Iterable<ClassName>> mutationDetailsToTestClass() {
      return a -> FCollection.map(a.getTestsInOrder(),
          TestInfo.toDefiningClassName());
    }
}

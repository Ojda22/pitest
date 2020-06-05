package ie.ucd.csl.pitestHOM.pitestHOM;

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
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        List<Integer> lineNumbers = new ArrayList<>();
        List<Integer> blocks = new ArrayList<>();

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

    private void runMutantsOfOrderBatchStream(List<MutationDetails> superSet, int order) {
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

    private void runMutantsOfOrderStream(List<MutationDetails> superSet, int order) {
        int size = superSet.size();
        if (order < 0 || order > size) {
            return;
        }
        int[] indices = new int[order];
        for (int i = 0; i < order; i++) {
            indices[i] = i;
        }
        MutationDetails[] toCombine = new MutationDetails[order];
        while (true) {
            for (int i = 0; i < order; i++) {
                toCombine[i] = superSet.get(indices[i]);
            }
            if (noOverlap(Arrays.asList(toCombine))) {
                MutationDetails detail = combineMutants(toCombine);
                final List<TestInfo> testDetails = this.testPrioritiser.assignTests(detail);
                detail.addTestsInOrder(testDetails);
                List<MutationDetails> l = Collections.singletonList(detail);
                interceptor.intercept(l, this.mutater);
                final List<MutationAnalysisUnit> tus = new ArrayList<>();
                if (!l.isEmpty()) {
                    for (final Collection<MutationDetails> ms : this.grouper.groupMutations(
                            this.codeClasses, l)) {
                        tus.add(makeUnanalysedUnit(ms));
                    }
                    this.mae.run(tus);
                }
            }
            int r = order - 1;
            for (int m = size; r >= 0 && indices[r] == --m; r--) { }
            if (r == -1) {
                return;
            }
            for (int c = indices[r]; r < order;) {
                indices[r++] = ++c;
            }
        }
    }

    public Collection<MutationDetails> combineMutants(List<MutationDetails> mutantsOnChange, List<MutationDetails> mutantsAroundChange, List<Integer> changedLines, boolean outerBehaviour){
        Collection<MutationDetails> combinedHOM = new ArrayList<>();

        for (MutationDetails mutantAround : mutantsAroundChange) {
                if(changedLines.contains(mutantAround.getLineNumber())){
                    continue;
                }
            // here its going checking for line number
            // because if a mutant (scope all) is on the changed line -> it should not have mapping with inner mutants
            for (MutationDetails mutantOn : mutantsOnChange) {
                Collection<Location> locations = new ArrayList<>();
                locations.addAll(mutantAround.getId().getLocations());
                locations.addAll(mutantOn.getId().getLocations());

                Collection<List<Integer>> indexesList = new ArrayList<>();
                indexesList.addAll(mutantAround.getId().getIndexesList());
                indexesList.addAll(mutantOn.getId().getIndexesList());

                Collection<String> mutatorsUniqueIDs = new ArrayList<>();
                mutatorsUniqueIDs.addAll(mutantAround.getId().getMutators());
                mutatorsUniqueIDs.addAll(mutantOn.getId().getMutators());

                Collection<Integer> blocks = new ArrayList<>();
                blocks.addAll(mutantAround.getBlocks());
                blocks.addAll(mutantOn.getBlocks());

                Collection<Integer> lineNumbers = new ArrayList<>();
                lineNumbers.addAll(mutantAround.getLineNumbers());
                lineNumbers.addAll(mutantOn.getLineNumbers());
//                String description = "Lines: " + lineNumbers.toArray()[0] + " - " + lineNumbers.toArray()[1];

                boolean isInFinallyBlock = mutantAround.isInFinallyBlock() || mutantOn.isInFinallyBlock();
                String fileName = mutantAround.getFilename();

                PoisonStatus poisonStatus;
                if (mutantAround.mayPoisonJVM() || mutantOn.mayPoisonJVM()){
                    poisonStatus = PoisonStatus.MAY_POISON_JVM;
                }else {
                    poisonStatus = PoisonStatus.NORMAL;
                }

                List<TestInfo> testsForMutantOnLine = this.testPrioritiser.assignTests(mutantOn);
                String description = testsForMutantOnLine.stream().map(a -> a.getName()).collect(Collectors.joining("|"));

                MutationIdentifier id = new MutationIdentifier(locations, indexesList, mutatorsUniqueIDs);

                MutationDetails combinedMutant = new MutationDetails(id, fileName, description, lineNumbers, blocks, isInFinallyBlock, poisonStatus);

                if(outerBehaviour){
                    combinedMutant.addTestsInOrder(testsForMutantOnLine);
                }else{
                    List<TestInfo> testsForMutantAroundLine = this.testPrioritiser.assignTests(mutantAround);

                    List<TestInfo> testsUnion = union(testsForMutantOnLine, testsForMutantAroundLine);

                    combinedMutant.addTestsInOrder(testsUnion);
                }

                combinedHOM.add(combinedMutant);
            }
        }

        interceptor.intercept(combinedHOM, this.mutater);

        return combinedHOM;
    }

    public <T> List<T> union(List<T> testsForMutantOnLine, List<T> testsForMutantAroundLine){
        // computationaly expensive O(n^2)
//        return Stream.concat(testsForMutantOnLine.stream(),testsForMutantAroundLine.stream()).distinct().collect(Collectors.toList())
        Set<T> set = new HashSet<T>();
        set.addAll(testsForMutantAroundLine);
        set.addAll(testsForMutantOnLine);
        return new ArrayList<T>(set);
    }

    public <T> List<T> intersection(List<T> testsForMutantOnLine, List<T> testsForMutantAroundLine){
//        return testsForMutantAroundLine.stream().filter(testsForMutantOnLine::contains).collect(Collectors.toList());
        List<T> list = new ArrayList<T>();
        for(T t: testsForMutantAroundLine){
            if(testsForMutantOnLine.contains(t)){
                list.add(t);
            }
        }
        return list;
    }

    public List<MutationDetails> extractFromChangedLines(List<MutationDetails> foms, List<Integer> lines){

        List<MutationDetails> mutantsOnLines = foms.stream().filter(m -> lines.contains(m.getLineNumbers().get(0))).collect(Collectors.toList());

        return mutantsOnLines;
    }

    private Collection<MutationDetails> makeMutantsOfOrder(List<MutationDetails> superSet, int order) {
        Collection<MutationDetails> result = new ArrayList<>();
        int size = superSet.size();
        if (order < 0 || order > size) {
            return result;
        }
        int[] indices = new int[order];
        for (int i = 0; i < order; i++) {
            indices[i] = i;
        }
        MutationDetails[] toCombine = new MutationDetails[order];
        while (true) {
            for (int i = 0; i < order; i++) {
                toCombine[i] = superSet.get(indices[i]);
            }
            if (noOverlap(Arrays.asList(toCombine))) {
                MutationDetails detail = combineMutants(toCombine);
                final List<TestInfo> testDetails = this.testPrioritiser.assignTests(detail);
                detail.addTestsInOrder(testDetails);
                result.add(detail);
            }
            int r = order - 1;
            for (int m = size; r >= 0 && indices[r] == --m; r--) { }
            if (r == -1) {
                return result;
            }
            for (int c = indices[r]; r < order;) {
                indices[r++] = ++c;
            }
        }
    }

    /**
     * Generates and runs mutants of the specified orders with the batch-stream method
     */
    public void runMutantsOfOrdersBatchStream(List<MutationDetails> fom, Collection<Integer> orders) {
        if (!orders.isEmpty() && ! fom.isEmpty()) {
          for (Integer i : orders) {
            if (i != 1) {
              runMutantsOfOrderBatchStream(fom, i);
            }
          }
        }
    }

    /**
     * Generates and runs mutants of the specified orders with the stream method
     */
    public void runMutantsOfOrdersStream(List<MutationDetails> fom, Collection<Integer> orders) {
        if (!orders.isEmpty() && ! fom.isEmpty()) {
            for (Integer i : orders) {
                if (i != 1) {
                    runMutantsOfOrderBatchStream(fom, i);
                }
            }
        }
    }

    /**
     * Generates mutants of the specified orders
     */
    public Collection<MutationDetails> makeMutantsOfOrders(List<MutationDetails> fom, Collection<Integer> orders) {
        Collection<MutationDetails> result = new ArrayList<>();
        if (!orders.isEmpty() && ! fom.isEmpty()) {
            for (Integer i : orders) {
                if (i != 1) {
                    result.addAll(makeMutantsOfOrder(fom, i));
                }
            }
        }
        interceptor.intercept(result, this.mutater);
        return result;
    }

    private static boolean noOverlap(Collection<MutationDetails> mutants) {
        List<Integer> l = FCollection.map(mutants, (a) -> a.getFirstIndex());
        Set<Integer> s = new HashSet<>(l);

        return l.size() == s.size();
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

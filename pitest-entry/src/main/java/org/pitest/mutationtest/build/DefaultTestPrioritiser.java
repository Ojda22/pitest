package org.pitest.mutationtest.build;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import org.pitest.classinfo.ClassName;
import org.pitest.coverage.BlockLocation;
import org.pitest.coverage.CoverageDatabase;
import org.pitest.coverage.InstructionLocation;
import org.pitest.coverage.TestInfo;
import org.pitest.functional.FCollection;
import org.pitest.functional.prelude.Prelude;
import org.pitest.mutationtest.engine.Location;
import org.pitest.mutationtest.engine.MutationDetails;
import org.pitest.util.Log;

/**
 * Assigns tests based on line coverage and order them by execution speed with a
 * weighting towards tests whose names imply they are intended to test the
 * mutated class
 *
 * @author henry
 *
 */
public class DefaultTestPrioritiser implements TestPrioritiser {

  private static final Logger    LOG                                  = Log
                                                                          .getLogger();

  private static final int       TIME_WEIGHTING_FOR_DIRECT_UNIT_TESTS = 1000;

  private final CoverageDatabase coverage;

  public DefaultTestPrioritiser(CoverageDatabase coverage) {
    this.coverage = coverage;
  }

  @Override
  public List<TestInfo> assignTests(MutationDetails mutation) {
    return prioritizeTests(mutation.getClassName(), pickTests(mutation));
  }

  private Collection<TestInfo> pickTests(MutationDetails mutation) {
    if (!mutation.isInStaticInitializer()) {
        HashSet<TestInfo> ret = new HashSet<>();
        List<List<Integer>> indexeslist = mutation.getId().getIndexesList();
        List<Location> locationsList = mutation.getId().getLocations();
        List<Integer> blocksList = mutation.getBlocks();
        for (int i = 0; i <  indexeslist.size(); i++) {
            for (int each :  indexeslist.get(i) ) {
                Collection<TestInfo> r = this.coverage.getTestsForInstructionLocation(
                        new InstructionLocation(
                                new BlockLocation(locationsList.get(i),
                                        blocksList.get(i), -1, -1), each - 1));
                if (r != null) {
                    ret.addAll(r);
                }
            }
        }
        return ret;
    } else {
      LOG.warning("Using untargeted tests");
      return this.coverage.getTestsForClass(mutation.getClassName());
    }
  }

  private List<TestInfo> prioritizeTests(ClassName clazz,
      Collection<TestInfo> testsForMutant) {
    final List<TestInfo> sortedTis = FCollection.map(testsForMutant,
        Prelude.id(TestInfo.class));
    Collections.sort(sortedTis, new TestInfoPriorisationComparator(clazz,
        TIME_WEIGHTING_FOR_DIRECT_UNIT_TESTS));
    return sortedTis;
  }

}

/*
 * Copyright 2010 Henry Coles
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.pitest.coverage.execute;

import static org.pitest.util.Unchecked.translateCheckedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import org.pitest.coverage.CoverageReceiver;
import org.pitest.rewriter.Serializer;
import org.pitest.testapi.TestUnit;
import org.pitest.testapi.execute.Container;
import org.pitest.testapi.execute.Pitest;
import org.pitest.testapi.execute.containers.UnContainer;
import org.pitest.util.Log;

public class CoverageWorker {

  private final CoveragePipe   pipe;
  private final List<TestUnit> tests;
  private static final Logger LOG = Log.getLogger();

  public CoverageWorker(final CoveragePipe pipe, final List<TestUnit> tests) {
    this.pipe = pipe;
    this.tests = tests;
  }

  public void run(boolean isCoverageCache) {

    try {
      final List<TestUnit> decoratedTests = decorateForCoverage(this.tests,
          this.pipe);

      Collections.sort(decoratedTests, testComparator());

      if (!isCoverageCache) {
        LOG.info("Caching coverage: " + isCoverageCache + " : All tests are executed");
        final Container c = new UnContainer();
        final Pitest pit = new Pitest(new ErrorListener());
        pit.run(c, decoratedTests);
      }else {
        LOG.info("Caching coverage: " + isCoverageCache + " : Tests are serialized");
        int i = 0;
        for (TestUnit tu : decoratedTests) {
          Serializer.serializeCoverage(tu.getDescription().getQualifiedName());
          List<TestUnit> l = new ArrayList<TestUnit>();
          l.add(tu);
          final Container c = new UnContainer();
          ErrorListener listener = new ErrorListener();
          final Pitest pit = new Pitest(listener);

          pit.run(c, l);

          Serializer.writeCoverageResult(i++, listener.pass, listener.errorMessage);
        }
      }
    } catch (final Exception ex) {
      throw translateCheckedException(ex);
    }

  }

  private static Comparator<TestUnit> testComparator() {
    return (o1, o2) -> o1.getDescription().getQualifiedName()
        .compareTo(o2.getDescription().getQualifiedName());
  }

  private static List<TestUnit> decorateForCoverage(final List<TestUnit> plainTests,
      final CoverageReceiver queue) {
    final List<TestUnit> decorated = new ArrayList<>(plainTests.size());
    for (final TestUnit each : plainTests) {
      decorated.add(new CoverageDecorator(queue, each));
    }
    return decorated;
  }
}

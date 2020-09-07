package org.pitest.mutationtest.report.mutantcoverage;

import org.pitest.mutationtest.ListenerArguments;
import org.pitest.mutationtest.MutationResultListener;
import org.pitest.mutationtest.MutationResultListenerFactory;

import java.util.Properties;

public class MutantCoverageReportFactory implements MutationResultListenerFactory {

    @Override
    public MutationResultListener getListener(Properties props, ListenerArguments args) {
        System.out.println(props);
        return new MutantCoverageListener(args);
    }

    @Override
    public String name() {
        return "MutantCoverageReporter";
    }

    @Override
    public String description() {
        return "Mutant Coverage plugin";
    }
}

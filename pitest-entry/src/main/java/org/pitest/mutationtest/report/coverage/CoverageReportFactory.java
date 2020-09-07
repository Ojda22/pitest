package org.pitest.mutationtest.report.coverage;

import org.pitest.mutationtest.ListenerArguments;
import org.pitest.mutationtest.MutationResultListener;
import org.pitest.mutationtest.MutationResultListenerFactory;

import java.util.Properties;

public class CoverageReportFactory implements MutationResultListenerFactory {

    @Override
    public MutationResultListener getListener(Properties props, ListenerArguments args) {
        return new CoverageReportListener(args);
    }

    @Override
    public String name() {
        return "CoverageReporter";
    }

    @Override
    public String description() {
        return "Coverage report plugin";
    }
}

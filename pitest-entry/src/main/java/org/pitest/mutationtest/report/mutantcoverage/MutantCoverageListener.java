package org.pitest.mutationtest.report.mutantcoverage;

import org.pitest.mutationtest.ClassMutationResults;
import org.pitest.mutationtest.ListenerArguments;
import org.pitest.mutationtest.MutationResult;
import org.pitest.mutationtest.MutationResultListener;
import org.pitest.mutationtest.engine.MutationDetails;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class MutantCoverageListener implements MutationResultListener {

    private ListenerArguments listenerArguments;
    private final Writer out;

    private List<MutationDetails> mutationDetailsList = new ArrayList<MutationDetails>();

    public MutantCoverageListener(Writer writer){
        this.out = writer;
    }

    public MutantCoverageListener(ListenerArguments arguments){
        this(arguments.getOutputStrategy().createWriterForFile("mutation_coverage_matrix.csv"));
        this.listenerArguments = arguments;
    }

    @Override
    public void runStart() {

    }

    @Override
    public void handleMutationResult(ClassMutationResults results) {
        for (MutationResult mutationResult : results.getMutations()){
            this.mutationDetailsList.add(mutationResult.getDetails());
        }
    }

    @Override
    public void runEnd() {

    }
}

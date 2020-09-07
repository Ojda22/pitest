package org.pitest.mutationtest.report.coverage;

import org.pitest.coverage.BlockLocation;
import org.pitest.coverage.CoverageDatabase;
import org.pitest.coverage.TestInfo;
import org.pitest.coverage.CoverageDataLineToClass;
import org.pitest.mutationtest.ClassMutationResults;
import org.pitest.mutationtest.ListenerArguments;
import org.pitest.mutationtest.MutationResultListener;
import org.pitest.util.Unchecked;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CoverageReportListener implements MutationResultListener {

    private final Writer out;
    private ListenerArguments listenerArguments;

    public CoverageReportListener(ListenerArguments args) {
        this(args.getOutputStrategy().createWriterForFile("mutations_coverage.csv"));
        this.listenerArguments = args;
    }

    public CoverageReportListener(Writer writer) {
        this.out = writer;
    }

    @Override
    public void runStart() {
        System.out.println("CoverageReportListener started");
    }

    @Override
    public void handleMutationResult(ClassMutationResults results) {
        System.out.println("Class Mutation Results");
    }

    private String getCsvLine(TestInfo key, Set<BlockLocation> value, Map<BlockLocation, Set<Integer>> blockLineMap) {
        StringBuilder csvLine = new StringBuilder();

        csvLine.append(key.getName() + " ");

        // implement instruction coverage more clearly. Each test should have instruction that covers, and block information of that instruction
        for (BlockLocation blockLocation : value) {
            Set<Integer> lineSet = blockLineMap.get(blockLocation);
            if (lineSet == null) {
                continue;
            }
            csvLine.append(blockLocation.getLocation() + ": ");
            for (Integer line : lineSet) {
                csvLine.append(line);
                csvLine.append(", ");
            }
        }
        csvLine.append("\n");

        return csvLine.toString();
    }

    @Override
    public void runEnd() {
        try {
            CoverageDatabase coverageDatabase = this.listenerArguments.getCoverage();

            if (coverageDatabase instanceof CoverageDataLineToClass) {
                CoverageDataLineToClass coverageDataLineToClass = (CoverageDataLineToClass) coverageDatabase;

                Map<TestInfo, Set<BlockLocation>> testToInstructionLocationMap = coverageDataLineToClass.getTestToClassLine();

                Map<BlockLocation, Set<Integer>> blockLineMap = coverageDataLineToClass.getBlockLineMap();
                for (Entry<TestInfo, Set<BlockLocation>> entry : testToInstructionLocationMap.entrySet()) {
                    this.out.write(getCsvLine(entry.getKey(), entry.getValue(), blockLineMap));
                }

            }else {
                throw new RuntimeException("Do not get the right listener object");
            }
            this.out.close();
        } catch (IOException e) {
            throw Unchecked.translateCheckedException(e);
        }
    }
}

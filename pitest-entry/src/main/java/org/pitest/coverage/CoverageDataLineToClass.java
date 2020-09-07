package org.pitest.coverage;

import org.pitest.classpath.CodeSource;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CoverageDataLineToClass extends CoverageData{

    private Map<TestInfo, Set<BlockLocation>> testToClassLineMap = new LinkedHashMap<TestInfo, Set<BlockLocation>>();

    public CoverageDataLineToClass(CodeSource code, LineMap lm) {
        super(code, lm);
    }

    public Map<TestInfo, Set<BlockLocation>> getTestToClassLine(){
        Map<BlockLocation, Set<TestInfo>> blockCoverage = super.getBlockCoverage();

        for (BlockLocation blockLocation : blockCoverage.keySet()){
            Set<TestInfo> testInfoSet = blockCoverage.get(blockLocation);
            for (TestInfo testInfo : testInfoSet){

                Set<BlockLocation> blockLocationSet = this.testToClassLineMap.get(testInfo);
                if (blockLocationSet == null){
                    blockLocationSet = new HashSet<BlockLocation>();
                }

                blockLocationSet.add(blockLocation);

                this.testToClassLineMap.put(testInfo, blockLocationSet);
            }
        }

        return CoverageDataLineToClass.this.testToClassLineMap;
    }

    public Map<BlockLocation, Set<Integer>> getBlockLineMap(){
        return super.getBlocksToLines();
    }

}

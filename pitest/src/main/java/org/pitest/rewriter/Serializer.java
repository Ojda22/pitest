package org.pitest.rewriter;

import org.apache.commons.codec.digest.DigestUtils;
import org.pitest.mutationtest.execute.MutationTestWorker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class Serializer {

    private static final String MUT_DIRECTORY = "mutation-assertion";
    private static final String COV_DIRECTORY = "coverage-assertion";
    public static final String splitter = ",";
    private static List<String> list = new ArrayList<String>();
    private static Set<String> tracedAsserts = new HashSet<String>();
    public static final String SEP = " ";
    public static final String EXP = "[EXCEPTION]";
    public static final String STRACE = "[STACKTRACE]";


    public static void serializeCoverage(String desc) {
        try {
            list.add(desc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void serialize(String desc, boolean value, Object expecteds, Object actuals) {
        try {
            String assertId = desc.trim();
            if (MutationTestWorker.testUnit != null) {
                assertId = desc.trim() + SEP + MutationTestWorker.testUnit.getDescription().getQualifiedName();
            }
            String key;

            if (!tracedAsserts.contains(assertId)) {
                String content = getObjectContent(expecteds) + "" + getObjectContent(actuals);
                // keep complete exception info
                if (desc.equals(EXP)) {
                    key = assertId + SEP + value + SEP + content;
                }else {
                    key = assertId + SEP + value + SEP + DigestUtils.md5Hex(content);
                }
                list.add(key);
                tracedAsserts.add(assertId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void serialize(String desc, boolean value, Object o) {
        try {
            String assertId = desc.trim();
            if (MutationTestWorker.testUnit != null) {
                assertId = desc.trim() + SEP + MutationTestWorker.testUnit.getDescription().getQualifiedName();
            }

            if (!tracedAsserts.contains(assertId)) {
                String key;
                String content = getObjectContent(o);
                // keep complete exception info
                if (desc.equals(EXP)) {
                    key = assertId + SEP + value + SEP + content;
                }else {
                    key = assertId + SEP + value + SEP + DigestUtils.md5Hex(content);
                }
                list.add(key);
                tracedAsserts.add(assertId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void serialize(String desc, boolean value, Object[] expecteds, Object[] actuals) {
        try {
            String assertId = desc.trim();
            if (MutationTestWorker.testUnit != null) {
                assertId = desc.trim() + SEP + MutationTestWorker.testUnit.getDescription().getQualifiedName();
            }
            String key;

            if (!tracedAsserts.contains(assertId)) {
                String content = getArrayContents(expecteds) + "" + getArrayContents(actuals);
                // keep complete exception info
                if (desc.equals(EXP)) {
                    key = assertId + SEP + value + SEP + content;
                }else {
                    key = assertId + SEP + value + SEP + DigestUtils.md5Hex(content);
                }
                list.add(key);
                tracedAsserts.add(assertId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void serialize(String desc, boolean value, String content) {
        try {
            String assertId = desc.trim();
            if (MutationTestWorker.testUnit != null) {
                assertId = desc.trim() + SEP + MutationTestWorker.testUnit.getDescription().getQualifiedName();
            }
            if (!tracedAsserts.contains(assertId)) {
                String key;
                // keep complete exception info
                if (desc.equals(EXP)) {
                    key = assertId + SEP + value + SEP + content;
                }else {
                    key = assertId + SEP + value + SEP + DigestUtils.md5Hex(content);
                }
                list.add(key);
                tracedAsserts.add(assertId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeCoverageResult(int instanceCount, boolean testOutcome, String stackTrace) {
        PrintWriter serializer = null;
        try {
            String fileName = COV_DIRECTORY + File.separator + instanceCount;
            File outputDir = new File(COV_DIRECTORY);
            if (!outputDir.exists()) {
                outputDir.mkdir();
            }

            serializer = new PrintWriter(new File(fileName));

            serializer.write(list.get(0) + SEP + testOutcome + SEP + (stackTrace) + "\n");
            // writer assertion id and outcome
            for (int i = 1; i < list.size(); i++) {
                String item = list.get(i);
                serializer.write(item + "\n");
            }
            serializer.flush();

            list.clear();
            tracedAsserts.clear();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serializer.close();
        }
    }

    public static void writeResult(String result, int instanceCount) {
        BufferedWriter serializer = null;
        try {
            String fileName = MUT_DIRECTORY + File.separator
                    + MutationTestWorker.mutationDetails.getId().getClassName()
                    + "." + MutationTestWorker.mutationDetails.getLineNumber()
                    + "." + MutationTestWorker.mutationDetails.getFirstIndex()
                    + System.nanoTime() + instanceCount;
            File outputDir = new File(MUT_DIRECTORY);
            if (!outputDir.exists()) {
                outputDir.mkdir();
            }

//            GZIPOutputStream zip = new GZIPOutputStream(
//                    new FileOutputStream(new File(fileName + ".cover.gz")));

            serializer = new BufferedWriter(new FileWriter(fileName + ".cover", true));

            serializer.write(MutationTestWorker.mutationDetails.toString() + "\n");

            for (String item : list) {
                serializer.write(item + "\n");
            }
            serializer.write(result);
            serializer.flush();

            list.clear();
            tracedAsserts.clear();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serializer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Compute object content via transforming the object into XML format (using
     * XStream), since the hashCode of object changed during different runs
     *
     * @param object
     * @return XML string of o
     */
    public static String getObjectContent(Object object) {
        if (object == null) {
            return "";
        }else {
            return new XStream(new StaxDriver()).toXML(object).replace("\n", "").replace(" ", "");
        }
    }

    public static String getArrayContents(Object[] os) {
        String content = "";
        if (os == null) {
            return content;
        }
        for (Object o : os) {
            content = content + getObjectContent(o) + splitter;
        }
        return content;
    }

//    public static final Logger LOG = Log.getLogger();
//    private static final String DIRECTORY = "mutation-assertion";
////    private static Set<ResultItem> set = new HashSet<ResultItem>();
////    private static Set<String> set = new HashSet<String>();
//    private static List<String> list = new ArrayList<String>();
//    private static Set<String> tracedAsserts = new HashSet<String>();
//
//    private static final String MUT_DIRECTORY = "mutation-assertion";
//    private static final String COV_DIRECTORY = "coverage-assertion";
//    public static final String SEP=" ";
//    public static final String EXP="[EXCEPTION]";
//    public static final String STRACE="[STACKTRACE]";
//    public static final String splitter = ",";
//
//    static {
////
//    }
//
//    public static void serializeCoverage(String description){
//        try{
//            list.add(description);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public static void writeCoverageResults(int instanceCount, boolean testOutcome, String stackTrace){
//        PrintWriter serializer = null;
//        try{
//            String fileName = COV_DIRECTORY + File.separator + instanceCount;
//            File outputDir = new File(COV_DIRECTORY);
//            if (!outputDir.exists()){
//                outputDir.mkdir();
//            }
//            serializer = new PrintWriter(new File(fileName));
//
//            serializer.write(list.get(0) + SEP + testOutcome + SEP + (stackTrace) + "\n");
//            for (int i = 1; i < list.size(); i++){
//                String item = list.get(i);
//                serializer.write(item + "\n");
//            }
//            serializer.flush();
//
//            list.clear();
//            tracedAsserts.clear();
//        }catch (IOException e){
//            e.printStackTrace();
//        }finally {
//            serializer.close();
//        }
//    }
//
//    public static void serialize(String desc, boolean value, Object expecteds, Object actuals) {
//        try {
//            String assertId = desc.trim();
////            ResultItem resultItem = new ResultItem();
//            if (MutationTestWorker.testUnit != null) {
////                resultItem.setMethodUnitQualifiedName(MutationTestWorker.testUnit.getDescription().getQualifiedName());
//                assertId = desc.trim() + SEP + MutationTestWorker.testUnit.getDescription().getQualifiedName();
//            }
//            String key;
//
////            String[] assertIdValues = assertId.split(":");
////            resultItem.setClassName(assertIdValues[0]);
////            resultItem.setTestCaseName(assertIdValues[1]);
////            resultItem.setTestDescription(assertIdValues[2]);
////            resultItem.setLineNumber(assertIdValues[3]);
////            resultItem.setAssertMethod(assertIdValues[4]);
//
//            if (!tracedAsserts.contains(assertId)) {
//                String content = getObjectContent(expecteds) + "" + getObjectContent(actuals);
//                LOG.info("<<<<<Assert objects content: " + content);
//                // keep complete exception info
//                if (desc.equals(EXP)) {
//                    key = assertId + SEP + value + SEP + content;
//                }else {
////                    key = assertId + SEP + value + SEP + DigestUtils.md5Hex(content);
//                    key = assertId + SEP + value + SEP + content;
//                }
////                resultItem.setAssertion(value);
////                resultItem.setAssertionContent(content);
////                set.add(resultItem);
//                list.add(key);
//                tracedAsserts.add(assertId);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void serialize(String desc, boolean value, Object o) {
//        try {
//            String assertId = desc.trim();
////            ResultItem resultItem = new ResultItem();
//            if (MutationTestWorker.testUnit != null) {
////                resultItem.setMethodUnitQualifiedName(MutationTestWorker.testUnit.getDescription().getQualifiedName());
//                assertId = desc.trim() + SEP + MutationTestWorker.testUnit.getDescription().getQualifiedName();
//            }
//
////            String[] assertIdValues = assertId.split(":");
////            resultItem.setClassName(assertIdValues[0]);
////            resultItem.setTestCaseName(assertIdValues[1]);
////            resultItem.setTestDescription(assertIdValues[2]);
////            resultItem.setLineNumber(assertIdValues[3]);
////            resultItem.setAssertMethod(assertIdValues[4]);
//
//            if (!tracedAsserts.contains(assertId)) {
//                String key;
//                String content = getObjectContent(o);
//                LOG.info("<<<<<Assert object content: " + content);
//                // keep complete exception info
//                if (desc.equals(EXP)) {
//                    key = assertId + SEP + value + SEP + content;
//                }else {
////                    key = assertId + SEP + value + SEP + DigestUtils.md5Hex(content);
//                    key = assertId + SEP + value + SEP + content;
//                }
////                resultItem.setAssertion(value);
////                resultItem.setAssertionContent(content);
////                set.add(resultItem);
//                list.add(key);
//                tracedAsserts.add(assertId);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void serialize(String desc, boolean value, Object[] expecteds, Object[] actuals) {
//        try {
//            String assertId = desc.trim();
//            if (MutationTestWorker.testUnit != null) {
//                assertId = desc.trim() + SEP + MutationTestWorker.testUnit.getDescription().getQualifiedName();
//            }
//            String key;
//
//            if (!tracedAsserts.contains(assertId)) {
//                String content = getArrayContents(expecteds) + "" + getArrayContents(actuals);
//                LOG.info("<<<<<Assert objects array content: " + content);
//                // keep complete exception info
//                if (desc.equals(EXP)) {
//                    key = assertId + SEP + value + SEP + content;
//                }else {
////                    key = assertId + SEP + value + SEP + DigestUtils.md5Hex(content);
//                    key = assertId + SEP + value + SEP + content;
//                }
//                list.add(key);
//                tracedAsserts.add(assertId);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void serialize(String assertDescription, boolean value, String content) {
//        try {
//            String assertId = assertDescription.trim();
//            if (MutationTestWorker.testUnit != null) {
//                assertId = assertDescription.trim() + SEP + MutationTestWorker.testUnit.getDescription().getQualifiedName();
//            }
//
//            if (!tracedAsserts.contains(assertId)) {
//                String key;
//                // keep complete exception info
//                if (assertDescription.equals(EXP)) {
//                    key = assertId + SEP + value + SEP + content;
//                }else {
////                    key = assertId + SEP + value + SEP + DigestUtils.md5Hex(content);
//                    key = assertId + SEP + value + SEP + content;
//                }
//                list.add(key);
//                tracedAsserts.add(assertId);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static void writeResult(String result, int instanceCount) {
//        BufferedWriter serializer = null;
////        BufferedWriter serializerList = null;
//        try {
////            String fileName = MUT_DIRECTORY + File.separator
////                + MutationTestWorker.mutationDetails.getId().getClassName() + "-"
////                + Arrays.toString(MutationTestWorker.mutationDetails.getLineNumbers().toArray()) + "-"
////                + "[" + MutationTestWorker.mutationDetails.getId().getIndexesList().stream().map(l -> l.get(0) + "").collect(Collectors.joining(",")) + "]" + "-" + Arrays.toString(MutationTestWorker.mutationDetails.getId().getMutators().toArray()) + "-" + instanceCount;
//            String fileName = DIRECTORY + File.separator
//                    + MutationTestWorker.mutationDetails.getId().getClassName() + "."
//                    + MutationTestWorker.mutationDetails.getLineNumber() + "."
//                    + MutationTestWorker.mutationDetails.getFirstIndex() + "." + System.nanoTime() + instanceCount;
//            File outputDir = new File(MUT_DIRECTORY);
//            if (!outputDir.exists()) {
//                outputDir.mkdir();
//            }
//
////            GZIPOutputStream zip = new GZIPOutputStream(new FileOutputStream(new File(fileName + ".cover.gz")));
////            serializer = new BufferedWriter(new OutputStreamWriter(zip, "UTF-8"));
//
//            serializer = new BufferedWriter(new FileWriter(fileName + ".cover", true));
//
//            serializer.write(MutationTestWorker.mutationDetails.toString() + "\n");
//
//            for(String item : list){
//                serializer.write(item + "\n");
//            }
//
//            serializer.write(result);
//            serializer.flush();
//
////            serializerList = new BufferedWriter(new FileWriter(fileName + ".list", true));
//
////            for (Entry<String,Integer> item : dict.entrySet()){
////                serializerList.write(item.getKey() + ":" + item.getValue() + "\n");
////            }
//
////            serializerList.flush();
////            resultList.clear();
//            list.clear();
//            tracedAsserts.clear();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                serializer.close();
////                serializerList.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void logInfo(String info){
//        LOG.info(info);
//    }
//
//    /**
//     * Compute object content via transforming the object into XML format (using
//     * XStream), since the hashCode of object changed during different runs
//     *
//     * @param object
//     * @return XML string of o
//     */
//    public static String getObjectContent(Object object) {
//        if (object == null) {
//            return "";
//        }else{
//            return new XStream(new StaxDriver()).toXML(object).replace("\n", "").replace(" ", "");
//        }
//    }
//
//    public static String getArrayContents(Object[] os) {
//        String content = "";
//        if (os == null) {
//            return content;
//        }
//        for (Object o : os) {
//            content = content + getObjectContent(o) + splitter;
//        }
//        return content;
//    }

}

package org.pitest.rewriter;

import org.apache.commons.codec.digest.DigestUtils;
import org.pitest.mutationtest.execute.MutationTestWorker;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.pitest.util.Log;

public class Serializer {

    private static final String MUT_DIRECTORY = "mutation-assertion";
    private static final String COV_DIRECTORY = "coverage-assertion";
    public static final String splitter = ",";
    private static List<String> list = new ArrayList<String>();
    private static List<ResultItem> resultItemsList = new ArrayList<ResultItem>();
    private static Set<String> tracedAsserts = new HashSet<String>();
    public static final String SEP = " ";
    public static final String EXP = "[EXCEPTION]";
    public static final String STRACE = "[STACKTRACE]";
    public static final Logger LOG = Log.getLogger();

    private static final String MUT_ASSERT_FILE = "mutations-assertions.xml";

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
            ResultItem resultItem = new ResultItem();
            if (MutationTestWorker.testUnit != null) {
                assertId = desc.trim() + SEP + MutationTestWorker.testUnit.getDescription().getQualifiedName();
                resultItem.setTestUnitQualifiedName(MutationTestWorker.testUnit.getDescription().getQualifiedName());
            }
            String key;

            if (!tracedAsserts.contains(assertId)) {
                String content = getObjectContent(expecteds) + "" + getObjectContent(actuals);
                // keep complete exception info
                if (desc.equals(EXP)) {
                    key = assertId + SEP + value + SEP + content;
                    resultItem.setAssertionDescription(desc);
                    resultItem.setAssertionContent(content);
                    String[] traceElements = content.split("\\[STACKTRACE\\]");
                    if (traceElements.length == 2) {
                        resultItem.setExceptionName(traceElements[0]);
                        resultItem.setStackTrace(traceElements[1]);
                    }else {
                        resultItem.setExceptionName(traceElements[0]);
                    }
                }else {
                    key = assertId + SEP + value + SEP + DigestUtils.md5Hex(content);
                    resultItem.setAssertionContent(DigestUtils.md5Hex(content));
                    resultItem.setAssertionDescription(desc);
                    String[] descriptionElements = desc.split(":");
                    if (descriptionElements.length == 5) {
                        resultItem.setClassName(descriptionElements[0]);
                        resultItem.setTestCaseName(descriptionElements[1]);
                        resultItem.setTestCaseMethodDescription(descriptionElements[2]);
                        resultItem.setAssertionLineNumber(descriptionElements[3]);
                        resultItem.setAssertionMethod(descriptionElements[4]);
                    }else {
                        LOG.log(Level.SEVERE, "SHOULD NOT HAPPEN!!!");
                    }
                }
                resultItem.setAssertionValue(new Boolean(value));
                resultItemsList.add(resultItem);
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
            ResultItem resultItem = new ResultItem();
            if (MutationTestWorker.testUnit != null) {
                assertId = desc.trim() + SEP + MutationTestWorker.testUnit.getDescription().getQualifiedName();
                resultItem.setTestUnitQualifiedName(MutationTestWorker.testUnit.getDescription().getQualifiedName());
            }

            if (!tracedAsserts.contains(assertId)) {
                String key;
                String content = getObjectContent(o);
                // keep complete exception info
                if (desc.equals(EXP)) {
                    key = assertId + SEP + value + SEP + content;
                    resultItem.setAssertionDescription(desc);
                    resultItem.setAssertionContent(content);
                    String[] traceElements = content.split("\\[STACKTRACE\\]");
                    if (traceElements.length == 2) {
                        resultItem.setExceptionName(traceElements[0]);
                        resultItem.setStackTrace(traceElements[1]);
                    }else {
                        resultItem.setExceptionName(traceElements[0]);
                    }
                }else {
                    key = assertId + SEP + value + SEP + DigestUtils.md5Hex(content);
                    resultItem.setAssertionContent(DigestUtils.md5Hex(content));
                    resultItem.setAssertionDescription(desc);
                    String[] descriptionElements = desc.split(":");
                    if (descriptionElements.length == 5) {
                        resultItem.setClassName(descriptionElements[0]);
                        resultItem.setTestCaseName(descriptionElements[1]);
                        resultItem.setTestCaseMethodDescription(descriptionElements[2]);
                        resultItem.setAssertionLineNumber(descriptionElements[3]);
                        resultItem.setAssertionMethod(descriptionElements[4]);
                    }else {
                        LOG.log(Level.SEVERE, "SHOULD NOT HAPPEN!!!");
                    }
                }
                resultItem.setAssertionValue(new Boolean(value));
                resultItemsList.add(resultItem);
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
            ResultItem resultItem = new ResultItem();
            if (MutationTestWorker.testUnit != null) {
                assertId = desc.trim() + SEP + MutationTestWorker.testUnit.getDescription().getQualifiedName();
                resultItem.setTestUnitQualifiedName(MutationTestWorker.testUnit.getDescription().getQualifiedName());
            }
            String key;

            if (!tracedAsserts.contains(assertId)) {
                String content = getArrayContents(expecteds) + "" + getArrayContents(actuals);
                // keep complete exception info
                if (desc.equals(EXP)) {
                    key = assertId + SEP + value + SEP + content;
                    resultItem.setAssertionDescription(desc);
                    resultItem.setAssertionContent(content);
                    String[] traceElements = content.split("\\[STACKTRACE\\]");
                    if (traceElements.length == 2) {
                        resultItem.setExceptionName(traceElements[0]);
                        resultItem.setStackTrace(traceElements[1]);
                    }else {
                        resultItem.setExceptionName(traceElements[0]);
                    }
                }else {
                    key = assertId + SEP + value + SEP + DigestUtils.md5Hex(content);
                    resultItem.setAssertionContent(DigestUtils.md5Hex(content));
                    resultItem.setAssertionDescription(desc);
                    String[] descriptionElements = desc.split(":");
                    if (descriptionElements.length == 5) {
                        resultItem.setClassName(descriptionElements[0]);
                        resultItem.setTestCaseName(descriptionElements[1]);
                        resultItem.setTestCaseMethodDescription(descriptionElements[2]);
                        resultItem.setAssertionLineNumber(descriptionElements[3]);
                        resultItem.setAssertionMethod(descriptionElements[4]);
                    }else {
                        LOG.log(Level.SEVERE, "SHOULD NOT HAPPEN!!!");
                    }
                }
                resultItem.setAssertionValue(new Boolean(value));
                resultItemsList.add(resultItem);
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
            ResultItem resultItem = new ResultItem();
            if (MutationTestWorker.testUnit != null) {
                assertId = desc.trim() + SEP + MutationTestWorker.testUnit.getDescription().getQualifiedName();
                resultItem.setTestUnitQualifiedName(MutationTestWorker.testUnit.getDescription().getQualifiedName());
            }
            if (!tracedAsserts.contains(assertId)) {
                String key;
                // keep complete exception info
                if (desc.equals(EXP)) {
                    key = assertId + SEP + value + SEP + content;
                    resultItem.setAssertionDescription(desc);
                    resultItem.setAssertionContent(content);
                    String[] traceElements = content.split("\\[STACKTRACE\\]");
                    if (traceElements.length == 2) {
                        resultItem.setExceptionName(traceElements[0]);
                        resultItem.setStackTrace(traceElements[1]);
                    }else {
                        resultItem.setExceptionName(traceElements[0]);
                    }
                }else {
                    key = assertId + SEP + value + SEP + DigestUtils.md5Hex(content);
                    resultItem.setAssertionContent(DigestUtils.md5Hex(content));
                    resultItem.setAssertionDescription(desc);
                    String[] descriptionElements = desc.split(":");
                    if (descriptionElements.length == 5) {
                        resultItem.setClassName(descriptionElements[0]);
                        resultItem.setTestCaseName(descriptionElements[1]);
                        resultItem.setTestCaseMethodDescription(descriptionElements[2]);
                        resultItem.setAssertionLineNumber(descriptionElements[3]);
                        resultItem.setAssertionMethod(descriptionElements[4]);
                    }else {
                        LOG.log(Level.SEVERE, "SHOULD NOT HAPPEN!!!");
                    }
                }
                resultItem.setAssertionValue(new Boolean(value));
                resultItemsList.add(resultItem);
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
            String fileName = MUT_DIRECTORY + File.separator + MUT_ASSERT_FILE;

            serializer = new BufferedWriter(new FileWriter(new File(fileName), true));

            ReportWriter reportWriter = new ReportWriter(serializer);

            reportWriter.runBody(MutationTestWorker.mutationDetails, resultItemsList);

            serializer.flush();

            resultItemsList.clear();
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


    public static void closeOrOpenMutationFile(){
        BufferedWriter serializer = null;
        try {
            File outputDir = new File(MUT_DIRECTORY);
            if (!outputDir.exists()) {
                outputDir.mkdir();
            }

            String fileName = outputDir + File.separator + MUT_ASSERT_FILE;

            File file = new File(fileName);
            boolean fileExists = file.exists();

            serializer = new BufferedWriter(new FileWriter(file, true));
            ReportWriter reportWriter = new ReportWriter(serializer);
            if (fileExists) {
                reportWriter.runEnd();
            }else {
                reportWriter.runStart();
            }
            serializer.flush();
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
}

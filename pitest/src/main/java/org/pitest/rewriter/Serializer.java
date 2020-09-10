package org.pitest.rewriter;

import org.pitest.mutationtest.execute.MutationTestWorker;
import org.pitest.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.logging.Logger;

public class Serializer {

    public static final Logger LOG = Log.getLogger();

    private static final String DIRECTORY = "mutation-assertion";

    private static Map<String, String> dict = new HashMap<String, String>();
    private static List<ResultItem> resultList = new ArrayList<ResultItem>();
//    private static Set<ResultItem> set = new HashSet<ResultItem>();
    private static Set<String> set = new HashSet<String>();
    private static List<String> list = new ArrayList<String>();

    private static final String columnSeperator = "\t";

    private static BufferedReader bufferedReader;

    static {
//        try {
//            String line;
//
//            bufferedReader = new BufferedReader(new FileReader("index.txt"));
//
//            while ((line = bufferedReader.readLine()) != null){
//                String[] fields = line.split(columnSeperator);
//                dict.put(fields[0] + ":" + fields[2], fields[4]);
//                System.out.println(fields[0] + ":" + fields[2]);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                if (bufferedReader != null){
//                    bufferedReader.close();
//                }
//            }catch (IOException exception){
//                exception.printStackTrace();
//            }
//        }
    }


    public static void serialize(String assertID, boolean value, String content){
        LOG.info("#### Destination: " + assertID + " Value: " + value);
        try {
////            String id = getId(assertID);
//            ResultItem resultItem = new ResultItem(value, assertID);
////            if (id != null){
////                resultItem = new ResultItem(value, id);
////            }
////            if (!set.contains(resultItem)){
////                set.add(resultItem);
////                resultList.add(resultItem);
////            }
//            resultList.add(resultItem);
            String key = assertID.trim() + ":" + MutationTestWorker.testUnit.getDescription().getQualifiedName() + ":" + value + ":" + content;
            if (!set.contains(key)){
                set.add(assertID.trim() + ":" + MutationTestWorker.testUnit.getDescription().getQualifiedName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String getId(String destination){
        if (dict.containsKey(destination)){
            return dict.get(destination);
        }else {
            LOG.info("NO SUCH ITEM: " + destination);
        }
        return null;
    }

    public static void writeResult(String result, int instanceCount) {
        BufferedWriter serializer = null;
//        BufferedWriter serializerList = null;
        try {
            String fileName = DIRECTORY + File.separator
                + MutationTestWorker.mutationDetails.getId().getClassName() + "."
                + MutationTestWorker.mutationDetails.getLineNumber() + "."
                + MutationTestWorker.mutationDetails.getFirstIndex() + "." + System.nanoTime() + instanceCount;
            File outputDir = new File(DIRECTORY);
            if (!outputDir.exists()) {
                outputDir.mkdir();
            }

//            GZIPOutputStream zip = new GZIPOutputStream(new FileOutputStream(new File(fileName + ".cover.gz")));
//            serializer = new BufferedWriter(new OutputStreamWriter(zip, "UTF-8"));

            serializer = new BufferedWriter(new FileWriter(fileName + ".cover", true));

            serializer.write(MutationTestWorker.mutationDetails.toString() + "\n");

//            for (ResultItem item : resultList){
//                serializer.write(item.id + ":" + (item.assertion ? 1:0) + "\n");
//            }

            for(String item : set){
                serializer.write(item + "\n");
            }

            serializer.write(result);
            serializer.flush();

//            serializerList = new BufferedWriter(new FileWriter(fileName + ".list", true));

//            for (Entry<String,Integer> item : dict.entrySet()){
//                serializerList.write(item.getKey() + ":" + item.getValue() + "\n");
//            }

//            serializerList.flush();
            resultList.clear();
            set.clear();
            list.clear();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serializer.close();
//                serializerList.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void serialize(String directory, String value) {
        BufferedWriter serializer = null;
        LOG.info("#### REWRITER DIRECTORY: " + directory);
        try {
            File outputDir = new File(directory);
            if (!outputDir.exists()) {
                outputDir.mkdir();
            }
            serializer = new BufferedWriter(new FileWriter(directory + File.separator + Rewriter.FILENAME, true));
            serializer.write(value + "\n");
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

    public static void logInfo(String info){
        LOG.info(info);
    }

}

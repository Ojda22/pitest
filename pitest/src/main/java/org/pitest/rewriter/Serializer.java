package org.pitest.rewriter;

import org.pitest.mutationtest.engine.MutationDetails;
import org.pitest.mutationtest.execute.MutationTestWorker;
import org.pitest.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

public class Serializer {

    public static final Logger LOG = Log.getLogger();

    public static MutationDetails mut = null;

    private static final String DIRECTORY = "assertion-files";

    private static Map<String, Integer> dict = new HashMap<String, Integer>();
    private static List<ResultItem> resultList = new ArrayList<ResultItem>();
    private static int idCount = 0;

    public static void serialize(String destination, boolean value){
        LOG.info("#### Destination: " + destination + " Value: " + value);
        Integer id = getId(destination);
        resultList.add(new ResultItem(value, id));
    }

    private static Integer getId(String destination){
        if (dict.containsKey(destination)){
            return dict.get(destination);
        }
        int id = idCount++;
        dict.put(destination, id);
        return id;
    }

    public static void writeResult(String result) {
        BufferedWriter serializer = null;
        BufferedWriter serializerList = null;
        String fileName = DIRECTORY + File.separator
                + MutationTestWorker.mutationDetails.getId().getClassName() + "."
                + MutationTestWorker.mutationDetails.getLineNumber() + "."
                + MutationTestWorker.mutationDetails.getFirstIndex();
        try {
            File outputDir = new File(DIRECTORY);
            if (!outputDir.exists()) {
                outputDir.mkdir();
            }
            serializer = new BufferedWriter(new FileWriter(fileName + ".cover", true));
            serializer.write(MutationTestWorker.mutationDetails.toString() + "\n");

            for (ResultItem item : resultList){
                serializer.write(item.id + ":" + (item.assertion ? 1:0) + "\n");
            }
            serializer.write(result);
            serializer.flush();

            serializerList = new BufferedWriter(new FileWriter(fileName + ".list", true));

            for (Entry<String,Integer> item : dict.entrySet()){
                serializerList.write(item.getKey() + ":" + item.getValue() + "\n");
            }

            serializerList.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serializer.close();
                serializerList.close();
                resultList.clear();
                dict.clear();
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

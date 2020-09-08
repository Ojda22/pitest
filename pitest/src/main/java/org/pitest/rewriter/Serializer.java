package org.pitest.rewriter;

import org.pitest.mutationtest.engine.MutationDetails;
import org.pitest.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class Serializer {

    public static final Logger LOG = Log.getLogger();

    public static MutationDetails mut = null;

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

package org.pitest.rewriter;

import org.pitest.util.Log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class Serializer {

    public static final Logger LOG = Log.getLogger();

    public static void serialize(String path, String value){
        BufferedWriter serializer;
        LOG.info("#### REWRITER PATH: " + path);
        try {
            serializer = new BufferedWriter(new FileWriter(path, true));
            serializer.write(value+"\n");
            serializer.flush();
            LOG.info(value + "\n");
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

}

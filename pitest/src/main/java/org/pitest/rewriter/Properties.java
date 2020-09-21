package org.pitest.rewriter;

import org.pitest.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Properties {

    private static final Logger LOG = Log.getLogger();
    public static final String PROJECT_PREFIX_KEY = "prefix";
    public static final String ASSERT_TRANS_KEY = "rewriter.trans";
    public static final String REWRITER_DIR = "assert-files";

    public static String PROJECT_PREFIX = "org/apache/commons/collections4";
//    public static String PROJECT_PREFIX = getProperty(PROJECT_PREFIX_KEY);
    public static boolean ASSERT_TRANS = getPropertyOrDefault(ASSERT_TRANS_KEY, true);

    public static final String REWRITER_CLASS_NAME = "org/pitest/rewriter/Rewriter";

    public static Map<String, String> configuration;

    static {
        BufferedReader br = null;

        try {
            List<String> lines;
            br = new BufferedReader(new FileReader("prefix.conf"));
            lines = br.lines().collect(Collectors.toList());
            for (String line : lines) {
                if (line.contains("=")) {
                    String key = line.split("=")[0];
                    String value = line.split("=")[1];
                    configuration.put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static String getProperty(String key){
        String result = null;
        if (System.getProperty(key) != null){
            result = System.getProperty(key);
        }
        return result;
    }

    public static boolean getPropertyOrDefault(String key, boolean defaultValue){
        String property = getProperty(key);
        if (property != null){
            String propertyTrimmed = property.trim().toLowerCase();
            if (propertyTrimmed.equals("true") || propertyTrimmed.equals("yes")){
                return true;
            }else if (propertyTrimmed.equals("false") || propertyTrimmed.equals("no")){
                return false;
            }
        }
        return defaultValue;
    }


}

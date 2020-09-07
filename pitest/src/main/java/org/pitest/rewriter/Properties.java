package org.pitest.rewriter;

import org.pitest.util.Log;

import java.util.logging.Logger;

public class Properties {

    private static final Logger LOG = Log.getLogger();
    public static final String PROJECT_PREFIX_KEY = "rewrite.pattern";
    public static final String ASSERT_TRANS_KEY = "rewriter.trans";
    public static final String REWRITER_DIR = "assert-files";

    public static String PROJECT_PREFIX = "org/apache/commons/collections4";
    public static boolean ASSERT_TRANS = getPropertyOrDefault(ASSERT_TRANS_KEY, true);

    public static final String REWRITER_CLASS_NAME = "org/pitest/rewriter/Rewriter";

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

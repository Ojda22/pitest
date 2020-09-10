package org.pitest.rewriter;

public class AssertParser {

    public static final String splitter = ",";

    /**
     * Compute object address for "==" computation: Use System.identityHashCode
     * since the hashCode computation may has been overridden
     *
     * @param o
     * @return computed object address
     */
    @Deprecated
    public static String getObjectAddress(Object o) {
        return o.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(o));
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
        }else{
//            return new XStream(new StaxDriver()).toXML(object).replace("\n", "").replace(" ", "");
            return object.toString();
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

    public static String getArrayContents(boolean[] bs) {
        String content = "";
        if (bs == null) {
            return content;
        }
        for (boolean b : bs) {
            content = content + b + splitter;
        }
        return content;
    }

    public static String getArrayContents(char[] cs) {
        String content = "";
        if (cs == null) {
            return content;
        }
        for (char c : cs) {
            content = content + c + splitter;
        }
        return content;
    }

    public static String getArrayContents(byte[] bs) {
        String content = "";
        if (bs == null) {
            return content;
        }
        for (byte b : bs) {
            content = content + b + splitter;
        }
        return content;
    }

    public static String getArrayContents(int[] bs) {
        String content = "";
        if (bs == null) {
            return content;
        }
        for (int b : bs) {
            content = content + b + splitter;
        }
        return content;
    }

    public static String getArrayContents(float[] bs) {
        String content = "";
        if (bs == null) {
            return content;
        }
        for (float b : bs) {
            content = content + b + splitter;
        }
        return content;
    }

    public static String getArrayContents(double[] bs) {
        String content = "";
        if (bs == null) {
            return content;
        }
        for (double b : bs) {
            content = content + b + splitter;
        }
        return content;
    }

    public static String getArrayContents(long[] bs) {
        String content = "";
        if (bs == null) {
            return content;
        }
        for (long b : bs) {
            content = content + b + splitter;
        }
        return content;
    }

    public static String getArrayContents(short[] bs) {
        String content = "";
        if (bs == null) {
            return content;
        }
        for (short b : bs) {
            content = content + b + splitter;
        }
        return content;
    }

}

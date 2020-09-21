package org.pitest.rewriter;

import org.pitest.util.Log;

import java.util.Arrays;
import java.util.logging.Logger;

public class AssertParser {

    public static final String splitter = ",";

    private static final Logger LOG = Log.getLogger();

    /**
     * Compute object address for "==" computation: Use System.identityHashCode
     * since the hashCode computation may has been overridden
     *
     * @param o
     * @return computed object address
     */
    @Deprecated
    public static String getObjectAddress(Object o) {
        LOG.info("<<<<< Object address: " + o.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(o)));
        return o.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(o));
    }

    public static String getArrayContents(boolean[] bs) {
        LOG.info("<<<<< Assert content: " + Arrays.toString(bs));
        return Arrays.toString(bs);
    }

    public static String getArrayContents(char[] cs) {
        LOG.info("<<<<< Assert content: " + Arrays.toString(cs));
        return Arrays.toString(cs);
    }

    public static String getArrayContents(byte[] bs) {
        LOG.info("<<<<< Assert content: " + Arrays.toString(bs));
        return Arrays.toString(bs);
    }

    public static String getArrayContents(int[] is) {
        LOG.info("<<<<< Assert content: " + Arrays.toString(is));
        return Arrays.toString(is);
    }

    public static String getArrayContents(float[] bs) {
        LOG.info("<<<<< Assert content: " + Arrays.toString(bs));
        return Arrays.toString(bs);
    }

    public static String getArrayContents(double[] bs) {
        LOG.info("<<<<< Assert content: " + Arrays.toString(bs));
        return Arrays.toString(bs);
    }

    public static String getArrayContents(long[] bs) {
        LOG.info("<<<<< Assert content: " + Arrays.toString(bs));
        return Arrays.toString(bs);
    }

    public static String getArrayContents(short[] bs) {
        LOG.info("<<<<< Assert content: " + Arrays.toString(bs));
        return Arrays.toString(bs);
    }

}

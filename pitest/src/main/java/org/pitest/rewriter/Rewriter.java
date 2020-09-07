package org.pitest.rewriter;

import java.io.BufferedReader;
import java.io.File;

public class Rewriter {

    static BufferedReader serializer;
    static String path = Properties.REWRITER_DIR + File.separator + "assertion-output";

    public static void assertArrayEquals(boolean[] expecteds, boolean[] actuals, String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(byte[] expecteds, byte[] actuals, String id_assertion) {
        Serializer.serialize(path,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(char[] expecteds, char[] actuals,
                                         String id_assertion) {
        Serializer.serialize(path,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(double[] expecteds, double[] actuals, double delta, String id_assertion) {
        Serializer.serialize(path,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals, delta)));
    }

    public static void assertArrayEquals(float[] expecteds, float[] actuals, float delta, String id_assertion) {
        Serializer.serialize(path,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals, delta)));
    }

    public static void assertArrayEquals(int[] expecteds, int[] actuals, String id_assertion) {
        Serializer.serialize(path,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(long[] expecteds, long[] actuals, String id_assertion) {
        Serializer.serialize(path,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(Object[] expecteds, Object[] actuals, String id_assertion) {
        Serializer.serialize(path,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(short[] expecteds, short[] actuals, String id_assertion) {
        Serializer
                .serialize(
                        path,
                        id_assertion + " "
                                + (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(String message, boolean[] expecteds,
                                         boolean[] actuals, String id_assertion) {
        Serializer
                .serialize(
                        path,
                        id_assertion + " "
                                + (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(String message, byte[] expecteds,
                                         byte[] actuals, String id_assertion) {
        Serializer
                .serialize(
                        path,
                        id_assertion + " "
                                + (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(String message, char[] expecteds,
                                         char[] actuals, String id_assertion) {
        Serializer
                .serialize(
                        path,
                        id_assertion + " "
                                + (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(String message, double[] expecteds,
                                         double[] actuals, double delta, String id_assertion) {
        Serializer.serialize(
                path,
                id_assertion + " "
                        + (Utility.getAssertRes(expecteds, actuals, delta)));
    }

    public static void assertArrayEquals(String message, float[] expecteds,
                                         float[] actuals, float delta, String id_assertion) {
        Serializer.serialize(
                path,
                id_assertion + " "
                        + (Utility.getAssertRes(expecteds, actuals, delta)));

    }

    public static void assertArrayEquals(String message, int[] expecteds,
                                         int[] actuals, String id_assertion) {
        Serializer
                .serialize(
                        path,
                        id_assertion + " "
                                + (Utility.getAssertRes(expecteds, actuals)));

    }

    public static void assertArrayEquals(String message, long[] expecteds,
                                         long[] actuals, String id_assertion) {
        Serializer
                .serialize(
                        path,
                        id_assertion + " "
                                + (Utility.getAssertRes(expecteds, actuals)));

    }

    public static void assertArrayEquals(String message, Object[] expecteds,
                                         Object[] actuals, String id_assertion) {
        Serializer
                .serialize(
                        path,
                        id_assertion + " "
                                + (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(String message, short[] expecteds,
                                         short[] actuals, String id_assertion) {
        Serializer
                .serialize(
                        path,
                        id_assertion + " "
                                + (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertEquals(double expected, double actual,
                                    String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + (actual == expected));
    }

    public static void assertEquals(int expected, int actual,
                                    String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + (actual == expected));
    }

    public static void assertEquals(boolean expected, boolean actual,
                                    String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + (actual == expected));
    }

    public static void assertEquals(double expected, double actual,
                                    double delta, String id_assertion) {
        Serializer.serialize(path,
                id_assertion + " " + (Math.abs(actual - expected) <= delta));
    }

    public static void assertEquals(float expected, float actual, float delta,
                                    String id_assertion) {
        Serializer.serialize(path,
                id_assertion + " " + (Math.abs(actual - expected) <= delta));
    }

    public static void assertEquals(long expected, long actual,
                                    String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + (actual == expected));

    }

    public static void assertEquals(String expected, String actual,
                                    String id_assertion) {
        Serializer.serialize(path,
                id_assertion + " " + (actual.equals(expected)));
    }

    public static void assertEquals(Object[] expecteds, Object[] actuals,
                                    String id_assertion) {
        Serializer
                .serialize(
                        path,
                        id_assertion + " "
                                + (Utility.getAssertRes(expecteds, actuals)));

    }

    public static void assertEquals(Object expected, Object actual,
                                    String id_assertion) {
        Serializer.serialize(path,
                id_assertion + " " + (actual.equals(expected)));

    }

    public static void assertEquals(String message, double expected,
                                    double actual, String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + (actual == expected));

    }

    public static void assertEquals(String message, double expected,
                                    double actual, double delta, String id_assertion) {
        Serializer.serialize(path,
                id_assertion + " " + (Math.abs(actual - expected) <= delta));
    }

    public static void assertEquals(String message, float expected,
                                    float actual, float delta, String id_assertion) {
        Serializer.serialize(path,
                id_assertion + " " + (Math.abs(actual - expected) <= delta));
    }

    public static void assertEquals(String message, long expected, long actual,
                                    String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + (actual == expected));
    }

    public static void assertEquals(String message, Object[] expecteds,
                                    Object[] actuals, String id_assertion) {
        Serializer
                .serialize(
                        path,
                        id_assertion + " "
                                + (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertEquals(String message, Object expected,
                                    Object actual, String id_assertion) {
        Serializer.serialize(path,
                id_assertion + " " + (expected.equals(actual)));

    }

    public static void assertFalse(boolean condition, String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + (!condition));
    }

    public static void assertFalse(String message, boolean condition,
                                   String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + (!condition));

    }

    public static void assertNotEquals(double unexpected, double actual,
                                       double delta, String id_assertion) {
        Serializer.serialize(path,
                id_assertion + " " + (Math.abs(actual - unexpected) > delta));

    }

    public static void assertNotEquals(float unexpected, float actual,
                                       float delta, String id_assertion) {
        Serializer.serialize(path,
                id_assertion + " " + (Math.abs(actual - unexpected) > delta));
    }

    public static void assertNotEquals(long unexpected, long actual,
                                       String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + (actual != unexpected));
    }

    public static void assertNotEquals(Object unexpected, Object actual,
                                       String id_assertion) {
        Serializer.serialize(path,
                id_assertion + " " + (!unexpected.equals(actual)));

    }

    public static void assertNotEquals(String message, double unexpected,
                                       double actual, double delta, String id_assertion) {
        Serializer.serialize(path,
                id_assertion + " " + (Math.abs(actual - unexpected) > delta));
    }

    public static void assertNotEquals(String message, float unexpected,
                                       float actual, float delta, String id_assertion) {
        Serializer.serialize(path,
                id_assertion + " " + (Math.abs(actual - unexpected) > delta));

    }

    public static void assertNotEquals(String message, long unexpected,
                                       long actual, String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + (unexpected != actual));

    }

    public static void assertNotEquals(String message, Object unexpected,
                                       Object actual, String id_assertion) {
        Serializer.serialize(path,
                id_assertion + " " + (!unexpected.equals(actual)));
    }

    public static void assertNotNull(Object object, String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + (object != null));

    }

    public static void assertNotNull(String message, Object object,
                                     String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + (object != null));
    }

    public static void assertNotSame(Object unexpected, Object actual,
                                     String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + (unexpected != actual));
    }

    public static void assertNotSame(String message, Object unexpected,
                                     Object actual, String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + (unexpected != actual));

    }

    public static void assertNull(Object object, String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + (object == null));

    }

    public static void assertNull(String message, Object object,
                                  String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + (object == null));
    }

    public static void assertSame(Object expected, Object actual,
                                  String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + (actual == expected));

    }

    public static void assertSame(String message, Object expected,
                                  Object actual, String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + (actual == expected));

    }

    public static void assertTrue(boolean condition, String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + condition);

    }

    public static void assertTrue(String message, boolean condition,
                                  String id_assertion) {
        Serializer.serialize(path, id_assertion + " " + condition);
    }

}

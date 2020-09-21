package org.pitest.rewriter;

import org.hamcrest.Matcher;

public class Rewriter {

    public static <T> void assertThat(String reason, T actual,
                                      Matcher<? super T> matcher, String id_assertion) {
        Serializer.serialize(id_assertion, matcher.matches(actual),
                actual);
    }

    public static <T> void assertThat(T actual, Matcher<? super T> matcher,
                                      String id_assertion) {
        Serializer.serialize(id_assertion, matcher.matches(actual),
                actual);
    }

    public static void fail(String id_assertion) {
        Serializer.serialize(id_assertion, false, "");
    }

    public static void fail(String message, String id_assertion) {
        Serializer.serialize(id_assertion, false, "");
    }

    public static void failNotEquals(String message, String id_assertion) {
        Serializer.serialize(id_assertion, false, "");
    }

    public static void failNotSame(String message, Object expected,
                                   Object actual, String id_assertion) {
        Serializer.serialize(id_assertion, false, "");
    }

    public static void failSame(String message, String id_assertion) {
        Serializer.serialize(id_assertion, false, "");
    }

    public static void assertArrayEquals(boolean[] expecteds, boolean[] actuals,
                                         String id_assertion) {
        Serializer.serialize(id_assertion,
                Utility.getAssertRes(expecteds, actuals),
                AssertParser.getArrayContents(expecteds) + ""
                        + AssertParser.getArrayContents(actuals));
    }

    public static void assertArrayEquals(byte[] expecteds, byte[] actuals,
                                         String id_assertion) {
        Serializer.serialize(id_assertion,
                Utility.getAssertRes(expecteds, actuals),
                AssertParser.getArrayContents(expecteds) + ""
                        + AssertParser.getArrayContents(actuals));
    }

    public static void assertArrayEquals(char[] expecteds, char[] actuals,
                                         String id_assertion) {
        Serializer.serialize(id_assertion,
                Utility.getAssertRes(expecteds, actuals),
                AssertParser.getArrayContents(expecteds) + ""
                        + AssertParser.getArrayContents(actuals));
    }

    public static void assertArrayEquals(double[] expecteds, double[] actuals,
                                         double delta, String id_assertion) {
        Serializer.serialize(id_assertion,
                Utility.getAssertRes(expecteds, actuals, delta),
                AssertParser.getArrayContents(expecteds) + ""
                        + AssertParser.getArrayContents(actuals));
    }

    public static void assertArrayEquals(float[] expecteds, float[] actuals,
                                         float delta, String id_assertion) {
        Serializer.serialize(id_assertion,
                Utility.getAssertRes(expecteds, actuals, delta),
                AssertParser.getArrayContents(expecteds) + ""
                        + AssertParser.getArrayContents(actuals));
    }

    public static void assertArrayEquals(int[] expecteds, int[] actuals,
                                         String id_assertion) {
        Serializer.serialize(id_assertion,
                Utility.getAssertRes(expecteds, actuals),
                AssertParser.getArrayContents(expecteds) + ""
                        + AssertParser.getArrayContents(actuals));
    }

    public static void assertArrayEquals(long[] expecteds, long[] actuals,
                                         String id_assertion) {
        Serializer.serialize(id_assertion,
                Utility.getAssertRes(expecteds, actuals),
                AssertParser.getArrayContents(expecteds) + ""
                        + AssertParser.getArrayContents(actuals));
    }

    public static void assertArrayEquals(Object[] expecteds, Object[] actuals,
                                         String id_assertion) {
        Serializer.serialize(id_assertion,
                Utility.getAssertRes(expecteds, actuals),
                expecteds,actuals);
    }

    public static void assertArrayEquals(short[] expecteds, short[] actuals,
                                         String id_assertion) {
        Serializer.serialize(id_assertion,
                Utility.getAssertRes(expecteds, actuals),
                AssertParser.getArrayContents(expecteds) + ""
                        + AssertParser.getArrayContents(actuals));
    }

    public static void assertArrayEquals(String message, boolean[] expecteds,
                                         boolean[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion,
                Utility.getAssertRes(expecteds, actuals),
                AssertParser.getArrayContents(expecteds) + ""
                        + AssertParser.getArrayContents(actuals));
    }

    public static void assertArrayEquals(String message, byte[] expecteds,
                                         byte[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion,
                (Utility.getAssertRes(expecteds, actuals)),
                AssertParser.getArrayContents(expecteds) + ""
                        + AssertParser.getArrayContents(actuals));
    }

    public static void assertArrayEquals(String message, char[] expecteds,
                                         char[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion,
                (Utility.getAssertRes(expecteds, actuals)),
                AssertParser.getArrayContents(expecteds) + ""
                        + AssertParser.getArrayContents(actuals));
    }

    public static void assertArrayEquals(String message, double[] expecteds,
                                         double[] actuals, double delta, String id_assertion) {
        Serializer.serialize(id_assertion,
                (Utility.getAssertRes(expecteds, actuals, delta)),
                AssertParser.getArrayContents(expecteds) + ""
                        + AssertParser.getArrayContents(actuals));
    }

    public static void assertArrayEquals(String message, float[] expecteds,
                                         float[] actuals, float delta, String id_assertion) {
        Serializer.serialize(id_assertion,
                (Utility.getAssertRes(expecteds, actuals, delta)),
                AssertParser.getArrayContents(expecteds) + ""
                        + AssertParser.getArrayContents(actuals));

    }

    public static void assertArrayEquals(String message, int[] expecteds,
                                         int[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion,
                (Utility.getAssertRes(expecteds, actuals)),
                AssertParser.getArrayContents(expecteds) + ""
                        + AssertParser.getArrayContents(actuals));

    }

    public static void assertArrayEquals(String message, long[] expecteds,
                                         long[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion,
                (Utility.getAssertRes(expecteds, actuals)),
                AssertParser.getArrayContents(expecteds) + ""
                        + AssertParser.getArrayContents(actuals));

    }

    public static void assertArrayEquals(String message, Object[] expecteds,
                                         Object[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion,
                (Utility.getAssertRes(expecteds, actuals)),
                expecteds,
                actuals);
    }

    public static void assertArrayEquals(String message, short[] expecteds,
                                         short[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion,
                (Utility.getAssertRes(expecteds, actuals)),
                AssertParser.getArrayContents(expecteds) + ""
                        + AssertParser.getArrayContents(actuals));
    }

    public static void assertEquals(short expected, short actual,
                                    String id_assertion) {
        Serializer.serialize(id_assertion, expected == actual,
                expected + "" + actual);
    }
    public static void assertEquals(String message, short expected, short actual,
                                    String id_assertion) {
        Serializer.serialize(id_assertion, expected == actual,
                expected + "" + actual);
    }

    public static void assertEquals(byte expected, byte actual,
                                    String id_assertion) {
        Serializer.serialize(id_assertion, expected == actual,
                expected + "" + actual);
    }


    public static void assertEquals(String message, byte expected, byte actual,
                                    String id_assertion) {
        Serializer.serialize(id_assertion, expected == actual,
                expected + "" + actual);
    }

    public static void assertEquals(double expected, double actual,
                                    String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected),
                expected + "" + actual);
    }

    public static void assertEquals(char expected, char actual,
                                    String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected),
                expected + "" + actual);
    }

    public static void assertEquals(String message, char expected, char actual,
                                    String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected),
                expected + "" + actual);
    }

    public static void assertEquals(int expected, int actual,
                                    String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected),
                expected + "" + actual);
    }

    public static void assertEquals(String message, int expected, int actual,
                                    String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected),
                expected + "" + actual);
    }

    public static void assertEquals(boolean expected, boolean actual,
                                    String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected),
                expected + "" + actual);
    }

    public static void assertEquals(String message, boolean expected,
                                    boolean actual, String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected),
                expected + "" + actual);
    }

    public static void assertEquals(double expected, double actual,
                                    double delta, String id_assertion) {
        Serializer.serialize(id_assertion,
                (Math.abs(actual - expected) <= delta), expected + "" + actual);
    }

    public static void assertEquals(float expected, float actual, float delta,
                                    String id_assertion) {
        Serializer.serialize(id_assertion,
                (Math.abs(actual - expected) <= delta), expected + "" + actual);
    }

    public static void assertEquals(long expected, long actual,
                                    String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected),
                expected + "" + actual);

    }

    public static void assertEquals(String expected, String actual,
                                    String id_assertion) {
        // bug fix
        Serializer.serialize(id_assertion,
                Utility.compareObject(expected, actual),
                expected + "" + actual);
    }

    public static void assertEquals(String message, String expected,
                                    String actual, String id_assertion) {
        // bug fix
        Serializer.serialize(id_assertion,
                Utility.compareObject(expected, actual),
                expected + "" + actual);
    }

    public static void assertEquals(Object[] expecteds, Object[] actuals,
                                    String id_assertion) {
        Serializer.serialize(id_assertion,
                (Utility.getAssertRes(expecteds, actuals)),
                expecteds,actuals);

    }

    public static void assertEquals(Object expected, Object actual,
                                    String id_assertion) {
        Serializer.serialize(id_assertion,
                (Utility.compareObject(expected, actual)),
                expected,actual);
    }

    public static void assertEquals(String message, double expected,
                                    double actual, String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected),
                expected + "" + actual);

    }

    public static void assertEquals(String message, double expected,
                                    double actual, double delta, String id_assertion) {
        Serializer.serialize(id_assertion,
                (Math.abs(actual - expected) <= delta), expected + "" + actual);
    }

    public static void assertEquals(String message, float expected,
                                    float actual, float delta, String id_assertion) {
        Serializer.serialize(id_assertion,
                (Math.abs(actual - expected) <= delta), expected + "" + actual);
    }

    public static void assertEquals(String message, long expected, long actual,
                                    String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected),
                expected + "" + actual);
    }

    public static void assertEquals(String message, Object[] expecteds,
                                    Object[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion,
                (Utility.getAssertRes(expecteds, actuals)),
                expecteds,actuals);
    }

    public static void assertEquals(String message, Object expected,
                                    Object actual, String id_assertion) {
        Serializer.serialize(id_assertion,
                (Utility.compareObject(expected, actual)),
                expected,actual);
    }

    public static void assertFalse(boolean condition, String id_assertion) {
        Serializer.serialize(id_assertion, (!condition), condition + "");
    }

    public static void assertFalse(String message, boolean condition,
                                   String id_assertion) {
        Serializer.serialize(id_assertion, (!condition), condition + "");

    }

    public static void assertNotEquals(double unexpected, double actual,
                                       double delta, String id_assertion) {
        Serializer.serialize(id_assertion,
                (Math.abs(actual - unexpected) > delta),
                unexpected + "" + actual);

    }

    public static void assertNotEquals(float unexpected, float actual,
                                       float delta, String id_assertion) {
        Serializer.serialize(id_assertion,
                (Math.abs(actual - unexpected) > delta),
                unexpected + "" + actual);
    }

    public static void assertNotEquals(long unexpected, long actual,
                                       String id_assertion) {
        Serializer.serialize(id_assertion, (actual != unexpected),
                unexpected + "" + actual);
    }

    public static void assertNotEquals(Object unexpected, Object actual,
                                       String id_assertion) {
        Serializer.serialize(id_assertion,
                (!Utility.compareObject(unexpected, actual)),
                unexpected,actual);

    }

    public static void assertNotEquals(String message, double unexpected,
                                       double actual, double delta, String id_assertion) {
        Serializer.serialize(id_assertion,
                (Math.abs(actual - unexpected) > delta),
                unexpected + "" + actual);
    }

    public static void assertNotEquals(String message, float unexpected,
                                       float actual, float delta, String id_assertion) {
        Serializer.serialize(id_assertion,
                (Math.abs(actual - unexpected) > delta),
                unexpected + "" + actual);

    }

    public static void assertNotEquals(String message, long unexpected,
                                       long actual, String id_assertion) {
        Serializer.serialize(id_assertion, (unexpected != actual),
                unexpected + "" + actual);

    }

    public static void assertNotEquals(String message, Object unexpected,
                                       Object actual, String id_assertion) {
        Serializer.serialize(id_assertion,
                (!Utility.compareObject(unexpected, actual)),
                unexpected,actual);
    }

    public static void assertNotNull(Object object, String id_assertion) {
        Serializer.serialize(id_assertion, (object != null),
                object);
    }

    public static void assertNotNull(String message, Object object,
                                     String id_assertion) {
        Serializer.serialize(id_assertion, (object != null),
                object);
    }

    public static void assertNotSame(Object unexpected, Object actual,
                                     String id_assertion) {
        Serializer.serialize(id_assertion, (unexpected != actual),
                unexpected,actual);
    }

    public static void assertNotSame(String message, Object unexpected,
                                     Object actual, String id_assertion) {
        Serializer.serialize(id_assertion, (unexpected != actual),
                unexpected,actual);

    }

    public static void assertNull(Object object, String id_assertion) {
        Serializer.serialize(id_assertion, (object == null),
                object);

    }

    public static void assertNull(String message, Object object,
                                  String id_assertion) {
        Serializer.serialize(id_assertion, (object == null),
                object);
    }

    public static void assertSame(Object expected, Object actual,
                                  String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected),
                expected,actual);

    }

    public static void assertSame(String message, Object expected,
                                  Object actual, String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected),
                expected,actual);
    }

    public static void assertTrue(boolean condition, String id_assertion) {
        Serializer.serialize(id_assertion, condition, condition + "");

    }

    public static void assertTrue(String message, boolean condition,
                                  String id_assertion) {
        Serializer.serialize(id_assertion, condition, condition + "");
    }

    public static void assertFailSame() {

    }

}

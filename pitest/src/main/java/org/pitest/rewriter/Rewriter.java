package org.pitest.rewriter;

//import org.hamcrest.Matcher;

public class Rewriter {

//    original version under comments. Versions that is using PATH var for serialization destination
//    public static final String PATH = Properties.REWRITER_DIR;
    public static final String FILENAME = "assertion-output";
//    private static final Logger LOG = Log.getLogger();
//    static BufferedReader serializer;

//    public static void print(String info) {
//        Serializer.serialize(PATH, info);
//    }

//    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher, String id_assertion){
//        Serializer.serialize(id_assertion, matcher.matches(actual));
//    }
//
//    public static <T> void assertThat(T actual, Matcher<? super T> matcher, String id_assertion){
//        Serializer.serialize(id_assertion, matcher.matches(actual));
//    }

    public static void fail(String id_assertion) {
        Serializer.serialize(id_assertion, false);
    }

    public static void fail(String message, String id_assertion) {
        Serializer.serialize(id_assertion, false);
    }

    public static void failNotEquals(String message, String id_assertion) {
        Serializer.serialize(id_assertion, false);
    }

    public static void failNotSame(String message, Object expected, Object actual, String id_assertion) {
        Serializer.serialize(id_assertion, false);
    }

    public static void failSame(String message, String id_assertion) {
        Serializer.serialize(id_assertion, false);
    }

    public static void assertArrayEquals(boolean[] expecteds, boolean[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion, (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(byte[] expecteds, byte[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion, (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(char[] expecteds, char[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion, (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(double[] expecteds, double[] actuals, double delta, String id_assertion) {
        Serializer.serialize(id_assertion, (Utility.getAssertRes(expecteds, actuals, delta)));
    }

    public static void assertArrayEquals(float[] expecteds, float[] actuals, float delta, String id_assertion) {
        Serializer.serialize(id_assertion, (Utility.getAssertRes(expecteds, actuals, delta)));
    }

    public static void assertArrayEquals(int[] expecteds, int[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion, (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(long[] expecteds, long[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion, (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(Object[] expecteds, Object[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion, (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(short[] expecteds, short[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion, (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(String message, boolean[] expecteds, boolean[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion, (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(String message, byte[] expecteds, byte[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion, (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(String message, char[] expecteds, char[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion, (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(String message, double[] expecteds, double[] actuals, double delta, String id_assertion) {
        Serializer.serialize(id_assertion, (Utility.getAssertRes(expecteds, actuals, delta)));
    }

    public static void assertArrayEquals(String message, float[] expecteds, float[] actuals, float delta, String id_assertion) {
        Serializer.serialize(id_assertion, (Utility.getAssertRes(expecteds, actuals, delta)));
    }

    public static void assertArrayEquals(String message, int[] expecteds, int[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion, (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(String message, long[] expecteds, long[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion, (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(String message, Object[] expecteds, Object[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion, (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertArrayEquals(String message, short[] expecteds, short[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion, (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertEquals(byte expected, byte actual, String id_assertion) {
        Serializer.serialize(id_assertion, expected == actual);
    }

    public static void assertEquals(String message, byte expected, byte actual, String id_assertion) {
        Serializer.serialize(id_assertion, expected == actual);
    }

    public static void assertEquals(double expected, double actual, String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected));
    }

    public static void assertEquals(char expected, char actual, String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected));
    }

    public static void assertEquals(String message, char expected, char actual, String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected));
    }

    public static void assertEquals(int expected, int actual, String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected));
    }

    public static void assertEquals(String message, int expected, int actual, String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected));
    }

    public static void assertEquals(boolean expected, boolean actual, String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected));
    }

    public static void assertEquals(String message, boolean expected, boolean actual, String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected));
    }

    public static void assertEquals(double expected, double actual, double delta, String id_assertion) {
        Serializer.serialize(id_assertion, (Math.abs(actual - expected) <= delta));
    }

    public static void assertEquals(float expected, float actual, float delta, String id_assertion) {
        Serializer.serialize(id_assertion, (Math.abs(actual - expected) <= delta));
    }

    public static void assertEquals(long expected, long actual, String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected));

    }

    public static void assertEquals(String expected, String actual, String id_assertion) {
        Serializer.serialize(id_assertion, (actual.equals(expected)));
    }

    public static void assertEquals(String message, String expected, String actual, String id_assertion) {
        Serializer.serialize(id_assertion, (actual.equals(expected)));
    }

    public static void assertEquals(Object[] expecteds, Object[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion, (Utility.getAssertRes(expecteds, actuals)));

    }

    public static void assertEquals(Object expected, Object actual, String id_assertion) {
        Serializer.serialize(id_assertion, (actual.equals(expected)));

    }

    public static void assertEquals(String message, double expected, double actual, String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected));

    }

    public static void assertEquals(String message, double expected, double actual, double delta, String id_assertion) {
        Serializer.serialize(id_assertion, (Math.abs(actual - expected) <= delta));
    }

    public static void assertEquals(String message, float expected, float actual, float delta, String id_assertion) {
        Serializer.serialize(id_assertion, (Math.abs(actual - expected) <= delta));
    }

    public static void assertEquals(String message, long expected, long actual,
                                    String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected));
    }

    public static void assertEquals(String message, Object[] expecteds, Object[] actuals, String id_assertion) {
        Serializer.serialize(id_assertion, (Utility.getAssertRes(expecteds, actuals)));
    }

    public static void assertEquals(String message, Object expected, Object actual, String id_assertion) {
        Serializer.serialize(id_assertion, (expected.equals(actual)));

    }

    public static void assertFalse(boolean condition, String id_assertion) {
        Serializer.serialize(id_assertion, (!condition));
    }

    public static void assertFalse(String message, boolean condition, String id_assertion) {
        Serializer.serialize(id_assertion, (!condition));

    }

    public static void assertNotEquals(double unexpected, double actual, double delta, String id_assertion) {
        Serializer.serialize(id_assertion, (Math.abs(actual - unexpected) > delta));

    }

    public static void assertNotEquals(float unexpected, float actual, float delta, String id_assertion) {
        Serializer.serialize(id_assertion, (Math.abs(actual - unexpected) > delta));
    }

    public static void assertNotEquals(long unexpected, long actual, String id_assertion) {
        Serializer.serialize(id_assertion, (actual != unexpected));
    }

    public static void assertNotEquals(Object unexpected, Object actual, String id_assertion) {
        Serializer.serialize(id_assertion, (!unexpected.equals(actual)));

    }

    public static void assertNotEquals(String message, double unexpected, double actual, double delta, String id_assertion) {
        Serializer.serialize(id_assertion, (Math.abs(actual - unexpected) > delta));
    }

    public static void assertNotEquals(String message, float unexpected, float actual, float delta, String id_assertion) {
        Serializer.serialize(id_assertion, (Math.abs(actual - unexpected) > delta));

    }

    public static void assertNotEquals(String message, long unexpected, long actual, String id_assertion) {
        Serializer.serialize(id_assertion, (unexpected != actual));

    }

    public static void assertNotEquals(String message, Object unexpected, Object actual, String id_assertion) {
        Serializer.serialize(id_assertion, (!unexpected.equals(actual)));
    }

    public static void assertNotNull(Object object, String id_assertion) {
        Serializer.serialize(id_assertion, (object != null));

    }

    public static void assertNotNull(String message, Object object, String id_assertion) {
        Serializer.serialize(id_assertion, (object != null));
    }

    public static void assertNotSame(Object unexpected, Object actual, String id_assertion) {
        Serializer.serialize(id_assertion, (unexpected != actual));
    }

    public static void assertNotSame(String message, Object unexpected, Object actual, String id_assertion) {
        Serializer.serialize(id_assertion, (unexpected != actual));

    }

    public static void assertNull(Object object, String id_assertion) {
        Serializer.serialize(id_assertion, (object == null));

    }

    public static void assertNull(String message, Object object, String id_assertion) {
        Serializer.serialize(id_assertion, (object == null));
    }

    public static void assertSame(Object expected, Object actual, String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected));

    }

    public static void assertSame(String message, Object expected, Object actual, String id_assertion) {
        Serializer.serialize(id_assertion, (actual == expected));

    }

    public static void assertTrue(boolean condition, String id_assertion) {
        Serializer.serialize(id_assertion, condition);

    }

    public static void assertTrue(String message, boolean condition, String id_assertion) {
        Serializer.serialize(id_assertion, condition);
    }

//    public static void assertArrayEquals(boolean[] expecteds, boolean[] actuals, String id_assertion) {
//        Serializer.serialize(PATH, id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
//    }
//
//    public static void assertArrayEquals(byte[] expecteds, byte[] actuals, String id_assertion) {
//        LOG.info("Expected: " + expecteds);
//        LOG.info("Actuals: " + actuals);
//        LOG.info("id_assertion: " + id_assertion);
//        Serializer.serialize(PATH,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
//    }
//
//    public static void assertArrayEquals(char[] expecteds, char[] actuals, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
//    }
//
//    public static void assertArrayEquals(double[] expecteds, double[] actuals, double delta, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals, delta)));
//    }
//
//    public static void assertArrayEquals(float[] expecteds, float[] actuals, float delta, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals, delta)));
//    }
//
//    public static void assertArrayEquals(int[] expecteds, int[] actuals, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
//    }
//
//    public static void assertArrayEquals(long[] expecteds, long[] actuals, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
//    }
//
//    public static void assertArrayEquals(Object[] expecteds, Object[] actuals, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
//    }
//
//    public static void assertArrayEquals(short[] expecteds, short[] actuals, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
//    }
//
//    public static void assertArrayEquals(String message, boolean[] expecteds, boolean[] actuals, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
//    }
//
//    public static void assertArrayEquals(String message, byte[] expecteds, byte[] actuals, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
//    }
//
//    public static void assertArrayEquals(String message, char[] expecteds, char[] actuals, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
//    }
//
//    public static void assertArrayEquals(String message, double[] expecteds, double[] actuals, double delta, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals, delta)));
//    }
//
//    public static void assertArrayEquals(String message, float[] expecteds, float[] actuals, float delta, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals, delta)));
//
//    }
//
//    public static void assertArrayEquals(String message, int[] expecteds, int[] actuals, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
//
//    }
//
//    public static void assertArrayEquals(String message, long[] expecteds, long[] actuals, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
//
//    }
//
//    public static void assertArrayEquals(String message, Object[] expecteds, Object[] actuals, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
//    }
//
//    public static void assertArrayEquals(String message, short[] expecteds, short[] actuals, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
//    }
//
//    public static void assertEquals(double expected, double actual, String id_assertion) {
//        LOG.info("Expected: " + expected);
//        LOG.info("Actual: " + actual);
//        LOG.info("id_assertion: " + id_assertion);
//        Serializer.serialize(PATH, id_assertion + " " + (actual == expected));
//    }
//
//    public static void assertEquals(int expected, int actual, String id_assertion) {
//        LOG.info("Expected: " + expected);
//        LOG.info("Actual: " + actual);
//        LOG.info("id_assertion: " + id_assertion);
//        Serializer.serialize(PATH, id_assertion + " " + (actual == expected));
//    }
//
//    public static void assertEquals(boolean expected, boolean actual, String id_assertion) {
//        LOG.info("Expected: " + expected);
//        LOG.info("Actual: " + actual);
//        LOG.info("id_assertion: " + id_assertion);
//        Serializer.serialize(PATH, id_assertion + " " + (actual == expected));
//    }
//
//    public static void assertEquals(double expected, double actual, double delta, String id_assertion) {
//        LOG.info("Expected: " + expected);
//        LOG.info("Actual: " + actual);
//        LOG.info("id_assertion: " + id_assertion);
//        Serializer.serialize(PATH,id_assertion + " " + (Math.abs(actual - expected) <= delta));
//    }
//
//    public static void assertEquals(float expected, float actual, float delta, String id_assertion) {
//        LOG.info("Expected: " + expected);
//        LOG.info("Actual: " + actual);
//        LOG.info("id_assertion: " + id_assertion);
//        Serializer.serialize(PATH,id_assertion + " " + (Math.abs(actual - expected) <= delta));
//    }
//
//    public static void assertEquals(long expected, long actual, String id_assertion) {
//        LOG.info("Expected: " + expected);
//        LOG.info("Actual: " + actual);
//        LOG.info("id_assertion: " + id_assertion);
//        Serializer.serialize(PATH, id_assertion + " " + (actual == expected));
//
//    }
//
//    public static void assertEquals(String expected, String actual, String id_assertion) {
//        LOG.info("Expected: " + expected);
//        LOG.info("Actual: " + actual);
//        LOG.info("id_assertion: " + id_assertion);
//        Serializer.serialize(PATH,id_assertion + " " + (actual.equals(expected)));
//    }
//
//    public static void assertEquals(Object[] expecteds, Object[] actuals, String id_assertion) {
//        LOG.info("Expected: " + expecteds);
//        LOG.info("Actual: " + actuals);
//        LOG.info("id_assertion: " + id_assertion);
//        Serializer.serialize(PATH,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
//
//    }
//
//    public static void assertEquals(Object expected, Object actual, String id_assertion) {
//        LOG.info("Expected: " + expected);
//        LOG.info("Actual: " + actual);
//        LOG.info("id_assertion: " + id_assertion);
//        Serializer.serialize(PATH,id_assertion + " " + (actual.equals(expected)));
//
//    }
//
//    public static void assertEquals(String message, double expected, double actual, String id_assertion) {
//        LOG.info("Message: " + message);
//        LOG.info("Expected: " + expected);
//        LOG.info("Actual: " + actual);
//        LOG.info("id_assertion: " + id_assertion);
//        Serializer.serialize(PATH, id_assertion + " " + (actual == expected));
//
//    }
//
//    public static void assertEquals(String message, double expected, double actual, double delta, String id_assertion) {
//        LOG.info("Message: " + message);
//        LOG.info("Expected: " + expected);
//        LOG.info("Actual: " + actual);
//        LOG.info("id_assertion: " + id_assertion);
//        Serializer.serialize(PATH,id_assertion + " " + (Math.abs(actual - expected) <= delta));
//    }
//
//    public static void assertEquals(String message, float expected, float actual, float delta, String id_assertion) {
//        LOG.info("Message: " + message);
//        LOG.info("Expected: " + expected);
//        LOG.info("Actual: " + actual);
//        LOG.info("id_assertion: " + id_assertion);
//        Serializer.serialize(PATH,id_assertion + " " + (Math.abs(actual - expected) <= delta));
//    }
//
//    public static void assertEquals(String message, long expected, long actual, String id_assertion) {
//        LOG.info("Message: " + message);
//        LOG.info("Expected: " + expected);
//        LOG.info("Actual: " + actual);
//        LOG.info("id_assertion: " + id_assertion);
//        Serializer.serialize(PATH, id_assertion + " " + (actual == expected));
//    }
//
//    public static void assertEquals(String message, Object[] expecteds, Object[] actuals, String id_assertion) {
//        LOG.info("Message: " + message);
//        LOG.info("Expected: " + expecteds);
//        LOG.info("Actual: " + actuals);
//        LOG.info("id_assertion: " + id_assertion);
//        Serializer.serialize(PATH,id_assertion + " " + (Utility.getAssertRes(expecteds, actuals)));
//    }
//
//    public static void assertEquals(String message, Object expected, Object actual, String id_assertion) {
//        LOG.info("Message: " + message);
//        LOG.info("Expected: " + expected);
//        LOG.info("Actual: " + actual);
//        LOG.info("id_assertion: " + id_assertion);
//        Serializer.serialize(PATH,id_assertion + " " + (expected.equals(actual)));
//
//    }
//
//    public static void assertFalse(boolean condition, String id_assertion) {
//        Serializer.serialize(PATH, id_assertion + " " + (!condition));
//    }
//
//    public static void assertFalse(String message, boolean condition, String id_assertion) {
//        Serializer.serialize(PATH, id_assertion + " " + (!condition));
//
//    }
//
//    public static void assertNotEquals(double unexpected, double actual, double delta, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (Math.abs(actual - unexpected) > delta));
//
//    }
//
//    public static void assertNotEquals(float unexpected, float actual, float delta, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (Math.abs(actual - unexpected) > delta));
//    }
//
//    public static void assertNotEquals(long unexpected, long actual, String id_assertion) {
//        Serializer.serialize(PATH, id_assertion + " " + (actual != unexpected));
//    }
//
//    public static void assertNotEquals(Object unexpected, Object actual, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (!unexpected.equals(actual)));
//
//    }
//
//    public static void assertNotEquals(String message, double unexpected, double actual, double delta, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (Math.abs(actual - unexpected) > delta));
//    }
//
//    public static void assertNotEquals(String message, float unexpected, float actual, float delta, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (Math.abs(actual - unexpected) > delta));
//
//    }
//
//    public static void assertNotEquals(String message, long unexpected, long actual, String id_assertion) {
//        Serializer.serialize(PATH, id_assertion + " " + (unexpected != actual));
//
//    }
//
//    public static void assertNotEquals(String message, Object unexpected, Object actual, String id_assertion) {
//        Serializer.serialize(PATH,id_assertion + " " + (!unexpected.equals(actual)));
//    }
//
//    public static void assertNotNull(Object object, String id_assertion) {
//        Serializer.serialize(PATH, id_assertion + " " + (object != null));
//
//    }
//
//    public static void assertNotNull(String message, Object object, String id_assertion) {
//        Serializer.serialize(PATH, id_assertion + " " + (object != null));
//    }
//
//    public static void assertNotSame(Object unexpected, Object actual, String id_assertion) {
//        Serializer.serialize(PATH, id_assertion + " " + (unexpected != actual));
//    }
//
//    public static void assertNotSame(String message, Object unexpected, Object actual, String id_assertion) {
//        Serializer.serialize(PATH, id_assertion + " " + (unexpected != actual));
//
//    }
//
//    public static void assertNull(Object object, String id_assertion) {
//        Serializer.serialize(PATH, id_assertion + " " + (object == null));
//
//    }
//
//    public static void assertNull(String message, Object object, String id_assertion) {
//        Serializer.serialize(PATH, id_assertion + " " + (object == null));
//    }
//
//    public static void assertSame(Object expected, Object actual, String id_assertion) {
//        Serializer.serialize(PATH, id_assertion + " " + (actual == expected));
//
//    }
//
//    public static void assertSame(String message, Object expected, Object actual, String id_assertion) {
//        Serializer.serialize(PATH, id_assertion + " " + (actual == expected));
//
//    }
//
//    public static void assertTrue(boolean condition, String id_assertion) {
//        LOG.info("Condition: " + condition);
//        LOG.info("id_assertion: " + id_assertion);
//        Serializer.serialize(PATH, id_assertion + " " + condition);
//
//    }
//
//    public static void assertTrue(String message, boolean condition, String id_assertion) {
//        LOG.info("Message: " + message);
//        LOG.info("Condition: " + condition);
//        LOG.info("id_assertion: " + id_assertion);
//        Serializer.serialize(PATH, id_assertion + " " + condition);
//    }

}

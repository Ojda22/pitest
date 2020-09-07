package org.pitest.rewriter;

public class Utility {

    public static boolean getAssertRes(int[] expecteds, int[] actuals) {
        boolean value = true;
        if (expecteds.length != actuals.length) {
            value = false;
        } else {
            for (int i = 0; i < expecteds.length; i++) {
                if (expecteds[i] != actuals[i]) {
                    value = false;
                    break;
                }
            }
        }
        return value;
    }

    public static boolean getAssertRes(short[] expecteds, short[] actuals) {
        boolean value = true;
        if (expecteds.length != actuals.length) {
            value = false;
        } else {
            for (int i = 0; i < expecteds.length; i++) {
                if (expecteds[i] != actuals[i]) {
                    value = false;
                    break;
                }
            }
        }
        return value;
    }

    public static boolean getAssertRes(long[] expecteds, long[] actuals) {
        boolean value = true;
        if (expecteds.length != actuals.length) {
            value = false;
        } else {
            for (int i = 0; i < expecteds.length; i++) {
                if (expecteds[i] != actuals[i]) {
                    value = false;
                    break;
                }
            }
        }
        return value;
    }

    public static boolean getAssertRes(boolean[] expecteds, boolean[] actuals) {
        boolean value = true;
        if (expecteds.length != actuals.length) {
            value = false;
        } else {
            for (int i = 0; i < expecteds.length; i++) {
                if (expecteds[i] != actuals[i]) {
                    value = false;
                    break;
                }
            }
        }
        return value;
    }

    public static boolean getAssertRes(byte[] expecteds, byte[] actuals) {
        boolean value = true;
        if (expecteds.length != actuals.length) {
            value = false;
        } else {
            for (int i = 0; i < expecteds.length; i++) {
                if (expecteds[i] != actuals[i]) {
                    value = false;
                    break;
                }
            }
        }
        return value;
    }

    public static boolean getAssertRes(double[] expecteds, double[] actuals) {
        boolean value = true;
        if (expecteds.length != actuals.length) {
            value = false;
        } else {
            for (int i = 0; i < expecteds.length; i++) {
                if (expecteds[i] != actuals[i]) {
                    value = false;
                    break;
                }
            }
        }
        return value;
    }

    public static boolean getAssertRes(char[] expecteds, char[] actuals) {
        boolean value = true;
        if (expecteds.length != actuals.length) {
            value = false;
        } else {
            for (int i = 0; i < expecteds.length; i++) {
                if (expecteds[i] != actuals[i]) {
                    value = false;
                    break;
                }
            }
        }
        return value;
    }

    public static boolean getAssertRes(Object[] expecteds, Object[] actuals) {
        boolean value = true;
        if (expecteds.length != actuals.length) {
            value = false;
        } else {
            for (int i = 0; i < expecteds.length; i++) {
                if (!expecteds[i].equals(actuals[i])) {
                    value = false;
                    break;
                }
            }
        }
        return value;
    }

    public static boolean getAssertRes(double[] expecteds, double[] actuals, double delta) {
        boolean value = true;
        if (expecteds.length != actuals.length) {
            value = false;
        } else {
            for (int i = 0; i < expecteds.length; i++) {
                if (Math.abs(expecteds[i] - actuals[i]) > delta) {
                    value = false;
                    break;
                }
            }
        }
        return value;
    }

    public static boolean getAssertRes(int[] expecteds, int[] actuals, int delta) {
        boolean value = true;
        if (expecteds.length != actuals.length) {
            value = false;
        } else {
            for (int i = 0; i < expecteds.length; i++) {
                if (Math.abs(expecteds[i] - actuals[i]) > delta) {
                    value = false;
                    break;
                }
            }
        }
        return value;
    }

    public static boolean getAssertRes(float[] expecteds, float[] actuals, float delta) {
        boolean value = true;
        if (expecteds.length != actuals.length) {
            value = false;
        } else {
            for (int i = 0; i < expecteds.length; i++) {
                if (Math.abs(expecteds[i] - actuals[i]) > delta) {
                    value = false;
                    break;
                }
            }
        }
        return value;
    }

}

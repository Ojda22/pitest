package org.pitest.rewriter;

public class Utility {

    public static boolean getAssertRes(int[] expecteds, int[] actuals) {
        if (expecteds == null || actuals == null) {
            return expecteds == actuals;
        }
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
        if (expecteds == null || actuals == null) {
            return expecteds == actuals;
        }
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
        if (expecteds == null || actuals == null) {
            return expecteds == actuals;
        }
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
        if (expecteds == null || actuals == null) {
            return expecteds == actuals;
        }
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
        if (expecteds == null || actuals == null) {
            return expecteds == actuals;
        }
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
        if (expecteds == null || actuals == null) {
            return expecteds == actuals;
        }
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
        if (expecteds == null || actuals == null) {
            return expecteds == actuals;
        }
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
        if (expecteds == null || actuals == null) {
            return expecteds == actuals;
        }
        boolean value = true;
        if (expecteds.length != actuals.length) {
            value = false;
        } else {
            for (int i = 0; i < expecteds.length; i++) {
                if (expecteds[i] == null){
                    if (actuals[i] != null){
                        value = false;
                        break;
                    }
                }else if (!expecteds[i].equals(actuals[i])){
                    value = false;
                    break;
                }
            }
        }
        return value;
    }

    public static boolean getAssertRes(double[] expecteds, double[] actuals, double delta) {
        if (expecteds == null || actuals == null) {
            return expecteds == actuals;
        }
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
        if (expecteds == null || actuals == null) {
            return expecteds == actuals;
        }
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
        if (expecteds == null || actuals == null) {
            return expecteds == actuals;
        }
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

    public static boolean compareObject(Object expected, Object actual) {
        if (expected == null || actual == null) {
            return expected == actual;
        }
        return expected.equals(actual);
    }

}

public class MutationTesterMutated {
    public static int test_arith(int b, int c) {
        int result = b - c;

        return result;
    }

    public static int test_multi_arith(int b, int c, int d) {
        int result = b - c + d;

        return result;
    }

    public static boolean test_logical(boolean b, boolean c) {
        boolean result = b & c;

        return result;
    }
}

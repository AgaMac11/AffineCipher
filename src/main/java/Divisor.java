public class Divisor {

    private final int m = 26;
    private int greatestCommonDivisor;

    public Divisor() {
    }

    public int greatestCommonDivisor(int a) {
        for (int i = 1; i <= a && i <= m; i++) {
            if (a % i == 0 && m % i == 0) {
                greatestCommonDivisor = i;
            }
        }
        return greatestCommonDivisor;
    }


}

public class StepCounter {
    public static long CalculateCount(double a, double b, double esp){
        double temp;
        if (a > b) {
            temp = a;
            a = b;
            b = temp;
        }
        long n = (long)Math.round(((b - a)/(Math.pow(esp, 0.25))));
        if ((n & 1) != 0) n++;
            return Math.abs(n);
    }
    public static double CalculateStep(double a, double b, long count){
        return (Math.abs((a-b)/count));
    }
}

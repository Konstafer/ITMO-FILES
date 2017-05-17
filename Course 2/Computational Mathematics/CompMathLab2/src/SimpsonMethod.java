import java.util.ArrayList;
import java.util.List;

public class SimpsonMethod {

    public static double sum (double step, int count, Operationable func) {

        List<Point> points = new ArrayList<>();
        double integralValue = 0.0;
        int j = 0;
        double d = 0;
        while (j!= count){
            d += step;
            double x = d;
            double y = func.calculate(x);
            points.add(new Point(x,y));
            j++;
        }

        for (int i = 2; i < count; i += 2) {
            double h = points.get(i).getX() - points.get(i - 2).getX();
            integralValue += h * (points.get(i - 2).getY() + 4 * points.get(i - 1).getY() + points.get(i).getY());
        }

        integralValue /= 6.0;
        if ((Double.isInfinite(integralValue) || Double.isNaN(integralValue))) throw new NullPointerException("Unsolved");

        return Math.abs(integralValue);
    }

}

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Boolean flag = true;
        double esp;
        double a = 0;
        double b = 0;
        double step = 0.0;
        long count = 0;

        Operationable operation = x -> 0;

        while (flag) {
            System.out.println("Enter the A value:");
            a = scanner.nextDouble();
            System.out.println("Enter the B value:");
            b = scanner.nextDouble();
            System.out.println("Enter the accuracy value:");
            esp = scanner.nextDouble();
            flag = false;
            count = StepCounter.CalculateCount(a, b, esp);
            step = StepCounter.CalculateStep(a, b , count);

            if (count >= Integer.MAX_VALUE || (a == b))
            {
                System.out.println("Wrong data. Try again.");
                flag = true;
            }
        }
        flag = true;

        while (flag) {
            flag = false;
            System.out.println("Choose function for integrating:");
            System.out.println("Case 1:\t\t∫ xdx\nCase 2:\t\t∫ x³dx/sin(x)\nCase 3:\t\t∫ (x²+10)dx/(x+1)");
            int cs = scanner.nextInt();
            switch (cs) {
                case 1: {
                    operation = (x) -> x;
                    break;
                }
                case 2: {
                    operation = (x) -> (x*x*x) / (Math.sin(x));
                    break;
                }
                case 3: {
                    operation = x -> (x*x + 10) / (x + 1.0);
                    break;
                }
                default:{
                    System.out.println("Wrong input.Try again.");
                    flag = true;
                    break;
                }
            }

        }

        double result1 = SimpsonMethod.sum(step, (int)count, operation);
        double result2 = SimpsonMethod.sum(step*2, (int)count/2, operation);
        System.out.print("Solution:\t\t");
        System.out.println(result1);
        System.out.print("The number of partitions:\t\t");
        System.out.println(count);
        System.out.print("Measurement error:\t\t");
        System.out.println(Math.abs(result1-result2)/15);
        scanner.close();
    }
}

interface Operationable{
    double calculate(double x);
}
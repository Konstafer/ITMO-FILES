package p1;

import java.util.*;

public class RandomMatrix {


    static private double[][] generateMatrix(int size) {
        Random rand = new Random();
        double[][] matrix = new double[size][size];
        int maxValue = 100;
        int minValue = -100;

        for (double[] arr : matrix) {
            for (int i = 0; i < arr.length; ++i) {
                arr[i] = rand.nextInt(maxValue - minValue + 1) + minValue;
            }
        }
        return matrix;
    }

    static private double[] generateB(int size) {
        Random rand = new Random();
        double[] bArr = new double[size];
        int maxValue = 100;
        int minValue = -100;

        for (int i = 0; i < size; ++i) {
            bArr[i] = rand.nextInt(maxValue - minValue + 1) + minValue;
        }
        return bArr;
    }

    static public Matrix randomMatrix(int size) {
        double[][] matrix = generateMatrix(size);
        double[] b = generateB(size);
        return new Matrix(matrix, b);
    }
}
package p1;

import java.util.Arrays;

public class Matrix {
    private  double[][] matrix;
    private  double[] b;
    private  int size;

    Matrix (double[][] array, double[] bArray) {

        if (array.length != bArray.length) {
            throw new RuntimeException("\nLength of the array 'a' should be the same as the length of the array 'b'");
        } else if (array.length < 2) {
            throw new RuntimeException("\nArray length should be more than 2");
        } else if (array.length != array[0].length) {
            throw new RuntimeException("\nMatrix should be square!");
        } else if (array.length > 20) {
            throw new RuntimeException("\nLength of matrix should be less or equal to 20");
        }

        this.size = array.length;
        this.matrix = new double[size][size];
        this.b = bArray.clone();
        fillMatrix(array);
    }



    private void fillMatrix(double[][] array) {
        if (array.length == 0) {
            throw new RuntimeException("\nArray can't be empty");
        } else if (array.length != array[0].length) {
            throw new RuntimeException("\nArray should be square");
        } else if (array.length != this.matrix.length) {
            throw new RuntimeException("\nArray length does not fit matrix length");
        }

        for (int i = 0; i < array.length; ++i) {
            this.matrix[i] = array[i].clone();
        }
    }

    public double[] row(int index) {
        return this.matrix[index].clone();
    }

    public double[] B() {
        return this.b.clone();
    }

    public int getSize() {
        return this.size;
    }

    public double[] column(int index) {
        double[] column = new double[size];
        int i = 0;
        for (double[] row: this.matrix) {
            column[i] = row[index];
            ++i;
        }
        return column;
    }



    public void replaceRows(int i, int j) {
        double[] temp = this.matrix[i];
        this.matrix[i] = this.matrix[j];
        this.matrix[j] = temp;
        double tempB = this.b[i];
        this.b[i] = this.b[j];
        this.b[j] = tempB;
    }

    static private double findMultiplier(double a, double b) {
        if (b == 0.0f) return 1.0f;
        return a/b;
    }

    public void substractRows(int i, int j, double multiplier) {
        int accuracy = 10;
        double[] substactor = this.matrix[i];
        double[] minuend = this.matrix[j];
        for (int c = 0; c < size; ++c) {
            minuend[c] -= substactor[c] * multiplier;
            minuend[c] = Round(minuend[c], accuracy);
        }
        b[j] -= b[i] * multiplier;
        b[j] = Round(b[j], accuracy);
    }


    public void makeUpperTriangular() {
        for (int i = 0; i < this.size - 1; ++i) {
            double[] column = this.column(i);
            int maxIndex = Gauss.findIndexOfMaxElement(column, i);
            if (maxIndex != i) {
                this.replaceRows(i, maxIndex);
            }
            for (int j = i + 1; j < this.getSize(); ++j) {
                double multiplier = findMultiplier(this.row(j)[i], this.row(i)[i]);
                this.substractRows(i, j, multiplier);
            }
        }
    }


     public static double deterCalc(double[][] array) {
         double deter = 0.0;

        if (array.length == 2) {
            deter = array[0][0] * array[1][1] - array[1][0] * array[0][1];
        } else {
            int coef = 1;
            for (int i = 0; i < array.length; i++) {
                if (i % 2 == 1) {
                    coef = -1;
                } else {
                    coef = 1;
                }

                deter += coef * array[0][i] * deterCalc(getMinor(array,0,i));
            }
        }
        return deter;
    }

    public static double[][] getMinor(double[][] arr, int row, int column){
        int minorLength = arr.length-1;
        double[][] minor = new double[minorLength][minorLength];
        int dI=0;
        int dJ=0;
        for(int i=0; i<=minorLength; i++){
            dJ=0;
            for(int j=0; j<=minorLength; j++){
                if(i==row){
                    dI=1;
                }
                else{
                    if(j==column){
                        dJ=1;
                    }
                    else{
                        minor[i-dI][j-dJ] = arr[i][j];
                    }
                }
            }
        }
        return minor;
    }




    private double Round(double number, int roundTo) {
        double mp = Math.pow(10, (double)roundTo);
        return Math.round(number * mp) / mp;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < this.matrix.length; ++i) {
            str += (Arrays.toString(this.matrix[i]) + " " + this.b[i]) + '\n';
        }
        return str;
    }


}

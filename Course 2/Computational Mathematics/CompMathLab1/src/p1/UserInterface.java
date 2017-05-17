package p1;

import java.util.Scanner;
import static p1.CompMathLab1.print;

public class UserInterface {
    private String[]  menu;

    public UserInterface(String[] menu) {
        this.menu = menu;
    }

    public void printMenu() {
        for (int i = 0; i < menu.length; ++i) {
            print(String.format("%s. %s", i + 1, menu[i]));
        }
    }

    public int getResponse() {

        int choice;
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                choice = sc.nextInt();
                if (0 < choice && choice <= menu.length)
                    return choice;
            }
            catch (Exception ex) {
                sc.nextLine();
                print("Error:\nThere is not such item in the menu. Try again.");
                printMenu();
            }

        }

    }

    static int getInt() {
        int size = 0;
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                size = sc.nextInt();
                if (size < 2 || size > 20) {
                    throw new Exception();
                }
                return size;
            }
            catch (Exception ex) {
                print(size);
                sc.nextLine();
                print("Error:\nThe value must be an integer greater than 1 and less than 20.");
            }
        }
    }

    static Double[] getRow(int size) {
        Scanner sc = new Scanner(System.in);
        Double[] arr = new Double[size];

        while (true) {
            try {
                String[] stringArr = sc.nextLine().split(" ");

                if (stringArr.length != size) {
                    throw new Exception();
                }

                if (stringArr.length == size) {
                    for (int i = 0; i < size; ++i) {
                        arr[i] = new Double(stringArr[i]);
                    }
                }
                return arr;

            } catch (Exception ex) {
                print("Error:\n Invalid input, try again.");
            }
        }
    }

    static Matrix getMatrix(int size) {
        double[][] arr = new double[size][size];
        double[] arrB = new double[size];

        for (int i =0; i < size; ++i) {
            Double[] row = getRow(size + 1);
            for (int j = 0; j < size; ++j) {
                arr[i][j] = row[j];
            }
            arrB[i] = row[size];
        }
        return new Matrix(arr,arrB);
    }

    static String getLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }




}

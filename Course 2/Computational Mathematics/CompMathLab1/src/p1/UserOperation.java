package p1;

import java.util.Arrays;

import java.io.BufferedReader;
import java.io.FileReader;
import static p1.CompMathLab1.print;

class UserOperation {

     void display() {
        String[] menu = {"Manual input", "Input From File", "Random", "Exit"};
        UserInterface userInterface = new UserInterface(menu);
        userInterface.printMenu();
        int choice = userInterface.getResponse();
        int size;
        Matrix matrix;
        switch (choice) {
            case 1:
                print("Type in size of your matrix: ");
                size = UserInterface.getInt();
                print(String.format("Type in arrays of size %s(%s A's coefficients and last the B)." +
                        " Elements should be split by spaces.", size + 1, size));
                matrix = UserInterface.getMatrix(size);
                print(matrix);
                outputDecision(matrix);

                break;
            case 2:
                print("Type in the file name: ");
                matrix = loadMatrix(UserInterface.getLine());
                if (matrix != null) {
                    outputDecision(matrix);
                }
                break;
            case 3:
                print("Type in size of your matrix: ");
                size = UserInterface.getInt();
                Matrix randomMatrix = RandomMatrix.randomMatrix(size);
                print(randomMatrix);
                outputDecision(randomMatrix);
                break;
            case 4:
                print("Exit of program.");
            default:
                break;
        }
    }

    static  private  void outputDecision(Matrix matrix) {
        Double[] solution = Gauss.equate(matrix);
        Double[] residuals = Gauss.findResiduals(matrix, solution);
        for (int i = 0; i < solution.length; ++i) {
            print(String.format("x: %s  residuals: %s", solution[i], residuals[i]));
        }
    }

    static private Matrix loadMatrix(String fileName) {
        Matrix matrix;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            Integer size = new Integer(br.readLine());
            double[][] arr = new double[size][size];
            double[] bArr = new double[size];

            for (int i = 0; i < size; ++i) {
                String[] nums = br.readLine().split(" ");
                for (int j = 0; j < size; ++j) {
                    arr[i][j] = new Double(nums[j]);
                }
                bArr[i] = new Double(nums[size]);
            }
            matrix = new Matrix(arr, bArr);

        } catch (Exception e) {
            e.printStackTrace();
            print("File not found or it has been corrupted. Please, check the file. " + e.getMessage() );
            matrix = null;
        }
        return matrix;
    }

    static private int gaussTest() {

        double[][] z1 = {
                {10, -1, 5},
                {4, 16, -30},
                {13, -8, 24}
        };

        double[] b1 = {9, -3, 0};
        Double[] result = Gauss.equate(new Matrix(z1, b1));
        System.out.println(Arrays.toString(result));
        Double[] residuals = Gauss.findResiduals(new Matrix(z1, b1), result);
        System.out.println(Arrays.toString(residuals));
        return 1;
    }

}

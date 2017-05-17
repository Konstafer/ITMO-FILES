package Lab3;

import java.util.Scanner;

import static Lab3.Work3.print;

public class UserInterface {

    public double workWithUser() {
        double r = 0;
        Scanner in = new Scanner(System.in);

        print("Please, enter the radius:");
            try {
                r = Double.valueOf(in.next());
                if (0 >= r) {
                    throw new Exception("Radius must be greater than zero.");

                }
            } catch (NumberFormatException e) {
                print("Please, enter a number.");
                workWithUser();
            }
            catch (Exception e) {
               print(e.getMessage());
                workWithUser();
            }
        return r;
    }




}

package whileLoop;

import java.util.Scanner;

public class WhileLoop {
  public static void main(String[] args) {

    // bucle contrelado por contador

    /*
     * int counter = 0;
     * 
     * while (counter < 10) {
     * System.out.println("Im on the round: " + (counter));
     * counter++;
     * }
     */

    // ejemplo con bandrera

    boolean flag = true;
    String response;
    Scanner console = new Scanner(System.in);

    while (flag == true) {
      System.out.println("flag value: " + flag);
      System.out.println("Are you baring? (yes/no)");
      response = console.nextLine();

      if (response.equalsIgnoreCase("yes")) {
        System.out.println("sorry man, reed a book");
      } else if (response.equalsIgnoreCase("no")) {
        System.out.println("congratulations man");
        flag = false;
      } else {
        System.out.println("please type yes or no, dont be a fool");
      }
    }
    console.close();

  }
}

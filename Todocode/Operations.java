import java.util.Scanner;

public class Operations {
  public static void main(String[] args) {

    Scanner console = new Scanner(System.in);
    System.out.println("please type first number");

    int num1, num2;

    num1 = console.nextInt();
    while (num1 < 0) {
      System.out.println("please don´t be a stupid, type a valid number");
      num1 = console.nextInt();

    }

    System.out.println("please type number to add");
    num2 = console.nextInt();

    console.close();

    System.out.println("Result is :" + (num1 + num2));
  }
}

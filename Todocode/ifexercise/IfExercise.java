package ifexercise;

import java.util.Scanner;

public class IfExercise {
  public static void main(String[] args) {
    int age;

    Scanner console = new Scanner(System.in);

    System.out.println("please type your age");

    age = console.nextInt();
    console.close();

    if (age < 18) {
      System.out.println("your'e minor, are you a kid, get out of here!");
    } else if (age < 50) {
      System.out.println("your'e an respetable age person");
    } else {
      System.out.println("damn! your'e so old");
    }
  }
}

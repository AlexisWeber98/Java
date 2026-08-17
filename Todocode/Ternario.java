import java.util.Scanner;

public class Ternario {
  public static void main(String[] args) {
    double prom;
    String finalCondition;

    Scanner console = new Scanner(System.in);

    System.out.println("type your prom");

    prom = console.nextDouble();
    console.close();
    finalCondition = prom >= 6 ? "Aproved" : "Unaproved";

    System.out.println(finalCondition);
  }
}

package forLoop;

public class ForLoop {
  public static void main(String[] args) {

    int sum = 0;

    for (int i = 0; i < 10; i++) {
      System.out.println("I'm in the round: " + (i + 1));
      sum = 5 + i;

      if (sum >= 7) {
        i = 11;
        System.out.println("The sum is greater than 7, so we will break the loop");
      }
    }
  }
}

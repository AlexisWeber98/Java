package merceryExcercise;

import java.util.Scanner;

public class MerceryExcercise {
  public static void main(String[] args) {
    // mercería mayorista
    // menos de 5 paquetes no vende
    // entre 5 y 15 -> 10 USD envío
    // más de 15 paquetes -> envío gratarola

    int packageCount;
    double totalMount, diff, discount;

    Scanner console = new Scanner(System.in);

    System.out.println("type package count (buy)");
    packageCount = console.nextInt();

    if (packageCount < 5) {
      System.out.println("Sorry man, cannot sale you");
    } else {

      System.out.println("type your payment mount");
      console = new Scanner(System.in);
      totalMount = console.nextDouble();
      console.close();

      if (packageCount >= 5 && packageCount <= 15) {
        totalMount += 10;
        System.out.println("Ok man, 10 usd for shipping");
      } else {
        System.out.println("great! you have free shipping");
      }

      if (totalMount < 100) {
        diff = 100 - totalMount;
        System.out.println("totalMount is mainor than 100, you dont have a promo, you need + $" + diff
            + ". Your total payment mount is : $" + totalMount);

      } else {
        if (totalMount > 100 && totalMount <= 300) {

          discount = totalMount * 0.05;
          System.out.println("you have a discount 5% off ( $" + discount + ")\n " + "you total payment mount is : $"
              + (totalMount - discount));
        } else {
          discount = totalMount * 0.10;
          System.out.println("you have a discount 15% off ( $" + discount + ")\n " + "you total payment mount is : $"
              + (totalMount - discount));

        }
      }
    }
  }
}

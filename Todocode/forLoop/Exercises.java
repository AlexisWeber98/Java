package forLoop;

import java.util.Scanner;

public class Exercises {
  public static void main(String[] args) {
    /*
     * 1) realizar un programa que muestre por pantalla los númetros del 1 al 35
     * (uno debajo del otro)
     */

    for (int i = 1; i <= 35; i++) {
      System.out.println(i);
    }

    /*
     * 2) realizar un programa que, dado un límine por tectlado, (ejemplo 100)
     * muestre en pantalla todos los nlumeros hasta ese límite (comenzando por 1)
     */

    Scanner console = new Scanner(System.in);
    System.out.println("please type a limit number");
    int limit = console.nextInt();

    for (int i = 1; i <= limit; i++) {
      System.out.println(i);
    }

    console.close();

    /*
     * 3) Realizar un programa que muestre por pantalla los númetros del 200 al 250
     * saltando de 2 en 2
     */

    for (int i = 200; i <= 250; i++) {
      if (i % 2 == 0) {
        System.out.println(i);
      } else {
        continue;
      }
    }

    // 4) realizar un programa que lleve una cuenta regresiva (10 a 1)
    for (int i = 10; i >= 1; i--) {
      System.out.println(i);
    }
  }
}

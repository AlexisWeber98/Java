import java.util.Scanner;

public class Vectors {
  public static void main (String[] args) {

    int vector[] = new int[5];
  
    vector[0] = 10;
    vector[1] = 12;
    vector[2] = 25;
    vector[3] = 23;
    vector[4] = 26;
  
    for (int i = 0; i < vector.length; i++) {
      System.out.println("El valor del vector en la posición " + i + " es: " + vector[i]);
    }

    Scanner console = new Scanner(System.in);

    System.out.println("Ingrese la longitud del vector: ");
    int longVector = console.nextInt();
    int vector2[] = new int[longVector];

    for (int i = 0; i < vector2.length; i++){
    System.out.println("Ingrese los valores");
    int value = console.nextInt();
      vector2[i] = value; 
    }
  
    for (int i = 0; i < vector2.length; i++) {
      System.out.println("El valor del vector en la posición " + i + " es: " + vector2[i]);
      System.out.println("------------------------------------------------------------ ");
    }

    System.out.println("Este es el vector entero: ");
   System.out.println("\n---------------------------------");
    for (int i = 0; i < vector2.length; i++) {
      System.out.print(vector2[i] + "|");
    }

   System.out.println("\n---------------------------------");
  }
}

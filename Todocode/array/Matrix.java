import java.util.Scanner;

public class Matrix {
  public static  void main(String[] args) {
  
// si tiene la misma cantidad de columnas como de filas es una matriz cuadrada
 /*
   int matrix[][] = new int[3][3];

  Scanner console = new Scanner(System.in);

  for (int i = 0; i < matrix.length; i++) {
    for (int j =0; j < matrix.length; j++) {
    System.out.println("Ingrese el valor de la fila [" + i + "] colemna [" + j + "]: ");
    matrix[i][j] = console.nextInt();
    }
  }


  for (int i = 0; i < matrix.length; i++) { // .length no sirve en matrices que nso son cuadradas
    for (int j =0; j < matrix.length; j++) {
    System.out.println("Valor de la Fila " + i + ", Columna " + j + ", Valor: " + matrix[i][j] );
    }
  }
 
*/

    // RECORRIDO DE MATRICES NO CUADRADAS Y CUADRADAS POR IGUAL

    int matrix[][] = new int[3][5];

  Scanner console = new Scanner(System.in);

  for (int i = 0; i < matrix.length; i++) {
    for (int j =0; j < matrix[0].length; j++) {
    System.out.println("Ingrese el valor de la fila [" + i + "] colemna [" + j + "]: ");
    matrix[i][j] = console.nextInt();
    }
  }


  for (int i = 0; i < matrix.length; i++) { // .length no sirve en matrices que nso son cuadradas
    for (int j =0; j < matrix[0].length; j++) {
    System.out.println("Valor de la Fila " + i + ", Columna " + j + ", Valor: " + matrix[i][j] );
    }
  }

  }
}

 

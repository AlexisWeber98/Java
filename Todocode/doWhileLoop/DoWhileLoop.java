package doWhileLoop;

public class DoWhileLoop {
  public static void main(String[] args) {

    int aux = 0;

    do {
      System.out.println("Estoy en la vuelta  número :" + (aux + 1));
      aux++;
    } while (aux < 10);
  }
}

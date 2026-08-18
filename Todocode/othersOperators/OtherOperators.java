package othersOperators;

public class OtherOperators {
  public static void main(String[] args) {

    // String no es un tipo de dato primitivo, es un objeto de la clase String

    String word = "hello";

    // comparaciones de strings, no se puede usar == para comparar strings, se debe
    // usar el método equals()
    word.equals("hello"); // true

    if (!word.equals("Hello"))
      System.out.println("not equals");// false
    //

  }
}

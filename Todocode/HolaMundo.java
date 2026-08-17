
public class HolaMundo {
  public static void main(String[] args) {

    // constante
    final int iva = 21;
    // entero
    int numx = 10;

    int numy = 15;

    // double
    double height = 23.3;

    // booleans
    boolean isStupid = true;

    // caracter
    char character = 'a';

    // String

    String tel = "1122334455";

    // long
    long longNumber = 12356567;
    // operación básica oritmética
    int result = numx + numy;

    System.out.println("""
        Values:
        height: %s
        isStupid: %s
        char: %s
        tel: %s
        longNumber: %s,
        IVA: &s,
        result: %s
          """.formatted(height, isStupid, character, tel, longNumber, iva, result));

  }
}

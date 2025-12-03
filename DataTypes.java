public class DataTypes {
  public static void main (String[] args) {

    // Tipos de datos primitivos

    // Enteros
    byte byteNumber = 127; // 1 byte
    short shortNumber = 32767; // 2 bytes
    int intNumber = 2147483647; // 4 bytes
    long longNumber = 9223372036854775807L; // 8 bytes

    // Números con decimales
    float floatNumber = 3.4028235E38F; // 4 bytes
    double doubleNumber = 1.7976931348623157E308; // 8 bytes

    // Carácter
    char character = 'A'; // 2 bytes

    // Booleano
    boolean isJavaFun = true; // 1 bit

    // Imprimir los valores
    System.out.println("Byte: " + byteNumber);
    System.out.println("Short: " + shortNumber);
    System.out.println("Int: " + intNumber);
    System.out.println("Long: " + longNumber);
    System.out.println("Float: " + floatNumber);
    System.out.println("Double: " + doubleNumber);
    System.out.println("Character: " + character);
    System.out.println("Boolean: " + isJavaFun);
  }
}

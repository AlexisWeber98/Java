public class HelloWorld {

  public static void main(String[] args) {

    System.out.println("HelloWorld from Java");

    String name = "Ale";

    System.out.println(name);

    name = "Ale Weber";
    System.out.println(name);

    String namePlusAge = name + " 27 Years Old";

    System.out.println(namePlusAge);

    final String EMAIL = "ale@mail.com"; // es una constante
    System.out.println(EMAIL);

    // Llamado a main de DataTypes //
    DataTypes.main(new String[0]);

    System.out.println("-------------- OPERATIONS ----------------");
    Operations.main(new String[0]);

    System.out.println("-------------- STRINGS ----------------");
    Strings.main(new String[0]);

    System.out.println("-------------- CONDITIONALS ----------------");
    Conditionals.main(new String[0]);

    System.out.println("-------------- ARRAYS ----------------");
    Arrays.main(new String[0]);

    System.out.println("-------------- LISTS ----------------");
    List.main(new String[0]);

    System.out.println("-------------- SETS ----------------");
    Sets.main(new String[0]);

    System.out.println("-------------- MAPS ----------------");
    Maps.main(new String[0]);

    System.out.println("-------------- LOOPS ----------------");
    Loops.main(new String[0]);

    System.out.println("-------------- FUNCTIONS ----------------");
    Functions.main(new String[0]);
  }
}

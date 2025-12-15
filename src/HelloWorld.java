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

    Operations.main(new String[0]);

    Strings.main(new String[0]);

    Conditionals.main(new String[0]);

    Arrays.main(new String[0]);

    List.main(new String[0]);

    Sets.main(new String[0]);

    Maps.main(new String[0]);

    Loops.main(new String[0]);
  }
}

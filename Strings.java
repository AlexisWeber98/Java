public class Strings {

  public static void main(String[] args) {
    String name = "Alexis";
    String surname = "Weber";
    String holaRey = "  Hola Rey  ";
  
    System.out.println(name + surname);

    System.out.println("longitud: " + name.length());


    System.out.println("Obtener caracter en posicion 1: " + surname.charAt(1));


    System.out.println("substring: " + surname.substring(1));
    System.out.println("substring: " + name.substring(1, 4));
 

    System.out.println("MAAYUSCULA: " + name.toUpperCase());

    System.out.println("minusculas: " + name.toLowerCase());


    System.out.println("contiene: R " + surname.contains("r"));


    System.out.println("igualdad: " + name.equals("alexis"));

    System.out.println("igualdad: " + name.equalsIgnoreCase("alexis"));


    System.out.println("sin trim(): " + holaRey);

    System.out.println("trim(): " + holaRey.trim());


    System.out.println("replace: " + holaRey.replace("Rey", "Alexote!"));


    System.out.println("replace espacios: " + holaRey.replace(" ", ""));


    System.out.println(String.format("Hola, soy %s. y mi apellido es %s, tengo %d años", name, surname, 27));
  }
}

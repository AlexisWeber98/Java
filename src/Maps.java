import java.util.HashMap;

public class Maps {
  public static void main(String[] args) {
    // Maps
    // Son datos de tipo Clave-Valor
    HashMap<String, String> names = new HashMap<>();
    // add
    names.put("A1", "Ale");
    names.put("A2", "Juan");
    names.put("A3", "Pedro");

    System.out.println("Names map: " + names);

    var numbers = new HashMap<String, Integer>();

    numbers.put("One", 1);
    numbers.put("Five", 5);
    numbers.put("Ten", 10);

    System.out.println("numbers map: " + numbers);

    // acceder al elemento ( se hace por clave)

    names.get("A1");

    System.out.println("Get A1: " + names.get("A1"));

    numbers.get("Five");

    System.out.println("Get Five: " + numbers.get("Five"));

    // buscar por key

    numbers.containsKey("Ten");

    System.out.println("Contains key Ten: " + numbers.containsKey("Ten"));

    // buscar por value

    names.containsValue("Juan");

    System.out.println("Contains value Juan: " + names.containsValue("Juan"));
    // eliminar elemento

    names.remove("A3");
    numbers.remove("One", 1);

    System.out.println("Names map after remove A3: " + names);
    System.out.println("Numbers map after remove One: " + numbers);

    // limpiar

    names.clear();

    System.out.println("Names map after clear: " + names);

    // modificar elemento
    // put => si no existe lo crea, si existe lo modifica

    names.put("A2", "Carlos");
    System.out.println("Names map after modify A2: " + names);

    // modificar elemento con replace

    numbers.replace("Five", 15);
    System.out.println("Numbers map after replace Five: " + numbers);

    // crear sólo si no existe
    names.putIfAbsent("A1", "Alejandro");

    System.out.println("Names map after putIfAbsent A1: " + names);

    names.putIfAbsent("A4", "Pedro");

    System.out.println("Names map after putIfAbsent A3: " + names);

    // Obtener info

    System.out.println("names map is empty?: " + names.isEmpty());

    System.out.println("numbers map values: " + numbers.values());

    var arrayListFromMap = new java.util.ArrayList<>(numbers.values());

    System.out.println("ArrayList from numbers map values: " + arrayListFromMap);
  }
}

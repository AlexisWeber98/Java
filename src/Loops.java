import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

public class Loops {
  public static void main(String[] args) {
    // Loops

    // For

    System.out.println(" ------------------- FOR LOOP por contador --------------------------- ");

    for (int i = 0; i < 5; i++) {
      System.out.println("For Loop i: " + i);
    }

    for (int j = 10; j >= 0; j--) {
      System.out.println("For Loop i decrement: " + j);
    }

    // ------------ For Loop con estructuras de datos -----------------

    String[] names = {"Marcos", "Gabi", "Ale", "Pepe", "Javi"};

    for (int i = 1; i < names.length; i++) {
      System.out.println("Names array element using length: " + names[i]);
    }

    // For Each

    System.out.println(" ------------------- FOR EACH LOOP --------------------------- ");

    for (String name : names) {
      System.out.println("Name using For Each: " + name);
    }

    var numbers = new HashSet<Integer>();

    numbers.add(10);
    numbers.add(20);
    numbers.add(30);
    numbers.add(40);
    numbers.add(50);

    for (Integer number : numbers) {
      System.out.println("Number in set using For Each: " + number);
    }

    HashMap<String, String> emails = new HashMap<>();
    emails.put("A1", "auno@email.com");
    emails.put("A2", "ados@email.com");
    emails.put("A3", "atres@email.com");

    for (Map.Entry<String, String> email : emails.entrySet()) {
      System.out.println("Email in map using For Each ( Key ) : " + email.getKey());
      System.out.println("Email in map using For Each ( Value ) : " + email.getValue());
    }

    // While

    System.out.println(" ------------------- WHILE LOOP --------------------------- ");

    int index = 0;
    while (index < 5) {
      System.out.println("While Loop index: " + index);
      index++;
    }

    Boolean find = false;
    index = 0;
    while (!find) {
      System.out.println("While Loop names element: " + names[index]);
      if (names[index].equals("Ale")) {
        find = true;
        System.out.println("Found Ale! in Index " + index);
      }
      index++;
    }

    // do while

    System.out.println(" ------------------- DO WHILE LOOP --------------------------- ");

    index = 0;
    do {
      System.out.println("Do While Loop index: " + index);
      index++;
    } while (index < 5);

    // control de bucles

    for (String name : names) {
      if (name.equals("Ale")) {
        break;
      }
      System.out.println("Name before break: " + name);
    }

    for (int i = 0; i < 5; i++) {
      if (i == 3) {
        continue;
      }
      System.out.println("i value with continue: " + i);
    }
  }
}

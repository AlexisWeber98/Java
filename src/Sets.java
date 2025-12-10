import java.util.HashSet;

public class Sets {
  public static void main(String[] args) {

    HashSet<String> colors = new HashSet<>();

    HashSet<Integer> numbers = new HashSet<>();

    // agregar elementos
    colors.add("Red");
    colors.add("Green");
    colors.add("Blue");
    colors.add("Yellow");
    numbers.add(1);
    numbers.add(5);
    numbers.add(10);
    numbers.add(15);

    System.out.println("Colors set size: " + colors.size());
    System.out.println("Colors set: " + colors);

    System.out.println("numbers set: " + numbers);

    System.out.println("numbers set size: " + numbers.size());

    // buscar

    System.out.println("numberss set contiene 10 " + numbers.contains(10));

    System.out.println("numberss set contiene 20 " + numbers.contains(20));

    // eliminar elemento
    colors.remove("Yellow");
    System.out.println("Colors set after removing Yellow: " + colors);

    numbers.remove(5);
    System.out.println("Numbers set after removing 5: " + numbers);

    // agregar conjuntos
    var countries = new HashSet<String>();
    countries.add("Argentina");
    countries.add("Mexico");
    countries.add("España");

    System.out.println("Countries set: " + countries);

    colors.addAll(countries);
    System.out.println("Colors set after adding countries: " + colors);
  }
}

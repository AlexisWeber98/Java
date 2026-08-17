import java.util.ArrayList;

public class List {
  public static void main(String[] args) {

    // vieja sintxis
    ArrayList<String> names = new ArrayList<>();

    // nueva sintxais
    var numbers = new ArrayList<Integer>();

    System.out.println("ArrayList tamaño: " + names.size());

    // añadir elementos

    numbers.add(1);
    numbers.add(5);
    numbers.add(10);

    names.add("Ale");

    System.out.println("ArrayList names tamaño: " + names.size());

    System.out.println("ArrayList numbers tamaño: " + numbers.size());
    names.add("Juan");
    names.add("Pedro");

    System.out.println("ArrayList names tamaño: " + names.size());

    System.out.println("obtengo el prmero" + names.getFirst());

    System.out.println("obtengo el prmer numero" + numbers.getFirst());

    System.out.println("Obtengo el ultimo" + names.getLast());
    System.out.println("Obtengo el ultimo nú numero" + numbers.getLast());

    System.out.println("Obtengo el elemento posicion 1 " + names.get(1));

    System.out.println("Obtengo el elemento posicion 1 " + numbers.get(1));
    // modificar elemento

    names.set(1, "Carlos");
    System.out.println("Obtengo completo" + names);

    numbers.set(1, 15);
    System.out.println("Obtengo completo" + numbers);

    // remover elemento

    names.remove(2);
    numbers.remove(0);

    System.out.println("Obtengo completo" + numbers);

    System.out.println("Obtengo completo" + names);

    // buscar elemento

    System.out.println("Contiene a Ale? " + names.contains("Ale"));
    System.out.println("Contiene a Juan? " + names.contains("Juan"));

    System.out.println("Contiene el número 10? " + numbers.contains(10));
    System.out.println("Contiene el número 1? " + numbers.contains(1));

    // limpiar lista
    names.clear();
    System.out.println("Limpiando lista names " + names);
  }
}

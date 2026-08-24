// Módulo 15 — Primer contacto con ArrayList: una lista que crece y encoge sola.
// Ejecutar: java ejemplos/PrimerArrayList.java
import java.util.ArrayList;
import java.util.List;

public class PrimerArrayList {

    public static void main(String[] args) {
        // Programamos contra la interfaz List; ArrayList es sólo la implementación.
        List<String> invitados = new ArrayList<>();
        System.out.println("Recién creada: " + invitados + " (tamaño: " + invitados.size() + ")");

        // ADD: agregar al final. La lista se estira sin que pidamos permiso.
        invitados.add("Ana");
        System.out.println("add(\"Ana\")    -> " + invitados + " (size: " + invitados.size() + ")");
        invitados.add("Luis");
        invitados.add("Mara");
        System.out.println("3 adds después -> " + invitados + " (size: " + invitados.size() + ")");

        // GET: leer por índice. O(1): salto directo a la posición.
        System.out.println("\nget(0) devuelve: " + invitados.get(0));
        System.out.println("get(2) devuelve: " + invitados.get(2));

        // SET: reemplazar por índice (no cambia el tamaño).
        invitados.set(1, "Lucía");
        System.out.println("\nset(1, \"Lucía\") -> " + invitados);

        // ADD con índice: insertar en el medio desplaza a los siguientes.
        invitados.add(0, "Bruno");
        System.out.println("add(0, \"Bruno\") -> " + invitados + " (todos corrieron un lugar)");

        // REMOVE por índice y por valor.
        String sacado = invitados.remove(0);
        System.out.println("\nremove(0) sacó a " + sacado + " -> " + invitados);
        invitados.remove("Mara"); // primera aparición del valor
        System.out.println("remove(\"Mara\")  -> " + invitados + " (size: " + invitados.size() + ")");

        // Preguntas típicas antes de usar.
        System.out.println("\n¿Contiene a Lucía? " + invitados.contains("Lucía"));
        System.out.println("¿Está vacía?       " + invitados.isEmpty());

        // CLEAR: vaciar todo de una.
        invitados.clear();
        System.out.println("clear()         -> " + invitados + " (size: " + invitados.size() + ")");
    }
}

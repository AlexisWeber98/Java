import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * LIFO y FIFO lado a lado: el undo de un editor usa pila (lo último
 * escrito se deshace primero); la fila del banco usa cola (el primero
 * que llega es atendido primero).
 */
public class PilasYColasEnAccion {

    public static void main(String[] args) {
        demoUndoConPila();
        System.out.println();
        demoFilaBancoConCola();
    }

    static void demoUndoConPila() {
        Stack<String> historial = new Stack<>();

        System.out.println("== Editor de texto: undo con Stack (LIFO) ==");
        historial.push("escribió 'Hola'");
        historial.push("borró una palabra");
        historial.push("pegó un párrafo");
        System.out.println("Acciones en el historial: " + historial.size());

        while (!historial.isEmpty()) {
            String deshecha = historial.pop();
            System.out.println("Ctrl+Z → deshaciendo: " + deshecha);
        }
        // peek sobre vacía lanzaría excepción; por eso chequeamos isEmpty antes.
        System.out.println("Historial vacío, no hay nada que deshacer.");
    }

    static void demoFilaBancoConCola() {
        Queue<String> fila = new LinkedList<>();

        System.out.println("== Banco: atención con Queue (FIFO) ==");
        fila.offer("Ana");
        fila.offer("Bruno");
        fila.offer("Carla");
        System.out.println("Primero en la fila: " + fila.peek());

        while (!fila.isEmpty()) {
            String atendida = fila.poll();
            System.out.println("Atendiendo a: " + atendida);
        }
        System.out.println("poll() sobre fila vacía devuelve null: " + fila.poll());
    }
}

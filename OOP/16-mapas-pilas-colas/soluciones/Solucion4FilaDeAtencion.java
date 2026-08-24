/*
 * ============================================================================
 *  Solución 4 — Fila de atención con Queue
 * ============================================================================
 *
 *  Idea clave: Queue es FIFO (first in, first out): lo contrario del Stack
 *  del ejercicio anterior. offer(...) entra por el final, poll(...) sale por
 *  el frente. Elegimos poll() en vez de remove() porque devuelve null cuando
 *  la cola está vacía, lo que combina bien con el guardia explícito.
 */
import java.util.LinkedList;
import java.util.Queue;

public class Solucion4FilaDeAtencion {

    static class Cliente {
        private final String nombre;

        Cliente(String nombre) {
            this.nombre = nombre;
        }

        String getNombre() { return nombre; }
    }

    static void llegar(Queue<Cliente> fila, Cliente cliente) {
        fila.offer(cliente); // entra al final de la cola
        System.out.println("Llegó " + cliente.getNombre());
    }

    static void atender(Queue<Cliente> fila) {
        if (fila.isEmpty()) {
            System.out.println("No hay clientes esperando.");
            return;
        }
        Cliente cliente = fila.poll(); // sale el primero de la fila
        System.out.println("Atendiendo a " + cliente.getNombre());
    }

    static void mostrarEsperando(Queue<Cliente> fila) {
        // El for-each recorre sin alterar la cola: nadie pierde su lugar.
        for (Cliente cliente : fila) {
            System.out.println(" - " + cliente.getNombre());
        }
    }

    public static void main(String[] args) {
        Queue<Cliente> fila = new LinkedList<>();

        llegar(fila, new Cliente("Lucía"));
        llegar(fila, new Cliente("Martín"));
        llegar(fila, new Cliente("Sofía"));

        System.out.println("Abre la atención:");
        atender(fila);                 // Lucía
        atender(fila);                 // Martín

        System.out.println("Quedan esperando:");
        mostrarEsperando(fila);        // Sofía

        atender(fila);                 // Sofía
        atender(fila);                 // fila vacía: avisa sin explotar
    }
}

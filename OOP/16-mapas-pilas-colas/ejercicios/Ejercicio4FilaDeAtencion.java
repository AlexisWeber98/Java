/*
 * ============================================================================
 *  Ejercicio 4 — Fila de atención con Queue
 * ============================================================================
 *
 *  ENUNCIADO
 *  --------
 *  Una farmacia atiende por orden de llegada. La cola (Queue<Cliente>,
 *  implementada con LinkedList) guarda quién espera: entran por el final
 *  (offer) y salen por el frente (poll).
 *
 *  REQUISITOS
 *  ----------
 *  1. llegar(fila, cliente): agrega al cliente al final de la cola.
 *  2. atender(fila): atiende al primero SI la fila no está vacía; si está
 *     vacía informa "No hay clientes esperando". Nunca llames poll() a ciegas.
 *  3. mostrarEsperando(fila): nombres en orden, SIN sacar elementos.
 *
 *  PISTAS
 *  ------
 *  - Queue es FIFO (first in, first out): lo contrario del Stack anterior.
 *  - poll() devuelve null cuando la cola está vacía; remove() lanzaría
 *    excepción. Elegir bien entre ambos importa.
 *  - Para recorrer sin alterar la fila: for (Cliente c : fila) { ... }
 */

import java.util.LinkedList;
import java.util.Queue;

public class Ejercicio4FilaDeAtencion {

    static class Cliente {
        private final String nombre;

        Cliente(String nombre) {
            this.nombre = nombre;
        }

        String getNombre() { return nombre; }
    }

    static void llegar(Queue<Cliente> fila, Cliente cliente) {
        // TODO 1: entra por el final de la cola
    }

    static void atender(Queue<Cliente> fila) {
        // TODO 2: guardia anti-fila-vacía y aviso de a quién atendés
    }

    static void mostrarEsperando(Queue<Cliente> fila) {
        // TODO 3: recorrido sin modificar la fila
    }

    public static void main(String[] args) {
        Queue<Cliente> fila = new LinkedList<>();

        llegar(fila, new Cliente("Lucía"));
        llegar(fila, new Cliente("Martín"));
        llegar(fila, new Cliente("Sofía"));

        System.out.println("Abre la atención:");
        atender(fila);                 // debe atender a Lucía
        atender(fila);                 // debe atender a Martín

        System.out.println("Quedan esperando:");
        mostrarEsperando(fila);

        atender(fila);                 // Sofía
        atender(fila);                 // fila vacía: debe avisar sin explotar
    }
}

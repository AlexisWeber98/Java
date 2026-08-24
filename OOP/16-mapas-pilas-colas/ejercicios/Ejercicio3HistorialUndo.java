/*
 * ============================================================================
 *  Ejercicio 3 — Historial del editor con Stack (undo)
 * ============================================================================
 *
 *  ENUNCIADO
 *  --------
 *  Un editor de texto guarda las acciones aplicadas para poder deshacerlas.
 *  Modelamos cada acción como un String apilado en un Stack<String>.
 *
 *  REQUISITOS
 *  ----------
 *  1. aplicar(historial, accion): apila la acción con push(...).
 *  2. deshacer(historial): saca la última acción (pop) SOLO si la pila no
 *     está vacía; si está vacía informa "No hay acciones para deshacer".
 *  3. mostrarEstado(historial): imprime el contenido actual de la pila.
 *  BONUS (opcional): implementá rehacer(...) con una segunda pila.
 *
 *  PISTAS
 *  ------
 *  - pop() sobre una pila vacía lanza EmptyStackException: chequeá isEmpty()
 *    SIEMPRE antes de sacar.
 *  - Stack es LIFO: la última acción aplicada es la primera en deshacerse.
 *  - BONUS rehacer: al deshacer, mové la acción a una pilaRehacer; al aplicar
 *    algo nuevo, vaciala (el redo se pierde, como en cualquier editor real).
 *    Nota moderna: en código de producción se prefiere Deque/ArrayDeque;
 *    acá usamos Stack para practicar el concepto.
 */

import java.util.Stack;

public class Ejercicio3HistorialUndo {

    static void aplicar(Stack<String> historial, String accion) {
        // TODO 1: apilá la acción
    }

    static void deshacer(Stack<String> historial) {
        // TODO 2: cuidado con la pila vacía
    }

    static void mostrarEstado(Stack<String> historial) {
        // TODO 3: imprimí las acciones pendientes de deshacer (sin sacarlas)
    }

    public static void main(String[] args) {
        Stack<String> historial = new Stack<>();

        System.out.println("== Sesión de edición ==");
        aplicar(historial, "Escribió 'Hola'");
        mostrarEstado(historial);
        aplicar(historial, "Escribió ' mundo'");
        mostrarEstado(historial);

        System.out.println("== Deshaciendo ==");
        deshacer(historial);
        mostrarEstado(historial);
        deshacer(historial);
        mostrarEstado(historial);
        deshacer(historial);   // pila vacía: debe avisar y no romper

        // BONUS: ¿te animás a agregar rehacer() con una segunda pila?
    }
}

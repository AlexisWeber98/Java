/*
 * ============================================================================
 *  Solución 3 — Historial del editor con Stack (undo / redo)
 * ============================================================================
 *
 *  Idea clave: Stack es LIFO (last in, first out): lo último que se aplicó
 *  es lo primero que se deshace. El BONUS usa una segunda pila para redo;
 *  aplicar una acción nueva vacía esa pila (el redo se pierde), como en un
 *  editor real. Nota moderna: en producción se prefiere Deque/ArrayDeque;
 *  acá usamos Stack para practicar el concepto.
 */
import java.util.Stack;

public class Solucion3HistorialUndo {

    static void aplicar(Stack<String> historial, Stack<String> rehacer, String accion) {
        historial.push(accion);
        rehacer.clear(); // editar algo nuevo invalida el redo pendiente
        System.out.println("Aplicada -> " + accion);
    }

    static void deshacer(Stack<String> historial, Stack<String> rehacer) {
        if (historial.isEmpty()) {                 // pop() a ciegas lanza
            System.out.println("No hay acciones para deshacer."); // EmptyStackException
            return;
        }
        String accion = historial.pop();
        rehacer.push(accion);                      // queda disponible para redo
        System.out.println("Deshecha -> " + accion);
    }

    static void rehacer(Stack<String> historial, Stack<String> rehacer) {
        if (rehacer.isEmpty()) {
            System.out.println("No hay acciones para rehacer.");
            return;
        }
        String accion = rehacer.pop();
        historial.push(accion);
        System.out.println("Rehecha  -> " + accion);
    }

    static void mostrarEstado(Stack<String> historial, String etiqueta) {
        System.out.println(etiqueta + ": " + historial);
    }

    public static void main(String[] args) {
        Stack<String> historial = new Stack<>();
        Stack<String> rehacer = new Stack<>();

        System.out.println("== Sesión de edición ==");
        aplicar(historial, rehacer, "Escribió 'Hola'");
        aplicar(historial, rehacer, "Escribió ' mundo'");
        mostrarEstado(historial, "Historial");

        System.out.println("== Deshaciendo ==");
        deshacer(historial, rehacer);
        mostrarEstado(historial, "Historial");

        System.out.println("== Rehaciendo ==");
        rehacer(historial, rehacer);

        System.out.println("== Nueva acción: el redo previo se pierde ==");
        aplicar(historial, rehacer, "Borró una palabra");
        rehacer(historial, rehacer);               // pila de redo vacía: avisa

        System.out.println("== Deshaciendo todo ==");
        deshacer(historial, rehacer);
        deshacer(historial, rehacer);
        deshacer(historial, rehacer);              // pila vacía: avisa sin romper
    }
}

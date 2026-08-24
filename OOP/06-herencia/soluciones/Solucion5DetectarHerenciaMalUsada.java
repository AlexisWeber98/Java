/*
 * =============================================================================
 * Ejercicio 5 (SOLUCIÓN) — Detectar herencia mal usada: Pila extends ArrayList
 * Módulo 06 · Herencia
 * =============================================================================
 *
 * IDEAS CLAVE DE LA SOLUCIÓN
 *   - PilaPorHerencia compila y "funciona", pero expone TODA la API de
 *     ArrayList (get, clear, add en cualquier posición): el cliente puede
 *     leer el fondo de la pila o vaciarla de un saque. Adiós LIFO.
 *   - Una pila NO ES una lista: la USA. Herencia = ES-UN (identidad);
 *     composición = TIENE-UN (contención). Elegir mal rompe el encapsulamiento.
 *   - PilaPorComposicion esconde el ArrayList detrás de cuatro métodos:
 *     la única puerta de entrada es la puerta LIFO, y si mañana cambiamos
 *     la estructura interna, ningún cliente se entera.
 * =============================================================================
 */
import java.util.ArrayList;

public class Solucion5DetectarHerenciaMalUsada {

    // VERSIÓN ENFERMA (para comparar): heredar expone métodos que contradicen
    // lo que ES una pila. El compilador no puede salvarte porque todo es legal.
    static class PilaPorHerencia extends ArrayList<String> {
        void apilar(String elemento) {
            add(elemento);
        }

        String desapilar() {
            return remove(size() - 1);
        }
    }

    // ANÁLISIS (resumen del diagnóstico esperado):
    //   1. get(0) permite LEER EL FONDO: una pila solo deja ver su tope.
    //   2. clear() vacía la pila sin pasar por desapilar(): saltea el LIFO.
    //   3. add(i, x) permite APILAR EN EL MEDIO: la estructura deja de ser pila.
    //   4. La relación correcta es TIENE-UNA lista, no ES-UNA lista.
    //      Reutilizar código no justifica herencia; para eso existe composición.

    // VERSIÓN SANA: COMPOSICIÓN. La pila TIENE una lista y no lo publica.
    static class PilaPorComposicion {
        private final ArrayList<String> elementos = new ArrayList<>();

        void apilar(String elemento) {
            elementos.add(elemento); // delegación, no herencia
        }

        String desapilar() {
            if (elementos.isEmpty()) {
                throw new IllegalStateException("La pila está vacía.");
            }
            return elementos.remove(elementos.size() - 1);
        }

        boolean estaVacia() {
            return elementos.isEmpty();
        }

        int tamano() {
            return elementos.size();
        }
        // Lo que NO está acá, no existe para el cliente: ni get(), ni clear(),
        // ni add(i, x). Menos API pública = menos formas de romperla.
    }

    public static void main(String[] args) {
        System.out.println("=== HERENCIA: compila, pero la pila quedó desnuda ===");
        PilaPorHerencia pilaExpuesta = new PilaPorHerencia();
        pilaExpuesta.apilar("A");
        pilaExpuesta.apilar("B");
        pilaExpuesta.apilar("C");
        System.out.println("Espío el fondo con get(0): " + pilaExpuesta.get(0));
        pilaExpuesta.clear();
        System.out.println("Hice clear() sobre una 'pila'. ¿Sigue siendo pila?");

        System.out.println("\n=== COMPOSICIÓN: la única puerta es la LIFO ===");
        PilaPorComposicion pila = new PilaPorComposicion();
        pila.apilar("X");
        pila.apilar("Y");
        pila.apilar("Z");
        System.out.println("Desapilo: " + pila.desapilar());
        System.out.println("Quedan " + pila.tamano() + " elementos.");
        // pila.clear();          <- ni existe: error de COMPILACIÓN, no sorpresa
        // pila.elementos.get(0); <- privada: inaccesible desde afuera

        System.out.println("\nMoraleja: heredá solo ante un ES-UN real;"
                + " para reutilizar, componé.");
    }
}

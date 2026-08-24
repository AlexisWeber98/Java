/*
 * =============================================================================
 * Ejercicio 5 — Detectar herencia mal usada: Pila extends ArrayList
 * Módulo 06 · Herencia
 * =============================================================================
 *
 * ENUNCIADO
 * Te regalo un diseño ENFERMO. Mirá esta jerarquía real y funcional:
 *
 *     import java.util.ArrayList;
 *
 *     class Pila extends ArrayList<String> {
 *         void apilar(String elemento) { add(elemento); }
 *         String desapilar()           { return remove(size() - 1); }
 *     }
 *
 * Compila, corre... y es una trampa. Tu trabajo:
 *
 * REQUISITOS
 *   1. ANALIZÁ PilaPorHerencia (abajo, versión ejecutable del código de
 *      arriba). Ejecutá main y observá qué se puede hacer con esa "pila".
 *   2. Anotá como comentario los problemas: ¿qué métodos heredados de
 *      ArrayList quedan expuestos? ¿Qué pasa con la regla LIFO (último en
 *      entrar, primero en salir)?
 *   3. Respondete: ¿una pila ES una lista o TIENE una lista adentro?
 *      Herencia = ES-UN. Composición = TIENE-UN.
 *   4. Completá PilaPorComposicion: misma pila, pero construida CONTENIENDO
 *      un ArrayList privado. Solo debe ofrecer apilar, desapilar, estaVacia
 *      y tamano.
 *
 * PISTAS
 *   - El enemigo no es el compilador: es la API mentirosa que heredaste.
 *     get(0), clear(), add(i, x)... ¿debería una pila permitir eso?
 *   - Con composición, si mañana cambiás el ArrayList por otra estructura,
 *     NADIE afuera se entera. Con herencia, todo el mundo depende de ella.
 *   - Regla de oro: heredá solo cuando hay un ES-UN verdadero; para
 *     reutilizar código, componé.
 * =============================================================================
 */
import java.util.ArrayList;

public class Ejercicio5DetectarHerenciaMalUsada {

    // Versión ejecutable del diseño enfermo. Compila perfecto: el problema
    // es de DISEÑO, no de sintaxis.
    static class PilaPorHerencia extends ArrayList<String> {
        void apilar(String elemento) {
            add(elemento);              // método HEREDADO de ArrayList
        }

        String desapilar() {
            return remove(size() - 1);  // también heredado
        }
    }

    /*
     * TODO A: escribí ACÁ tu análisis de PilaPorHerencia, como comentario:
     *   - ¿qué métodos indeseados expone al cliente?
     *   - ¿cómo rompen la regla LIFO de una pila?
     *   - ¿"una pila ES-UNA lista" o "TIENE-UNA lista"? ¿qué relación usarías?
     */

    static class PilaPorComposicion {
        // La lista ahora es un DETALLE interno: nadie desde afuera la ve.
        private final ArrayList<String> elementos = new ArrayList<>();

        void apilar(String elemento) {
            // TODO B: delegá en la lista interna.
        }

        String desapilar() {
            // TODO C: devolvé y remové el TOPE (el último apilado).
            return null; // placeholder para que compile
        }

        boolean estaVacia() {
            // TODO D
            return true;
        }

        int tamano() {
            // TODO E
            return 0;
        }
    }

    public static void main(String[] args) {
        // Demostración del desastre potencial: TODO esto compila...
        PilaPorHerencia pilaExpuesta = new PilaPorHerencia();
        pilaExpuesta.apilar("A");
        pilaExpuesta.apilar("B");
        System.out.println("Leo el FONDO de la pila con get(0): " + pilaExpuesta.get(0));
        pilaExpuesta.clear();
        System.out.println("Hice clear() sobre una 'pila' y nadie protestó.");

        // TODO F: completá PilaPorComposicion, apilá "X", "Y", "Z",
        // desapilá una vez e imprimí tamano(). Verificá que get() y clear()
        // YA NO existan: el compilador ahora nos protege.
    }
}

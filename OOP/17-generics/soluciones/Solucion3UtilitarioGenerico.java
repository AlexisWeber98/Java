/*
 * ============================================================================
 *  Ejercicio 3 — Solución: métodos genéricos intercambiar y contarSi
 * ============================================================================
 *
 *  IDEA CLAVE: la <T> va ANTES del tipo de retorno. Ahí le decís al
 *  compilador "este método tiene su propia variable de tipo". El que llama
 *  no la escribe: el compilador la INFIERE de los argumentos.
 * ============================================================================
 */
import java.util.Arrays;
import java.util.function.Predicate;

public class Solucion3UtilitarioGenerico {

    /** Intercambia dos posiciones. Un solo método sirve para cualquier tipo de referencia. */
    static <T> void intercambiar(T[] arreglo, int i, int j) {
        T temporal = arreglo[i];      // la temporal también es T: cero casts, cero Object
        arreglo[i] = arreglo[j];
        arreglo[j] = temporal;
    }

    /** Cuenta los elementos que cumplen la condición. El criterio llega como parámetro. */
    static <T> int contarSi(T[] arreglo, Predicate<T> condicion) {
        int contador = 0;
        for (T elemento : arreglo) {
            if (condicion.test(elemento)) {
                contador++;
            }
        }
        return contador;
    }

    public static void main(String[] args) {
        // --- Demo con String[] ---
        String[] nombres = {"Ana", "lucas", "Betiana", "sofia", "Bruno"};
        System.out.println("Nombres originales:  " + Arrays.toString(nombres));

        intercambiar(nombres, 0, 4);
        System.out.println("Tras intercambiar 0↔4: " + Arrays.toString(nombres));

        int conMayuscula = contarSi(nombres, nombre -> Character.isUpperCase(nombre.charAt(0)));
        System.out.println("Arrancan con mayúscula: " + conMayuscula);

        // --- Demo con Integer[] ---
        Integer[] notas = {8, 5, 9, 2, 10, 4};
        System.out.println("\nNotas:               " + Arrays.toString(notas));

        intercambiar(notas, 1, 5);
        System.out.println("Tras intercambiar 1↔5: " + Arrays.toString(notas));

        int aprobados = contarSi(notas, nota -> nota >= 6);
        System.out.println("Notas mayores o iguales a 6: " + aprobados);

        int pares = contarSi(notas, nota -> nota % 2 == 0);
        System.out.println("Notas pares:                 " + pares);

        /*
         * Mirá qué pasó acá: contarSi no sabe NADA de nombres ni notas.
         * Recibe el QUÉ contar (el array) y el CRITERIO (el Predicate) por
         * parámetro. Esa separación datos/criterio es la base del estilo
         * funcional: es literalmente lo que hace Stream.filter().count().
         */
    }
}

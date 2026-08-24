/*
 * ============================================================================
 *  Ejercicio 5 (DESAFÍO) — Contador de palabras con HashMap
 * ============================================================================
 *
 *  ENUNCIADO
 *  --------
 *  Dado un texto, contar cuántas veces aparece cada palabra e imprimir el
 *  top 3. Es EL problema clásico donde un mapa brilla.
 *
 *  REQUISITOS
 *  ----------
 *  1. Normalizar el texto: pasar a minúsculas y reemplazar todo lo que no
 *     sea letra por espacio (la puntuación queda fuera).
 *  2. Partir en palabras y armar el mapa palabra -> frecuencia usando
 *     getOrDefault(...) o merge(...).
 *  3. mostrarTop3(...): imprimir las 3 palabras más repetidas CON sus conteos,
 *     recorriendo el mapa a mano. Prohibido streams: todavía no llegamos.
 *  BONUS: en caso de empate de conteos, desempatar por orden alfabético para
 *  que la salida sea determinista.
 *
 *  PISTAS
 *  ------
 *  - texto.toLowerCase().replaceAll("[^a-záéíóúüñ]", " ") deja solo letras.
 *  - split("\\s+") parte por espacios; ojo con cadenas vacías al inicio.
 *  - Top 3 sin streams: copiá el mapa (new HashMap<>(frecuencias)) y, tres
 *    veces, buscá la entrada de mayor conteo, imprimila y borrala de la copia.
 */

import java.util.HashMap;
import java.util.Map;

public class Ejercicio5DesafioContadorDePalabras {

    static Map<String, Integer> contarFrecuencias(String texto) {
        // TODO 1: normalizar, partir y contar
        return new HashMap<>();
    }

    static void mostrarTop3(Map<String, Integer> frecuencias) {
        // TODO 2: top 3 a mano, sin streams
    }

    public static void main(String[] args) {
        String texto = "Java es un lenguaje y la JVM ejecuta Java. "
                     + "Aprender Java abre puertas, y practicar Java consolida y motiva.";

        Map<String, Integer> frecuencias = contarFrecuencias(texto);
        System.out.println("Palabras distintas: " + frecuencias.size());
        mostrarTop3(frecuencias);
        // Referencia: java aparece 4 veces, "y" 3 veces...
    }
}

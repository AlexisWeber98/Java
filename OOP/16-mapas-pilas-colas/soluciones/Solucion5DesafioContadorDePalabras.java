/*
 * ============================================================================
 *  Solución 5 (DESAFÍO) — Contador de palabras con HashMap
 * ============================================================================
 *
 *  Idea clave: normalizar ANTES de contar (minúsculas + todo lo que no sea
 *  letra se vuelve espacio). El conteo usa merge(palabra, 1, Integer::sum),
 *  primo de getOrDefault pero en una sola línea. El top 3 se calcula "a mano":
 *  tres pasadas buscando el máximo (desempate alfabético para que la salida
 *  sea determinista), sin streams porque todavía no llegamos a ellos.
 */
import java.util.HashMap;
import java.util.Map;

public class Solucion5DesafioContadorDePalabras {

    static Map<String, Integer> contarFrecuencias(String texto) {
        Map<String, Integer> frecuencias = new HashMap<>();
        String limpio = texto.toLowerCase().replaceAll("[^a-záéíóúüñ]", " ");
        for (String palabra : limpio.split("\\s+")) {
            if (palabra.isEmpty()) continue; // split puede dejar vacíos al inicio
            frecuencias.merge(palabra, 1, Integer::sum);
        }
        return frecuencias;
    }

    static void mostrarTop3(Map<String, Integer> frecuencias) {
        Map<String, Integer> copia = new HashMap<>(frecuencias); // no tocamos el original
        System.out.println("--- Top 3 palabras ---");
        for (int puesto = 1; puesto <= 3; puesto++) {
            String mejorPalabra = null;
            int mejorConteo = -1;
            for (Map.Entry<String, Integer> entrada : copia.entrySet()) {
                int conteo = entrada.getValue();
                String palabra = entrada.getKey();
                boolean ganaPorConteo = conteo > mejorConteo;
                boolean ganaAlfabeticamente = conteo == mejorConteo
                        && mejorPalabra != null
                        && palabra.compareTo(mejorPalabra) < 0;
                if (ganaPorConteo || ganaAlfabeticamente) {
                    mejorConteo = conteo;
                    mejorPalabra = palabra;
                }
            }
            if (mejorPalabra == null) break; // había menos de 3 palabras distintas
            System.out.println(puesto + ") " + mejorPalabra + ": " + mejorConteo);
            copia.remove(mejorPalabra);
        }
    }

    public static void main(String[] args) {
        String texto = "Java es un lenguaje y la JVM ejecuta Java. "
                     + "Aprender Java abre puertas, y practicar Java consolida y motiva.";

        Map<String, Integer> frecuencias = contarFrecuencias(texto);
        System.out.println("Palabras distintas: " + frecuencias.size());
        mostrarTop3(frecuencias);
        // Esperado: java=4, y=3, y el tercer puesto (empate en 1) por orden
        // alfabético: abre.
    }
}

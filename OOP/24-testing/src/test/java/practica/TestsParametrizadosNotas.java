package practica;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ejercicio 4 — Un concepto, muchos datos: tests parametrizados.
 *
 * Escribir cinco tests casi iguales para cinco notas distintas es ruido.
 * Con @ParameterizedTest el test se escribe una sola vez y los datos viajan
 * en la anotación. Acá vas a usar dos fuentes:
 *   - @ValueSource(ints = {...}): una lista simple de valores primitivos.
 *   - @CsvSource({...}): filas de varias columnas separadas por comas; cada
 *     fila se convierte en los argumentos de una ejecución del test.
 *
 * Pista: ponéle nombre a cada ejecución con name = "...{0}..." para que, si
 * algo falla, veas de una qué dato fue el culpable.
 */
public class TestsParametrizadosNotas {

    // TODO 1: con @ParameterizedTest + @ValueSource(ints = {0, 30, 59})
    //         afirmá que Calificacion.aprueba(nota) es false.

    // TODO 2: igual que el anterior, pero con {60, 75, 100}: todas aprueban.

    // TODO 3: con @CsvSource probá la etiqueta que corresponde a cada nota.
    //         Filas sugeridas (nota, etiqueta):
    //           0 → Insuficiente, 59 → Insuficiente,
    //          60 → Aprobado,     84 → Aprobado,
    //          85 → Sobresaliente, 100 → Sobresaliente.
    //         El método recibe los dos valores de cada fila como argumentos.

    /**
     * Escala de calificaciones provista por el enunciado.
     */
    static class Calificacion {

        static boolean aprueba(int nota) {
            return nota >= 60;
        }

        static String etiquetaDe(int nota) {
            if (nota < 60) {
                return "Insuficiente";
            }
            if (nota < 85) {
                return "Aprobado";
            }
            return "Sobresaliente";
        }
    }
}

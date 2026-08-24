package solucion;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Solución del Ejercicio 4 — Tests parametrizados con @ValueSource y @CsvSource.
 *
 * Seis comportamientos idénticos entre sí se prueban con tres métodos. El
 * atributo name usa {0} (y {1}) como placeholders: si una fila falla, el
 * reporte muestra exactamente qué datos la provocaron.
 */
public class TestsParametrizadosNotas {

    @ParameterizedTest(name = "la nota {0} no aprueba")
    @ValueSource(ints = {0, 30, 59})
    void notasBajasNoAprueban(int nota) {
        assertFalse(Calificacion.aprueba(nota));
    }

    @ParameterizedTest(name = "la nota {0} aprueba")
    @ValueSource(ints = {60, 75, 100})
    void notasSuficientesAprueban(int nota) {
        assertTrue(Calificacion.aprueba(nota));
    }

    @ParameterizedTest(name = "nota {0} → {1}")
    @CsvSource({
            "0, Insuficiente",
            "59, Insuficiente",
            "60, Aprobado",
            "84, Aprobado",
            "85, Sobresaliente",
            "100, Sobresaliente"
    })
    void laEtiquetaCoincideConLaNota(int nota, String etiquetaEsperada) {
        assertEquals(etiquetaEsperada, Calificacion.etiquetaDe(nota));
    }

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

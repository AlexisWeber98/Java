package solucion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Solución del Ejercicio 2 — Probar excepciones con assertThrows.
 *
 * assertThrows hace dos cosas: afirmar que la excepción esperada se lanza y
 * devolverte la instancia para inspeccionarla. Si la lambda no lanza nada,
 * el test falla; si lanza otra excepción, también.
 */
public class ProbandoExcepciones {

    @Test
    void edadNegativaLanzaExcepcion() {
        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> ValidadorEdad.validar(-5));

        assertTrue(excepcion.getMessage().contains("edad"));
    }

    @Test
    void edadPorEncimaDelMaximoLanzaExcepcion() {
        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> ValidadorEdad.validar(200));

        assertTrue(excepcion.getMessage().contains("edad"));
    }

    /**
     * Extra: el caso feliz también se prueba. Un validador que solo lanzara
     * excepciones para todo pasaría estos tests sin problema.
     */
    @Test
    void edadValidaNoLanzaNada() {
        assertDoesNotThrow(() -> ValidadorEdad.validar(35));
        assertDoesNotThrow(() -> ValidadorEdad.validar(120));
    }

    /** Clase bajo prueba provista por el enunciado. */
    static class ValidadorEdad {

        static void validar(int edad) {
            if (edad < 0 || edad > 120) {
                throw new IllegalArgumentException(
                        "La edad debe estar entre 0 y 120, pero se recibió: " + edad);
            }
        }
    }
}

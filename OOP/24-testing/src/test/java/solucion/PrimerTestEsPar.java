package solucion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Solución del Ejercicio 1 — Tu primer test con JUnit 5.
 *
 * Tres afirmaciones, un patrón: dado un número, cuando se consulta esPar,
 * entonces el resultado es el esperado. El cero va siempre: los casos borde
 * son donde viven los bugs.
 */
public class PrimerTestEsPar {

    @Test
    void cuatroEsPar() {
        assertTrue(CalculadoraUtil.esPar(4));
    }

    @Test
    void sieteNoEsPar() {
        assertFalse(CalculadoraUtil.esPar(7));
    }

    @Test
    void ceroEsPar() {
        assertTrue(CalculadoraUtil.esPar(0));
    }

    /**
     * Utilidad provista por el enunciado.
     */
    static class CalculadoraUtil {

        static boolean esPar(int numero) {
            return numero % 2 == 0;
        }
    }
}

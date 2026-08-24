package soluciones;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** SOLUCIÓN Ejercicio 1: función pura probada con casos true/false/borde. */
@DisplayName("Solución 1: tu primer test")
class TuPrimerTestSolucion {

    static boolean esPar(int numero) {
        return numero % 2 == 0;
    }

    @Test
    @DisplayName("un número par devuelve true")
    void dadoNumeroPar_cuandoConsulto_entoncesDevuelveTrue() {
        // Arrange
        int numero = 4;

        // Act + Assert
        assertTrue(esPar(numero));
    }

    @Test
    @DisplayName("un número impar devuelve false")
    void dadoNumeroImpar_cuandoConsulto_entoncesDevuelveFalse() {
        // Arrange
        int numero = 7;

        // Act + Assert
        assertFalse(esPar(numero));
    }

    @Test
    @DisplayName("el cero es par (caso borde)")
    void dadoCero_cuandoConsulto_entoncesEsPar() {
        // Arrange
        int numero = 0;

        // Act + Assert
        assertTrue(esPar(numero), "el cero es par por definición");
    }
}

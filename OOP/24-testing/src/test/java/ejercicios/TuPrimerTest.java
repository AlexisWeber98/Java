package ejercicios;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * EJERCICIO 1 — Tu primer test.
 *
 * Objetivo: probar una función pura `esPar(int)` con casos verdaderos,
 * falsos y el caso borde del cero.
 *
 * Pasos:
 *   1. Implementá `esPar` en esta clase (abajo está el stub).
 *   2. Quitá los @Disabled de los tres tests.
 *   3. Corré `mvn test` y llevalos a verde.
 *
 * Pista: un número n es par si n % 2 == 0.
 */
@DisplayName("Ejercicio 1: tu primer test")
class TuPrimerTest {

    // TODO: implementá la lógica real
    static boolean esPar(int numero) {
        return false;
    }

    @Test
    @DisplayName("un número par devuelve true")
    @Disabled("Activame cuando implementes esPar")
    void dadoNumeroPar_cuandoConsulto_entoncesDevuelveTrue() {
        // Arrange
        int numero = 4;

        // Act + Assert
        assertTrue(esPar(numero));
    }

    @Test
    @DisplayName("un número impar devuelve false")
    @Disabled("Activame cuando implementes esPar")
    void dadoNumeroImpar_cuandoConsulto_entoncesDevuelveFalse() {
        // Arrange
        int numero = 7;

        // Act + Assert
        assertFalse(esPar(numero));
    }

    @Test
    @DisplayName("el cero es par (caso borde)")
    @Disabled("Activame cuando implementes esPar")
    void dadoCero_cuandoConsulto_entoncesEsPar() {
        // Arrange
        int numero = 0;

        // Act + Assert
        assertTrue(esPar(numero));
    }
}

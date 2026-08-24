package ejercicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * EJERCICIO 2 — Probando excepciones.
 *
 * Objetivo: usar assertThrows para validar que `validarEdad` rechaza
 * edades inválidas, e inspeccionar el mensaje de la excepción devuelta.
 *
 * Pasos:
 *   1. Implementá `validarEdad`: lanza IllegalArgumentException si la edad
 *      es negativa o mayor a 120, con un mensaje descriptivo.
 *   2. Quitá los @Disabled.
 *   3. Corré `mvn test` y llevalos a verde.
 */
@DisplayName("Ejercicio 2: probando excepciones")
class ProbandoExcepciones {

    // TODO: implementá la validación real
    static void validarEdad(int edad) {
        throw new UnsupportedOperationException("TODO: implementar");
    }

    @Test
    @DisplayName("una edad negativa lanza IllegalArgumentException")
    @Disabled("Activame cuando implementes validarEdad")
    void dadoEdadNegativa_cuandoValido_entoncesLanzaExcepcion() {
        // Arrange
        int edadInvalida = -5;

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> validarEdad(edadInvalida));
    }

    @Test
    @DisplayName("una edad mayor a 120 lanza excepción con mensaje explicativo")
    @Disabled("Activame cuando implementes validarEdad")
    void dadoEdadExcesiva_cuandoValido_entoncesMensajeExplicaElProblema() {
        // Arrange
        int edadInvalida = 200;

        // Act + Assert: capturamos la excepción para inspeccionar su mensaje
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> validarEdad(edadInvalida)
        );

        assertEquals("La edad debe estar entre 0 y 120", excepcion.getMessage());
    }

    @Test
    @DisplayName("una edad válida no lanza nada")
    @Disabled("Activame cuando implementes validarEdad")
    void dadoEdadValida_cuandoValido_entoncesNoPasaNada() {
        // Arrange
        int edadValida = 30;

        // Act + Assert: si llega acá sin lanzar, el test pasa
        validarEdad(edadValida);
    }
}

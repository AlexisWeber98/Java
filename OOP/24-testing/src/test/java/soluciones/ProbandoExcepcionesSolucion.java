package soluciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** SOLUCIÓN Ejercicio 2: assertThrows + inspección del mensaje. */
@DisplayName("Solución 2: probando excepciones")
class ProbandoExcepcionesSolucion {

    static void validarEdad(int edad) {
        if (edad < 0 || edad > 120) {
            throw new IllegalArgumentException("La edad debe estar entre 0 y 120");
        }
    }

    @Test
    @DisplayName("una edad negativa lanza IllegalArgumentException")
    void dadoEdadNegativa_cuandoValido_entoncesLanzaExcepcion() {
        // Arrange
        int edadInvalida = -5;

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> validarEdad(edadInvalida));
    }

    @Test
    @DisplayName("una edad mayor a 120 lanza excepción con mensaje explicativo")
    void dadoEdadExcesiva_cuandoValido_entoncesMensajeExplicaElProblema() {
        // Arrange
        int edadInvalida = 200;

        // Act: assertThrows DEVUELVE la excepción, así podemos inspeccionarla
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> validarEdad(edadInvalida)
        );

        // Assert
        assertEquals("La edad debe estar entre 0 y 120", excepcion.getMessage());
    }

    @Test
    @DisplayName("una edad válida no lanza nada")
    void dadoEdadValida_cuandoValido_entoncesNoPasaNada() {
        // Arrange
        int edadValida = 30;

        // Act + Assert: si llega acá sin lanzar, el test pasa
        validarEdad(edadValida);
    }
}

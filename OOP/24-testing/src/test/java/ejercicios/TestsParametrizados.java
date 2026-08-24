package ejercicios;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * EJERCICIO 4 — Tests parametrizados.
 *
 * Objetivo: validar `esMayorDeEdad(int)` con muchos casos en un solo
 * método, incluyendo el caso borde de los 18 años. Un @ParameterizedTest
 * con @ValueSource reemplaza decenas de copy-paste.
 *
 * Pasos:
 *   1. Implementá esMayorDeEdad (18 o más devuelve true).
 *   2. Quitá los @Disabled y corré `mvn test`.
 */
@DisplayName("Ejercicio 4: tests parametrizados")
class TestsParametrizados {

    // TODO: implementá la lógica real
    static boolean esMayorDeEdad(int edad) {
        return false;
    }

    @ParameterizedTest(name = "edad {0} es mayor de edad")
    @ValueSource(ints = {18, 19, 25, 65, 120})
    @DisplayName("edades desde 18 en adelante son mayor de edad")
    @Disabled("Activame cuando implementes esMayorDeEdad")
    void dadoEdadDesde18_cuandoConsulto_entoncesEsMayorDeEdad(int edad) {
        // Arrange: la edad llega como parámetro

        // Act + Assert
        org.junit.jupiter.api.Assertions.assertTrue(esMayorDeEdad(edad));
    }

    @ParameterizedTest(name = "edad {0} NO es mayor de edad")
    @ValueSource(ints = {0, 1, 15, 17})
    @DisplayName("edades menores a 18 no son mayor de edad")
    @Disabled("Activame cuando implementes esMayorDeEdad")
    void dadoEdadMenorA18_cuandoConsulto_entoncesNoEsMayorDeEdad(int edad) {
        // Arrange: la edad llega como parámetro

        // Act + Assert
        org.junit.jupiter.api.Assertions.assertFalse(esMayorDeEdad(edad));
    }
}

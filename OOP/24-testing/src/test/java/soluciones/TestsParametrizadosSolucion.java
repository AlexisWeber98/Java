package soluciones;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/** SOLUCIÓN Ejercicio 4: un método parametrizado reemplaza el copy-paste. */
@DisplayName("Solución 4: tests parametrizados")
class TestsParametrizadosSolucion {

    static boolean esMayorDeEdad(int edad) {
        return edad >= 18;
    }

    @ParameterizedTest(name = "edad {0} es mayor de edad")
    @ValueSource(ints = {18, 19, 25, 65, 120})
    @DisplayName("edades desde 18 en adelante son mayor de edad")
    void dadoEdadDesde18_cuandoConsulto_entoncesEsMayorDeEdad(int edad) {
        // Arrange: la edad llega como parámetro

        // Act + Assert
        Assertions.assertTrue(esMayorDeEdad(edad));
    }

    @ParameterizedTest(name = "edad {0} NO es mayor de edad")
    @ValueSource(ints = {0, 1, 15, 17})
    @DisplayName("edades menores a 18 no son mayor de edad")
    void dadoEdadMenorA18_cuandoConsulto_entoncesNoEsMayorDeEdad(int edad) {
        // Arrange: la edad llega como parámetro

        // Act + Assert
        Assertions.assertFalse(esMayorDeEdad(edad));
    }
}

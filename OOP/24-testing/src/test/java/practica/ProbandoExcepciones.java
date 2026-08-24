package practica;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ejercicio 2 — Probar excepciones con assertThrows.
 *
 * ValidadorEdad.validar(int) lanza IllegalArgumentException cuando la edad
 * está fuera del rango válido [0, 120]. Tu misión: comprobar que efectivamente
 * lanza y que el mensaje mencione "edad".
 *
 * Pista: assertThrows recibe la clase esperada y un Executable (una lambda):
 *         IllegalArgumentException excepcion = assertThrows(
 *                 IllegalArgumentException.class, () -> ValidadorEdad.validar(-5));
 *         Después inspeccionás el mensaje con assertTrue(excepcion.getMessage().contains(...)).
 */
public class ProbandoExcepciones {

    // TODO 1: validá la edad -5 y afirmá que se lanza IllegalArgumentException.

    // TODO 2: ahora con 200: también debe lanzarse.

    // TODO 3: además de que se lance, verificá el mensaje: debe contener la
    //         palabra "edad". El mensaje de una excepción es la primera pista
    //         que lee quien depura, así que también se prueba.

    /**
     * Clase bajo prueba provista por el enunciado.
     */
    static class ValidadorEdad {

        static void validar(int edad) {
            if (edad < 0 || edad > 120) {
                throw new IllegalArgumentException(
                        "La edad debe estar entre 0 y 120, pero se recibió: " + edad);
            }
        }
    }
}

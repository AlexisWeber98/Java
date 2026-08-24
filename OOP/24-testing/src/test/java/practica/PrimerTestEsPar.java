package practica;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ejercicio 1 — Tu primer test con JUnit 5.
 *
 * Un test se lee como una frase: "dado X, cuando llamo a esPar, entonces
 * obtengo Y". CalculadoraUtil ya te da el método esPar(int); tu trabajo es
 * escribir los tests que lo comprueban.
 *
 * Pista: usá assertEquals(valorEsperado, valorObtenido). El orden importa:
 * primero lo esperado, después lo real.
 */
public class PrimerTestEsPar {

    // TODO 1: escribí un test que afirme que esPar(4) es true.
    //         Nombralo descriptivamente, por ejemplo: cuatroEsPar().

    // TODO 2: escribí un test que afirme que esPar(7) es false.

    // TODO 3: el caso borde más importante: esPar(0) debe ser true. ¿Por qué
    //         cero? Porque es el típico valor que rompe implementaciones
    //         ingenuas. Corrélo y después mirá el código de esPar para
    //         entender por qué funciona.

    /**
     * Utilidad provista por el enunciado: no la modifiques, solo probála.
     */
    static class CalculadoraUtil {

        static boolean esPar(int numero) {
            return numero % 2 == 0;
        }
    }
}

package ejercicios;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * EJERCICIO 5 — Desafío TDD: rojo, verde, refactor.
 *
 * SPEC de CajaFuerte:
 *   - Se crea con un saldo inicial (double).
 *   - depositar(monto): suma al saldo; rechaza montos negativos lanzando
 *     IllegalArgumentException.
 *   - extraer(monto): resta del saldo; si el monto supera el saldo disponible
 *     lanza SaldoInsuficienteException (excepción propia, la creás vos);
 *     también rechaza montos negativos con IllegalArgumentException.
 *
 * METODOLOGÍA TDD:
 *   1. FASE ROJO: escribí TODOS los tests primero en esta clase
 *      (van a fallar: CajaFuerte ni siquiera existe → error de compilación,
 *      que también cuenta como rojo). Creá una clase CajaFuerte vacía con los
 *      métodos firmando `throw new UnsupportedOperationException()` para
 *      poder compilar y ver tests rojos por aserción.
 *   2. FASE VERDE: implementá lo mínimo para que cada test pase.
 *   3. REFACTOR: mejorá nombres/estructura sin romper tests.
 *
 * La solución completa está en soluciones.DesafioTDDRojoVerdeSolucion.
 */
@DisplayName("Ejercicio 5: desafío TDD rojo-verde")
class DesafioTDDRojoVerde {

    @Test
    @DisplayName("escribí acá tus tests siguiendo el spec de arriba")
    @Disabled("Este es tu lienzo: reemplazame por tus tests")
    void dadoQueEmpiezo_elDesafio_entoncesEscriboLosTestsPrimero() {
        // TODO: borrá este placeholder y escribí tus tests:
        //   - caja nueva arranca con el saldo inicial
        //   - depositar suma al saldo
        //   - depositar monto negativo lanza IllegalArgumentException
        //   - extraer descuenta del saldo
        //   - extraer más que el saldo lanza SaldoInsuficienteException
        //   - extraer monto negativo lanza IllegalArgumentException
    }
}

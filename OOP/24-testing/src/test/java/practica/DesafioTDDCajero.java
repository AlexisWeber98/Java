package practica;

/**
 * Ejercicio 5 — Desafío TDD: primero el test, después el código.
 *
 * En Test-Driven Development el ciclo es rojo → verde → refactorizar:
 *   1. Escribís un test que describe el comportamiento que falta. Falla (rojo).
 *   2. Escribís el mínimo código que lo hace pasar (verde).
 *   3. Limpiás el código sin cambiar el comportamiento.
 *
 * La especificación del cajero es:
 *   - new Cajero(saldoInicial): arranca con el saldo indicado; se consulta
 *     con consultarSaldo().
 *   - retirar(monto): descuenta del saldo; si el monto supera lo disponible
 *     lanza SaldoInsuficienteException y el saldo queda intacto.
 *   - depositar(monto): suma al saldo acumulado.
 *
 * Consigna: ANTES de tocar practica.Cajero, completá ESTA clase con los tests
 * que cubran toda la especificación. Casos sugeridos:
 *   TODO 1: el saldo inicial consultado es el recibido por el constructor.
 *   TODO 2: depositar(monto) acumula sobre el saldo actual.
 *   TODO 3: retirar(monto) descuenta del saldo actual.
 *   TODO 4: retirar más de lo disponible lanza SaldoInsuficienteException Y
 *           deja el saldo sin cambios (dos afirmaciones en el mismo test).
 *   TODO 5 (opcional): montos negativos en depositar o retirar. Decidí vos
 *           qué debería pasar y escribaló como test: en TDD el test ES la
 *           especificación ejecutable.
 *
 * Cuando tus tests compilen pero fallen (rojo), abrí practica/Cajero.java,
 * implementá los TODOs y volvé a correr hasta llegar al verde.
 */
public class DesafioTDDCajero {

    // TODO: escribí acá la clase de tests completa según los casos sugeridos.
    //       Te van a servir assertEquals para los saldos y assertThrows para
    //       la excepción.
}

package solucion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Solución del Ejercicio 5 — Desafío TDD: la suite escrita ANTES de
 * implementar Cajero.
 *
 * Cada test describe un punto de la especificación en lenguaje del negocio:
 * saldo inicial, acumulación por depósito, descuento por retiro y el caso de
 * fondos insuficientes. La implementación (Cajero, más abajo en esta misma
 * solución) se escribió recién después, con los tests ya fallando en rojo.
 */
public class DesafioTDDCajero {

    @Test
    void elSaldoInicialEsElRecibidoPorElConstructor() {
        Cajero cajero = new Cajero(1000);

        assertEquals(1000, cajero.consultarSaldo());
    }

    @Test
    void depositarAcumulaSobreElSaldoActual() {
        Cajero cajero = new Cajero(1000);

        cajero.depositar(500);
        cajero.depositar(250);

        assertEquals(1750, cajero.consultarSaldo());
    }

    @Test
    void retirarDescuentaDelSaldoActual() {
        Cajero cajero = new Cajero(1000);

        cajero.retirar(400);

        assertEquals(600, cajero.consultarSaldo());
    }

    @Test
    void retirarSinFondosLanzaExcepcionYDejaElSaldoIntacto() {
        Cajero cajero = new Cajero(1000);

        SaldoInsuficienteException excepcion = assertThrows(
                SaldoInsuficienteException.class,
                () -> cajero.retirar(1500));

        assertTrue(excepcion.getMessage().contains("saldo"));
        assertEquals(1000, cajero.consultarSaldo());
    }
}

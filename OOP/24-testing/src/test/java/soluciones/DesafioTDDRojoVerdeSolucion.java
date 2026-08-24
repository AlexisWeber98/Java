package soluciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * SOLUCIÓN Ejercicio 5: estos tests se escribieron ANTES que CajaFuerte
 * (fase rojo: no compilaba → rojo por error de compilación), y después
 * la implementación los llevó a verde.
 */
@DisplayName("Solución 5: desafío TDD rojo-verde")
class DesafioTDDRojoVerdeSolucion {

    private CajaFuerte caja;

    @BeforeEach
    void prepararCaja() {
        caja = new CajaFuerte(100.0);
    }

    @Test
    @DisplayName("una caja nueva arranca con el saldo inicial")
    void dadoSaldoInicial100_cuandoCreoLaCaja_entoncesElSaldoEs100() {
        // Arrange: lo hizo @BeforeEach

        // Act + Assert
        assertEquals(100.0, caja.getSaldo(), 0.0001);
    }

    @Test
    @DisplayName("depositar suma al saldo")
    void dadoCajaCon100_cuandoDeposito50_entoncesElSaldoEs150() {
        // Arrange (fixture en @BeforeEach)

        // Act
        caja.depositar(50.0);

        // Assert
        assertEquals(150.0, caja.getSaldo(), 0.0001);
    }

    @Test
    @DisplayName("depositar un monto negativo lanza IllegalArgumentException")
    void dadoCajaCon100_cuandoDepositoMontoNegativo_entoncesLanzaExcepcion() {
        // Arrange (fixture en @BeforeEach)

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> caja.depositar(-10));
    }

    @Test
    @DisplayName("extraer descuenta del saldo")
    void dadoCajaCon100_cuandoExtraigo40_entoncesElSaldoEs60() {
        // Arrange (fixture en @BeforeEach)

        // Act
        caja.extraer(40.0);

        // Assert
        assertEquals(60.0, caja.getSaldo(), 0.0001);
    }

    @Test
    @DisplayName("extraer más que el saldo lanza SaldoInsuficienteException")
    void dadoCajaCon100_cuandoExtraigo200_entoncesLanzaSaldoInsuficiente() {
        // Arrange (fixture en @BeforeEach)

        // Act + Assert
        assertThrows(SaldoInsuficienteException.class, () -> caja.extraer(200));
    }

    @Test
    @DisplayName("extraer un monto negativo lanza IllegalArgumentException")
    void dadoCajaCon100_cuandoExtraigoMontoNegativo_entoncesLanzaExcepcion() {
        // Arrange (fixture en @BeforeEach)

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> caja.extraer(-5));
    }
}

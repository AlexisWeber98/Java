package dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Suite de tests unitarios de CalculadoraDescuentos.
 * Cada test es INDEPENDIENTE: JUnit crea una instancia nueva de la clase por test.
 */
@DisplayName("Calculadora de descuentos")
class CalculadoraDescuentosTest {

    private CalculadoraDescuentos calculadora;

    @BeforeEach
    void prepararEscenario() {
        // Se ejecuta ANTES de cada test: fixture compartida, siempre fresca
        calculadora = new CalculadoraDescuentos();
    }

    @Test
    @DisplayName("un cliente común sin compra grande no recibe descuento")
    void dadoClienteComun_cuandoCompraMenosDe100Mil_entoncesNoHayDescuento() {
        // Arrange: escenario listo en @BeforeEach

        // Act
        double descuento = calculadora.calcularDescuento(50_000, false);

        // Assert
        assertEquals(0.0, descuento, 0.0001,
            "un cliente común con compra chica no debería tener descuento");
    }

    @Test
    @DisplayName("un cliente VIP recibe 15% de descuento")
    void dadoClienteVip_cuandoCompra50000_entoncesDescuentoEs15Porciento() {
        // Arrange (fixture en @BeforeEach)

        // Act
        double descuento = calculadora.calcularDescuento(50_000, true);

        // Assert
        assertEquals(0.15, descuento, 0.0001);
    }

    @Test
    @DisplayName("VIP con compra mayor a $100k acumula y queda topeado al 20%")
    void dadoClienteVip_cuandoCompra100000_entoncesDescuentoTotalEs20Porciento() {
        // Arrange (fixture en @BeforeEach)

        // Act: VIP (15%) + compra grande (5%) = 20%, justo el tope del combo
        double descuento = calculadora.calcularPrecioFinal(150_000, true);

        // Assert: precio final = 150000 * (1 - 0.20)
        assertEquals(120_000.0, descuento, 0.01);
    }

    @Test
    @DisplayName("compra mayor a $100k de cliente común suma 5% extra")
    void dadoClienteComun_cuandoCompraMasDe100Mil_entoncesTiene5PorcientoExtra() {
        // Arrange (fixture en @BeforeEach)

        // Act
        double descuento = calculadora.calcularDescuento(100_001, false);

        // Assert
        assertEquals(0.05, descuento, 0.0001);
    }

    @Test
    @DisplayName("el límite de compra grande es estricto: $100k exactos no suman extra")
    void dadoCompraExactamente100Mil_cuandoCalcula_entoncesNoSumaExtraPorMonto() {
        // Arrange (fixture en @BeforeEach)

        // Act
        double descuentoVipEnElLimite = calculadora.calcularDescuento(100_000, true);

        // Assert: solo el 15% VIP, sin los 5% extra
        assertEquals(0.15, descuentoVipEnElLimite, 0.0001);
    }

    @Test
    @DisplayName("precio negativo lanza IllegalArgumentException")
    void dadoPrecioNegativo_cuandoCalcula_entoncesLanzaExcepcionConMensaje() {
        // Arrange (fixture en @BeforeEach)

        // Act + Assert: assertThrows devuelve la excepción para inspeccionarla
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> calculadora.calcularDescuento(-1, false)
        );

        assertTrue(excepcion.getMessage().contains("negativo"),
            "el mensaje debería explicar el problema");
    }

    @ParameterizedTest(name = "precio {0} nunca produce descuento negativo")
    @ValueSource(doubles = {-10, -0.01, -100_000})
    @DisplayName("ningún precio negativo pasa sin excepción")
    void dadoCualquierPrecioNegativo_cuandoCalcula_entoncesSiempreLanza(double precio) {
        // Arrange (fixture en @BeforeEach)

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
            () -> calculadora.calcularPrecioFinal(precio, true));
    }
}

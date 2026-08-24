package soluciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** SOLUCIÓN Ejercicio 3: @BeforeEach + aislamiento entre tests. */
@DisplayName("Solución 3: fixture compartida con @BeforeEach")
class AntesDeCadaPruebaSolucion {

    static class Carrito {
        private final List<String> productos = new ArrayList<>();

        void agregar(String producto) {
            productos.add(producto);
        }

        int cantidadDeProductos() {
            return productos.size();
        }
    }

    private Carrito carrito;

    @BeforeEach
    void prepararCarrito() {
        carrito = new Carrito();
    }

    @Test
    @DisplayName("un carrito nuevo empieza vacío")
    void dadoCarritoRecienCreado_cuandoLoConsulto_entoncesEstaVacio() {
        // Arrange: lo hizo @BeforeEach

        // Act + Assert
        assertEquals(0, carrito.cantidadDeProductos());
    }

    @Test
    @DisplayName("agregar un producto deja el carrito con uno")
    void dadoCarritoVacio_cuandoAgregoUnProducto_entoncesTieneUno() {
        // Arrange
        carrito.agregar("Café");

        // Act + Assert
        assertEquals(1, carrito.cantidadDeProductos());
    }

    @Test
    @DisplayName("el estado no se arrastra entre tests (aislamiento)")
    void dadoTestAnteriorConProducto_cuandoArrancoEsteTest_entoncesElEstadoEsFresco() {
        // Arrange: aunque otro test agregó "Café", este carrito es NUEVO
        // porque JUnit instancia la clase de tests una vez POR test.

        // Act
        carrito.agregar("Mate");
        carrito.agregar("Bombilla");

        // Assert: si hubiera arrastre, acá habría 3 productos
        assertTrue(carrito.cantidadDeProductos() == 2,
            "cada test debe partir de un carrito recién creado");
    }
}

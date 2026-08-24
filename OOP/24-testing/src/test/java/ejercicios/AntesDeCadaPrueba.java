package ejercicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * EJERCICIO 3 — Antes de cada prueba.
 *
 * Objetivo: usar @BeforeEach para construir un Carrito que se reusa
 * en 3 tests, demostrando que cada test arranca con estado fresco.
 *
 * Pasos:
 *   1. Completá la clase Carrito (stub abajo).
 *   2. Completá el @BeforeEach para dejar `carrito` listo.
 *   3. Quitá los @Disabled y corré `mvn test`.
 *
 * Idea clave: JUnit crea una instancia NUEVA de esta clase por cada test,
 * así el carrito nunca "arrastra" productos de un test anterior.
 */
@DisplayName("Ejercicio 3: fixture compartida con @BeforeEach")
class AntesDeCadaPrueba {

    /** Stub: completá agregar() y cantidadDeProductos(). */
    static class Carrito {
        private final List<String> productos = new ArrayList<>();

        void agregar(String producto) {
            // TODO: agregar el producto a la lista
        }

        int cantidadDeProductos() {
            // TODO: devolver cuántos productos hay
            return 0;
        }
    }

    private Carrito carrito;

    // TODO: construí acá el carrito para que cada test arranque igual
    @BeforeEach
    void prepararCarrito() {
    }

    @Test
    @DisplayName("un carrito nuevo empieza vacío")
    @Disabled("Activame cuando implementes Carrito")
    void dadoCarritoRecienCreado_cuandoLoConsulto_entoncesEstaVacio() {
        // Arrange: lo hizo @BeforeEach

        // Act + Assert
        assertEquals(0, carrito.cantidadDeProductos());
    }

    @Test
    @DisplayName("agregar un producto deja el carrito con uno")
    @Disabled("Activame cuando implementes Carrito")
    void dadoCarritoVacio_cuandoAgregoUnProducto_entoncesTieneUno() {
        // Arrange
        carrito.agregar("Café");

        // Act + Assert
        assertEquals(1, carrito.cantidadDeProductos());
    }

    @Test
    @DisplayName("el estado no se arrastra entre tests (aislamiento)")
    @Disabled("Activame cuando implementes Carrito")
    void dadoTestAnteriorConProducto_cuandoArrancoEsteTest_entoncesElEstadoEsFresco() {
        // Arrange: aunque otro test agregó "Café", este carrito es NUEVO

        // Act
        carrito.agregar("Mate");
        carrito.agregar("Bombilla");

        // Assert: si hubiera arrastre, acá habría 3 productos
        assertTrue(carrito.cantidadDeProductos() == 2,
            "cada test debe partir de un carrito recién creado");
    }
}

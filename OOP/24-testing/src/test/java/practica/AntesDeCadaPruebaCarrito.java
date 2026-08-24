package practica;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ejercicio 3 — Código repetido antes de cada prueba: @BeforeEach.
 *
 * Los tres tests repiten las mismas líneas de armado del carrito. La regla es
 * clara: si todos los tests necesitan el mismo estado inicial, ese armado
 * pertenece a un método anotado con @BeforeEach, que JUnit ejecuta fresco
 * ANTES de cada test. Cada prueba arranca desde cero, sin arrastre del
 * anterior (JUnit crea una nueva instancia de la clase por cada test).
 *
 * Consigna: mové el armado repetido a un método @BeforeEach que asigne los
 * campos carrito, teclado y mouse. Cada test debe quedar solo con la acción y
 * la verificación.
 */
public class AntesDeCadaPruebaCarrito {

    // TODO 1: declará los campos compartidos:
    //         private Carrito carrito;
    //         private Producto teclado;
    //         private Producto mouse;

    // TODO 2: creá el método de armado común:
    //         @BeforeEach void prepararCarrito() { ... }

    @Test
    void agregarProductosSumaElTotal() {
        // Bloque repetido en los tres tests: este bloque va a @BeforeEach.
        Carrito carrito = new Carrito();
        Producto teclado = new Producto("Teclado", 25000);
        Producto mouse = new Producto("Mouse", 15000);

        carrito.agregar(teclado);
        carrito.agregar(mouse);

        assertEquals(40000, carrito.total());
    }

    @Test
    void vaciarReseteaElCarrito() {
        // Mismo bloque otra vez...
        Carrito carrito = new Carrito();
        Producto teclado = new Producto("Teclado", 25000);

        carrito.agregar(teclado);
        carrito.vaciar();

        assertEquals(0, carrito.total());
    }

    @Test
    void agregarElMismoProductoDosVecesEstaPermitido() {
        // ...y de nuevo. ¿Notás cuántas líneas idénticas llevamos?
        Carrito carrito = new Carrito();
        Producto teclado = new Producto("Teclado", 25000);

        carrito.agregar(teclado);
        carrito.agregar(teclado);

        assertEquals(50000, carrito.total());
    }

    /** Clases de apoyo provistas por el enunciado. */
    static class Producto {

        final String nombre;
        final int precio;

        Producto(String nombre, int precio) {
            this.nombre = nombre;
            this.precio = precio;
        }
    }

    static class Carrito {

        private final List<Producto> items = new ArrayList<>();

        void agregar(Producto producto) {
            items.add(producto);
        }

        void vaciar() {
            items.clear();
        }

        int total() {
            return items.stream().mapToInt(p -> p.precio).sum();
        }
    }
}

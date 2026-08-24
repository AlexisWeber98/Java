package solucion;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Solución del Ejercicio 3 — Armado común con @BeforeEach.
 *
 * El armado repetido vive ahora en prepararCarrito(), anotado con
 * @BeforeEach: JUnit lo ejecuta antes de CADA test sobre una instancia nueva
 * de la clase. Los tests quedaron reducidos a acción + verificación, que es
 * justo lo que se quiere leer cuando algo falla.
 */
public class AntesDeCadaPruebaCarrito {

    private Carrito carrito;
    private Producto teclado;
    private Producto mouse;

    @BeforeEach
    void prepararCarrito() {
        carrito = new Carrito();
        teclado = new Producto("Teclado", 25000);
        mouse = new Producto("Mouse", 15000);
    }

    @Test
    void agregarProductosSumaElTotal() {
        carrito.agregar(teclado);
        carrito.agregar(mouse);

        assertEquals(40000, carrito.total());
    }

    @Test
    void vaciarReseteaElCarrito() {
        carrito.agregar(teclado);
        carrito.vaciar();

        assertEquals(0, carrito.total());
    }

    @Test
    void agregarElMismoProductoDosVecesEstaPermitido() {
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

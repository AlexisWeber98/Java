/*
 * ============================================================================
 * Ejercicio 5 — Desafío: carrito inteligente
 * ============================================================================
 *
 * ENUNCIADO:
 *   Modelá un Carrito con una lista interna ArrayList<Producto> que exponga:
 *     - agregarProducto(Producto): devuelve false si ya existe otro producto
 *       con el MISMO nombre (no admite duplicados); true si pudo agregar.
 *     - quitarProducto(String nombre): elimina el producto por nombre y
 *       devuelve true si existía; false en caso contrario.
 *     - calcularTotal(): suma de los precios.
 *     - productoMasCaro(): el producto de mayor precio, o null si está vacío.
 *   Después simulá una sesión de compra imprimiendo el estado del carrito
 *   tras cada operación, incluyendo los fracasos (intentá agregar un duplicado).
 *
 * REQUISITOS:
 *   - El carrito encapsula su lista: ninguna otra clase la modifica directo.
 *   - Los métodos devuelven valores para que el llamador decida qué informar.
 *   - productoMasCaro() debe resolverse con un bucle, sin streams.
 *
 * PISTAS:
 *   - Antes de agregar, recorré la lista comparando nombres (misma técnica
 *     del Ejercicio 2) para detectar el duplicado.
 *   - calcularTotal() puede usar un acumulador double en un for mejorado.
 *   - productoMasCaro() arranca con Producto masCaro = null y lo reemplaza
 *     cuando encuentra un precio mayor; devolvé null si la lista está vacía.
 *   - Formateá montos con printf("%.2f", monto) para verlos prolijos.
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.List;

public class Ejercicio5DesafioCarritoInteligente {

    static class Producto {
        private final String nombre;
        private final double precio;

        Producto(String nombre, double precio) {
            this.nombre = nombre;
            this.precio = precio;
        }

        String getNombre() {
            return nombre;
        }

        double getPrecio() {
            return precio;
        }

        @Override
        public String toString() {
            return nombre + " ($" + precio + ")";
        }
    }

    static class Carrito {
        private final List<Producto> productos = new ArrayList<>();

        boolean agregarProducto(Producto nuevo) {
            // TODO: si ya existe un producto con ese nombre, devolvé false;
            //       si no, agregalo y devolvé true
            return false;
        }

        boolean quitarProducto(String nombre) {
            // TODO: buscá por nombre; si existe, eliminá y devolvé true
            return false;
        }

        double calcularTotal() {
            // TODO: acumulá los precios en un for mejorado
            return 0.0;
        }

        Producto productoMasCaro() {
            // TODO: bucle buscando el mayor precio; devolvé null si no hay productos
            return null;
        }

        int cantidadProductos() {
            // TODO: devolvé cuántos productos hay en el carrito
            return 0;
        }
    }

    public static void main(String[] args) {
        Carrito carrito = new Carrito();

        // TODO: simulación sugerida -
        //  1. agregá "Auriculares" y "Cable HDMI"; mostrá el total
        //  2. intentá agregar "Auriculares" otra vez (debe fallar) y mostrá el total
        //  3. mostrá el producto más caro
        //  4. quitá "Cable HDMI" y volvé a mostrar total y más caro
        //  5. intentá quitar un producto inexistente
    }
}

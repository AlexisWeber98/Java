/*
 * ============================================================================
 * Ejercicio 5 — Desafío: carrito inteligente (SOLUCIÓN)
 * ============================================================================
 * Encapsulamiento con ArrayList interno, control de duplicados por nombre,
 * total acumulado, máximo con bucle y simulación con estados visibles.
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
            // Rechazo de duplicados: la identidad del producto es su nombre.
            if (indicePorNombre(nuevo.getNombre()) >= 0) {
                return false;
            }
            productos.add(nuevo);
            return true;
        }

        boolean quitarProducto(String nombre) {
            int indice = indicePorNombre(nombre);
            if (indice < 0) {
                return false;
            }
            productos.remove(indice);
            return true;
        }

        double calcularTotal() {
            double total = 0.0;
            for (Producto producto : productos) {
                total += producto.getPrecio();
            }
            return total;
        }

        Producto productoMasCaro() {
            if (productos.isEmpty()) {
                return null;
            }
            Producto masCaro = productos.get(0);
            for (Producto producto : productos) {
                if (producto.getPrecio() > masCaro.getPrecio()) {
                    masCaro = producto;
                }
            }
            return masCaro;
        }

        int cantidadProductos() {
            return productos.size();
        }

        /** Búsqueda compartida por nombre (misma técnica del Ejercicio 2). */
        private int indicePorNombre(String nombreBuscado) {
            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getNombre().equalsIgnoreCase(nombreBuscado)) {
                    return i;
                }
            }
            return -1;
        }
    }

    public static void main(String[] args) {
        Carrito carrito = new Carrito();

        System.out.println("== Sesion de compra ==");

        // Paso 1: dos altas exitosas.
        informarAlta(carrito, new Producto("Auriculares", 45999.90));
        informarAlta(carrito, new Producto("Cable HDMI", 8750.50));
        informarEstado(carrito);

        // Paso 2: alta duplicada -> el carrito la rechaza.
        informarAlta(carrito, new Producto("Auriculares", 44999.00)); // mismo nombre, otro precio
        informarEstado(carrito);

        // Paso 3: máximo actual.
        System.out.println("Mas caro hasta ahora: " + carrito.productoMasCaro());

        // Paso 4: baja de un producto y nuevo estado.
        System.out.println(carrito.quitarProducto("Cable HDMI")
                ? "\"Cable HDMI\" quitado del carrito."
                : "\"Cable HDMI\" no estaba en el carrito.");
        informarEstado(carrito);
        System.out.println("Mas caro ahora: " + carrito.productoMasCaro());

        // Paso 5: baja inexistente -> false, sin excepciones.
        System.out.println(carrito.quitarProducto("Webcam")
                ? "\"Webcam\" quitada."
                : "\"Webcam\" no estaba en el carrito.");
    }

    private static void informarAlta(Carrito carrito, Producto producto) {
        System.out.println(carrito.agregarProducto(producto)
                ? "Agregado: " + producto
                : "RECHAZADO (duplicado): " + producto.getNombre());
    }

    private static void informarEstado(Carrito carrito) {
        System.out.printf("Estado del carrito (%d items) - Total: $%.2f%n",
                carrito.cantidadProductos(), carrito.calcularTotal());
    }
}

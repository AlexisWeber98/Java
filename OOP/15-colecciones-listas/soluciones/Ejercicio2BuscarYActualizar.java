/*
 * ============================================================================
 * Ejercicio 2 — Buscar y actualizar (SOLUCIÓN)
 * ============================================================================
 * Búsqueda manual por campo (sin depender del equals del objeto),
 * actualización de precio y tratamiento explícito del "no encontrado".
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.List;

public class Ejercicio2BuscarYActualizar {

    static class Producto {
        private final String nombre;
        private double precio;

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

        void setPrecio(double precio) {
            this.precio = precio;
        }

        @Override
        public String toString() {
            return nombre + " ($" + precio + ")";
        }
    }

    public static void main(String[] args) {
        List<Producto> inventario = new ArrayList<>();
        inventario.add(new Producto("Teclado", 8500.0));
        inventario.add(new Producto("Mouse", 4200.0));
        inventario.add(new Producto("Monitor", 152000.0));

        System.out.println("Inventario inicial: " + inventario);

        // Caso 1: producto existente -> se actualiza su precio.
        int indiceMouse = buscarIndicePorNombre(inventario, "Mouse");
        if (indiceMouse >= 0) {
            Producto mouse = inventario.get(indiceMouse);
            double precioAnterior = mouse.getPrecio();
            mouse.setPrecio(precioAnterior * 1.10); // aumento del 10 %
            System.out.printf("Actualizado: %s pasó de $%.2f a $%.2f%n",
                    mouse.getNombre(), precioAnterior, mouse.getPrecio());
        } else {
            System.out.println("El Mouse no está en el inventario.");
        }

        // Caso 2: producto inexistente -> mensaje claro, sin excepciones.
        int indiceWebcam = buscarIndicePorNombre(inventario, "Webcam");
        if (indiceWebcam >= 0) {
            System.out.println("Actualizado: " + inventario.get(indiceWebcam));
        } else {
            System.out.println("\"Webcam\" no se encuentra en el inventario.");
        }

        System.out.println("Inventario final:   " + inventario);
    }

    private static int buscarIndicePorNombre(List<Producto> productos, String nombreBuscado) {
        // Búsqueda lineal comparando el CAMPO nombre, no el objeto completo:
        // Producto no sobrescribe equals(), así que indexOf/contains no sirven.
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getNombre().equalsIgnoreCase(nombreBuscado)) {
                return i;
            }
        }
        return -1; // convención clásica: -1 significa "no encontrado"
    }
}

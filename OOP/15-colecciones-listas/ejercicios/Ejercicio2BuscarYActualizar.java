/*
 * ============================================================================
 * Ejercicio 2 — Buscar y actualizar
 * ============================================================================
 *
 * ENUNCIADO:
 *   Tenés un inventario de productos cargado en un ArrayList<Producto>.
 *   El programa debe:
 *     1. Buscar el producto "Mouse" recorriendo la lista con un bucle y
 *        comparando el campo nombre.
 *     2. Si existe, subirle el precio un 10 % e informar el cambio.
 *     3. Buscar "Webcam" e informar correctamente que NO está en la lista.
 *
 * REQUISITOS:
 *   - La búsqueda se hace con un bucle manual que compara el nombre de cada
 *     producto (NO con indexOf ni contains: todavía no sobrescribimos equals).
 *   - Devolver el índice encontrado o -1 si no existe.
 *   - Manejar explícitamente el caso "no encontrado" con un mensaje claro.
 *
 * PISTAS:
 *   - indexOf y contains dependen de equals(): sin sobrescribirlo comparan
 *     referencias en memoria, no nombres. Por eso la búsqueda campo por campo.
 *   - nombreBuscado.equalsIgnoreCase(producto.getNombre()) hace la comparación
 *     robusta ante mayúsculas/minúsculas.
 *   - El precio nuevo es precioActual * 1.10.
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.List;

public class Ejercicio2BuscarYActualizar {

    /** Producto mínimo para practicar listas tipadas con objetos propios. */
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

        // TODO 1: buscá el índice de "Mouse" con buscarIndicePorNombre(...)

        // TODO 2: si el índice es válido, subí el precio un 10 % e informalo

        // TODO 3: repetí la búsqueda con "Webcam" y mostrá el mensaje de
        //         producto no encontrado
    }

    /**
     * Devuelve el índice del primer producto cuyo nombre coincide
     * (sin distinguir mayúsculas/minúsculas), o -1 si no existe.
     */
    private static int buscarIndicePorNombre(List<Producto> productos, String nombreBuscado) {
        // TODO: recorré con un for, compará con equalsIgnoreCase y devolvé i
        return -1;
    }
}

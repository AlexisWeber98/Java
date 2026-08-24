/*
 * ============================================================================
 *  Solución 1 — Inventario de la tienda con HashMap
 * ============================================================================
 *
 *  Idea clave: el mapa responde "¿cuánto stock hay de X?" en tiempo constante.
 *  getOrDefault(clave, 0) permite leer sin preguntar antes containsKey():
 *  un solo método cubre el caso "existe" y el "es nuevo".
 */
import java.util.HashMap;
import java.util.Map;

public class Solucion1InventarioConMapa {

    static void reponer(Map<String, Integer> inventario, String producto, int cantidad) {
        int stockActual = inventario.getOrDefault(producto, 0); // 0 si es nuevo
        inventario.put(producto, stockActual + cantidad);
    }

    static boolean vender(Map<String, Integer> inventario, String producto, int cantidad) {
        int stockActual = inventario.getOrDefault(producto, 0);
        // Doble validación: cantidad positiva y existencia con stock suficiente.
        if (cantidad <= 0 || stockActual < cantidad) {
            return false; // no tocamos el mapa: la operación falló
        }
        inventario.put(producto, stockActual - cantidad);
        return true;
    }

    static void mostrarReporte(Map<String, Integer> inventario) {
        System.out.println("--- Reporte de inventario ---");
        for (Map.Entry<String, Integer> entrada : inventario.entrySet()) {
            System.out.println(entrada.getKey() + ": " + entrada.getValue());
        }
        System.out.println("Productos distintos: " + inventario.size());
    }

    public static void main(String[] args) {
        Map<String, Integer> inventario = new HashMap<>();

        reponer(inventario, "Yerba", 10);
        reponer(inventario, "Mate", 4);
        reponer(inventario, "Yerba", 5);           // acumula: Yerba queda en 15

        System.out.println("Venta Yerba x3  -> " + vender(inventario, "Yerba", 3)); // true
        System.out.println("Venta Azucar x1 -> " + vender(inventario, "Azucar", 1)); // false
        System.out.println("Venta Mate x9  -> " + vender(inventario, "Mate", 9));    // false

        mostrarReporte(inventario);
        // Esperado: Yerba: 12, Mate: 4, Productos distintos: 2
    }
}

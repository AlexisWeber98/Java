/*
 * ============================================================================
 *  Ejercicio 1 — Inventario de la tienda con HashMap
 * ============================================================================
 *
 *  ENUNCIADO
 *  --------
 *  Administrar el stock de un kiosco modelado como un mapa:
 *  clave = nombre del producto (String), valor = unidades en stock (Integer).
 *
 *  REQUISITOS
 *  ----------
 *  1. reponer(...): suma la cantidad al stock del producto; si no existía,
 *     queda creado con esa cantidad.
 *  2. vender(...): descuenta stock SOLO si el producto existe y alcanza.
 *     Devuelve true si la venta se realizó, false en caso contrario.
 *     Usá getOrDefault(...) para leer el stock sin riesgo de NullPointerException.
 *  3. mostrarReporte(...): imprime cada producto con su stock y, al final,
 *     cuántos productos distintos hay.
 *
 *  PISTAS
 *  ------
 *  - getOrDefault(clave, 0) te da el stock guardado o 0 si la clave no existe.
 *  - Para recorrer: for (Map.Entry<String, Integer> e : inventario.entrySet()).
 *  - Probá vender algo inexistente y algo sin stock suficiente: ambas ventas
 *    deben fallar sin romper el programa ni alterar el mapa.
 */

import java.util.HashMap;
import java.util.Map;

public class Ejercicio1InventarioConMapa {

    static void reponer(Map<String, Integer> inventario, String producto, int cantidad) {
        // TODO 1: sumá 'cantidad' al stock actual del producto.
        //  Ayudate con getOrDefault(producto, 0) para saber cuánto había.
    }

    static boolean vender(Map<String, Integer> inventario, String producto, int cantidad) {
        // TODO 2: validá existencia y stock suficiente antes de descontar.
        //  Si algo falla, devolvé false SIN modificar el mapa.
        return false;
    }

    static void mostrarReporte(Map<String, Integer> inventario) {
        // TODO 3: imprimí "producto: stock" por línea y el total de productos.
    }

    public static void main(String[] args) {
        Map<String, Integer> inventario = new HashMap<>();

        reponer(inventario, "Yerba", 10);
        reponer(inventario, "Mate", 4);
        reponer(inventario, "Yerba", 5);          // ya existía: debe quedar 15

        System.out.println("Venta Yerba x3  -> " + vender(inventario, "Yerba", 3));
        System.out.println("Venta Azucar x1 -> " + vender(inventario, "Azucar", 1)); // no existe
        System.out.println("Venta Mate x9  -> " + vender(inventario, "Mate", 9));   // insuficiente

        mostrarReporte(inventario);
    }
}

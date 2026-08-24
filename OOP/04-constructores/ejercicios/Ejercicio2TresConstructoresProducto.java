/*
 * ============================================================================
 *  Ejercicio 2 — Tres constructores para Producto (sobrecarga)
 *  Módulo 04 · Constructores
 * ============================================================================
 *
 *  ENUNCIADO
 *  A veces querés nacer de varias maneras: sin saber nada, sabiendo solo el
 *  nombre, o con todos los datos. Eso es SOBRECARGA de constructores: misma
 *  clase, varias firmas.
 *
 *  Completá la clase Producto con tres constructores:
 *    - Producto()                → nombre "Desconocido", stock 0, precio 0.0
 *    - Producto(String nombre)   → stock 0 y precio 0.0
 *    - Producto(String nombre, int stock, double precio) → todo explícito
 *
 *  En el main, instanciá UN producto con cada versión y mostrá el estado de
 *  los tres con mostrarEstado().
 *
 *  REQUISITOS
 *    1. Tres constructores sobrecargados en la misma clase.
 *    2. Cada uno deja al objeto en un estado coherente.
 *    3. mostrarEstado() imprime nombre, stock y precio.
 *    4. El main usa las tres formas de construcción.
 *
 *  PISTAS
 *    - La sobrecarga se diferencia por la lista de parámetros (cantidad,
 *      tipos u orden), nunca por otra cosa: el nombre del constructor ya es
 *      fijo (el de la clase).
 *    - El compilador elige qué constructor usar mirando los argumentos de la
 *      llamada: eso pasa EN TIEMPO DE COMPILACIÓN, no en ejecución.
 *
 *  Ejecutalo:  java Ejercicio2TresConstructoresProducto.java
 * ============================================================================
 */

public class Ejercicio2TresConstructoresProducto {

    public static void main(String[] args) {
        // TODO: instanciá un producto con el constructor sin argumentos

        // TODO: instanciá otro pasando solo el nombre

        // TODO: instanciá un tercero con nombre, stock y precio

        // TODO: mostrá el estado de los tres con mostrarEstado()
    }
}

class Producto {

    private String nombre;
    private int stock;
    private double precio;

    // TODO: constructor 1 — Producto(): producto "desconocido"
    //       (nombre = "Desconocido", stock = 0, precio = 0.0)

    // TODO: constructor 2 — Producto(String nombre): stock 0 y precio 0.0

    // TODO: constructor 3 — Producto(String nombre, int stock, double precio)

    // TODO: método mostrarEstado() que imprima nombre, stock y precio
}

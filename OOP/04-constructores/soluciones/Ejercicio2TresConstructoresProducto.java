/*
 * ============================================================================
 *  Ejercicio 2 — Tres constructores para Producto · SOLUCIÓN COMENTADA
 *  Módulo 04 · Constructores
 * ============================================================================
 *  Idea clave: sobrecargar constructores es ofrecer varias "puertas de
 *  entrada" a un mismo estado coherente. El compilador elige la puerta
 *  según los argumentos.
 *
 *  Ojo: acá cada constructor asigna TODO por su cuenta. ¿Ves la repetición?
 *  En el Ejercicio 3 la curamos con encadenamiento this(...).
 * ============================================================================
 */

public class Ejercicio2TresConstructoresProducto {

    public static void main(String[] args) {
        Producto misterioso = new Producto();
        Producto mate = new Producto("Mate Imperial");
        Producto teclado = new Producto("Teclado mecánico", 12, 45999.90);

        misterioso.mostrarEstado();
        mate.mostrarEstado();
        teclado.mostrarEstado();
    }
}

class Producto {

    private String nombre;
    private int stock;
    private double precio;

    // Versión "no sabíamos nada al crearlo": estado neutro pero válido.
    Producto() {
        this.nombre = "Desconocido";
        this.stock = 0;
        this.precio = 0.0;
    }

    // Versión intermedia: sabemos cómo se llama, nada más.
    Producto(String nombre) {
        this.nombre = nombre;
        this.stock = 0;
        this.precio = 0.0;
    }

    // Versión canónica: todos los datos explícitos.
    Producto(String nombre, int stock, double precio) {
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;
    }

    void mostrarEstado() {
        System.out.printf("Producto -> nombre=%s, stock=%d, precio=%.2f%n",
                nombre, stock, precio);
    }
}

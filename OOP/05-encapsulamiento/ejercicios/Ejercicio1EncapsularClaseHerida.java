/*
 * ============================================================================
 *  Ejercicio 1 — Encapsular una clase herida
 * ============================================================================
 *
 *  ENUNCIADO
 *  Te tocó mantener un sistema con una clase mal diseñada: ProductoHerido
 *  deja sus campos expuestos y el código cliente los pisa a mano. Hoy el
 *  programa "funciona", pero ya le colaron un stock en -7 y un precio en
 *  -999 sin que la clase se enterara.
 *
 *  Tu misión: curar la clase. Encapsulala y adaptá el main para que el
 *  programa muestre exactamente lo mismo, pero sin tocar campos desde
 *  afuera.
 *
 *  REQUISITOS
 *   1. Pasá los tres campos de ProductoHerido a PRIVATE.
 *   2. Agregale un constructor que reciba nombre, precioBase y stock.
 *   3. Agregá getters para los tres campos (getNombre, getPrecioBase,
 *      getStock).
 *   4. Agregá setters para precioBase y stock (setPrecioBase, setStock).
 *   5. nombre NO tiene setter: nace en el constructor y no cambia nunca
 *      más. ¿Por qué conviene? Dejalo pensado en un comentario.
 *   6. Adaptá el main: mismas operaciones, misma salida en pantalla,
 *      pero todo pasa por los métodos.
 *
 *  PISTAS
 *   - Cuando pongas los campos en private, este main se va a ROMPER al
 *     compilar. Eso es bueno: es el compilador marcando cada mano metida
 *     en el estado ajeno. Convertí cada línea rota en getter o setter.
 *   - Un getter devuelve el valor; un setter lo reemplaza. Juntos son la
 *     puerta controlada por la que entra y sale información del objeto.
 *   - Por ahora los setters asignan y listo. La VALIDACIÓN llega en el
 *     ejercicio 2: acá primero querés sentir el cambio de diseño.
 *   - Esta clase está afuera (no anidada) a propósito: así sí te frena el
 *     compilador al privatizar los campos.
 * ============================================================================
 */

/**
 * Versión HERIDA de la clase: campos expuestos, sin constructor, el cliente
 * arma el objeto a manopla y después lo pisa cuando se le canta.
 */
class ProductoHerido {
    // TODO 1: estos tres campos pasan a PRIVATE
    String nombre;
    double precioBase;
    int stock;
}

public class Ejercicio1EncapsularClaseHerida {

    public static void main(String[] args) {
        // Código cliente de la vieja escuela: construye el objeto a mano.
        ProductoHerido teclado = new ProductoHerido();
        teclado.nombre = "Teclado mecánico";
        teclado.precioBase = 25000.0;
        teclado.stock = 10;

        // Y después viene el abuso clásico: cualquiera muta sin control.
        teclado.stock = -7;        // ¿stock negativo? Hoy pasa sin aviso.
        teclado.precioBase = -999; // ¿precio negativo? También.

        System.out.println("Producto: " + teclado.nombre);
        System.out.println("Precio : " + teclado.precioBase);
        System.out.println("Stock  : " + teclado.stock);

        // TODO 2: encapsulá ProductoHerido (constructor + getters + setters,
        //         nombre sin setter) y adaptá ESTE main para que haga lo
        //         mismo pero solo por la puerta de los métodos.
    }
}

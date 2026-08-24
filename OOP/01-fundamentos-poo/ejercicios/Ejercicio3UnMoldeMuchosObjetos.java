/*
 * ============================================
 *  Ejercicio 3: Un molde, muchos objetos
 * ============================================
 *
 * ENUNCIADO:
 *   En un almacén hay muchos productos: todos comparten la estructura, pero
 *   cada uno guarda SUS propios valores. Con la clase Producto que te dejamos
 *   empezada, creá TRES productos distintos. Después descontá stock de uno y
 *   reponé stock de otro. Imprimí los tres ANTES y DESPUÉS de los cambios
 *   para comprobar con tus propios ojos que cada objeto tiene estado
 *   independiente.
 *
 * REQUISITOS:
 *   1. Completar describir(): imprime nombre, precio y stock en una línea.
 *   2. Implementar vender(int cantidad): descuenta del stock si alcanza;
 *      si no, avisa que no hay stock suficiente.
 *   3. Implementar reponer(int cantidad): suma cantidad al stock.
 *   4. En el main: 3 productos con valores distintos, imprimir el estado de
 *      los tres (ANTES), aplicar vender() a uno y reponer() a otro, e
 *      imprimir los tres de nuevo (DESPUÉS).
 *
 * PISTAS:
 *   - Cada new crea un objeto NUEVO con su propia copia de los campos.
 *   - Si cambiás el stock de uno, los demás ni se enteran: eso es lo que tu
 *     salida tiene que demostrar.
 *   - Etiquetá la salida ("--- ANTES ---", "--- DESPUÉS ---") para que se lea la prueba.
 */
class Producto {
    String nombre;
    double precio;
    int stock;

    void describir() {
        // TODO 1: imprimí en una línea nombre, precio y stock.

    }

    void vender(int cantidad) {
        // TODO 2: si hay stock suficiente, restalo y avisá; si no, avisá que no alcanza.

    }

    void reponer(int cantidad) {
        // TODO 3: sumá cantidad al stock.

    }
}

public class Ejercicio3UnMoldeMuchosObjetos {

    public static void main(String[] args) {
        // TODO 4: creá tres productos con new y cargales valores distintos.


        // TODO 5: imprimí el estado de los tres (ANTES).


        // TODO 6: vendé unidades del primero y repone stock del segundo.


        // TODO 7: imprimí otra vez los tres (DESPUÉS). ¿El tercero cambió? ¿Por qué?

    }
}

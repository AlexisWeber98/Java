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

    System.out
        .println("Nombre del producto : " + nombre + "\n Precio del producto : " + precio + "\n Stock : " + stock);

  }

  void vender(int cantidad) {
    // TODO 2: si hay stock suficiente, restalo y avisá; si no, avisá que no
    // alcanza.

    if (cantidad > stock) {
      System.out.println("No hay stock suficiente para vender " + cantidad + " unidades de " + nombre);
    } else {
      stock -= cantidad;
      System.out.println("Se vendieron " + cantidad + " unidades de " + nombre + ". Stock restante: " + stock);
    }
  }

  void reponer(int cantidad) {
    // TODO 3: sumá cantidad al stock.
    stock += cantidad;
    System.out.println("Se repusieron " + cantidad + " unidades de " + nombre + ". Stock actual: " + stock);
  }
}

public class Ejercicio3UnMoldeMuchosObjetos {

  public static void main(String[] args) {
    // TODO 4: creá tres productos con new y cargales valores distintos.

    Producto Fideos = new Producto();
    Fideos.nombre = "Fideos";
    Fideos.precio = 100;
    Fideos.stock = 50;

    Producto Harina = new Producto();
    Harina.nombre = "harina";
    Harina.precio = 200;
    Harina.stock = 30;

    Producto Aceite = new Producto();
    Aceite.nombre = "Aceite";
    Aceite.precio = 300;
    Aceite.stock = 20;

    Producto Arroz = new Producto();
    Arroz.nombre = "Arroz";
    Arroz.precio = 150;
    Arroz.stock = 40;

    // TODO 5: imprimí el estado de los tres (ANTES).

    System.out.println("\n --------- ANTES ------------");
    Fideos.describir();
    Harina.describir();
    Aceite.describir();
    Arroz.describir();

    // TODO 6: vendé unidades del primero y repone stock del segundo.

    Fideos.vender(10);
    Harina.reponer(20);

    // TODO 7: imprimí otra vez los tres (DESPUÉS). ¿El tercero cambió? ¿Por qué?

    System.out.println("\n --------- DESPUÉS ------------");
    Fideos.describir();
    Harina.describir();
    Aceite.describir();
    Arroz.describir();

  }
}

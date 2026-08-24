/*
 * ============================================
 *  Ejercicio 4: Refactor mental
 * ============================================
 *
 * ENUNCIADO:
 *   Acá abajo tenés (como comentario) un pedazo de código procedural clásico:
 *   los datos de dos productos en variables sueltas y la misma lógica copiada
 *   y pegada para cada uno. Funciona... hasta que no funciona más.
 *
 *   Primero respondé COMO ARQUITECTO (en comentarios): ¿qué problemas ves?
 *   ¿qué clase proponés? ¿qué campos y métodos tendría?
 *   Después escribí la versión orientada a objetos y hacela funcionar.
 *
 * CÓDIGO ORIGINAL (analizalo, NO lo descomentes):
 *
 *   String nombre1 = "Yerba mate";
 *   double precio1 = 2500.0;
 *   int stock1 = 10;
 *
 *   String nombre2 = "Azúcar";
 *   double precio2 = 1200.0;
 *   int stock2 = 30;
 *
 *   System.out.println(nombre1 + " $" + precio1 + " stock: " + stock1);
 *   precio1 = precio1 - precio1 * 0.10;   // descuento del 10% al producto 1
 *   System.out.println(nombre2 + " $" + precio2 + " stock: " + stock2);
 *   precio2 = precio2 - precio2 * 0.10;   // mismo descuento, copiado a mano
 *
 *   double total = precio1 * stock1 + precio2 * stock2;
 *   System.out.println("Valor del inventario: $" + total);
 *
 * REQUISITOS:
 *   1. En comentarios: nombrá al menos 3 problemas del código original.
 *   2. Diseñá UNA clase (por ejemplo Articulo) con campos y métodos que
 *      eliminen la lógica duplicada.
 *   3. Reescribí el flujo del main usando DOS objetos de esa clase.
 *   4. Tiene que compilar y correr: java Ejercicio4RefactorMental.java
 *
 * PISTAS:
 *   - Datos que siempre viajan juntos (nombre + precio + stock) quieren vivir
 *     juntos: en una clase.
 *   - Cada bloque copiado y pegado es un método que falta.
 *   - Preguntate: si mañana entra un tercer producto, ¿cuántas líneas había
 *     que copiar en el código original? ¿Cuántas hacen falta con objetos?
 */
public class Ejercicio4RefactorMental {

    public static void main(String[] args) {
        // TODO 1: RESPONDÉ acá (en comentarios): al menos 3 problemas del original.


        // TODO 2: declará tu clase debajo de esta y recreá el flujo con DOS objetos:
        //         mostrar la tarjeta de cada uno, aplicar descuento del 10% a ambos,
        //         e imprimir el valor total del inventario.

    }
}

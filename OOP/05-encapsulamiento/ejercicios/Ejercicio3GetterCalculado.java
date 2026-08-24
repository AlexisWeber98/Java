/*
 * ============================================================================
 *  Ejercicio 3 — Getter calculado (adiós al dato viejo)
 * ============================================================================
 *
 *  ENUNCIADO
 *  Factura conoce su precioUnitario y su cantidad. Hasta acá, perfecto.
 *  El problema es el campo `total`: alguien lo "calcula" a mano y lo
 *  guarda... y después la cantidad cambia y el total queda VIEJO.
 *  Un valor que se puede calcular a partir del estado NO debería
 *  guardarse jamás: se calcula cuando alguien lo pide.
 *
 *  Tu misión: eliminar el campo `total` y hacer que getTotal() calcule
 *  precioUnitario * cantidad en el momento.
 *
 *  REQUISITOS
 *   1. La clase Factura NO tiene ningún campo `total`.
 *   2. getTotal() devuelve precioUnitario * cantidad, calculado al vuelo.
 *   3. setCantidad cambia la cantidad; nada más (no hay totales que
 *      actualizar: esa es la gracia).
 *   4. Adaptá el main para que demuestre el escenario que antes dejaba un
 *      total viejo (cambiar cantidad) y ahora muestra el valor correcto.
 *
 *  PISTAS
 *   - Distinguí estado FUENTE (lo que la clase guarda) de estado DERIVADO
 *     (lo que se calcula desde la fuente). Solo el fuente se guarda.
 *   - Menos campos = menos invariantes que cuidar = menos chances de bug.
 *   - Este patrón es el mismo del IMC del ejercicio 5: guardalo en la
 *     cabeza, que lo vas a reusar.
 * ============================================================================
 */
public class Ejercicio3GetterCalculado {

    /**
     * Versión con el bug adentro: el total vive como CAMPO y depende de que
     * alguien lo actualice a mano. Spoiler: nadie lo hace nunca.
     */
    static class Factura {
        private final double precioUnitario;
        private int cantidad;

        // EL PROBLEMA: estado derivado guardado como si fuera fuente.
        double total;

        public Factura(double precioUnitario, int cantidad) {
            this.precioUnitario = precioUnitario;
            this.cantidad = cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
            // TODO 1: fijate que acá nadie actualiza `total`...
        }

        public double getTotal() {
            // TODO 2: dejá de devolver el campo guardado y calculá fresco.
            return total;
        }
    }

    public static void main(String[] args) {
        Factura factura = new Factura(1500.0, 2);

        // Alguien "calculó" el total a mano y lo guardó en el campo...
        factura.total = 3000.0;
        System.out.println("Total inicial                : " + factura.getTotal());

        // ...y después cambió la cantidad. ¿Y el total?
        factura.setCantidad(5);
        System.out.println("Total tras cambiar cantidad a 5: " + factura.getTotal());
        System.out.println("(Si ves 3000.0, acabás de presenciar un dato viejo en vivo.)");

        // TODO 3: eliminá el campo `total`, hacé que getTotal() calcule
        //         precioUnitario * cantidad, borrá la línea que pisa el
        //         campo en este main y volvé a correr. El dato viejo deja
        //         de ser un bug: es imposible, porque no existe.
    }
}

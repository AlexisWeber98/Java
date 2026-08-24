/*
 * ============================================================================
 *  Solución 3 — Getter calculado (adiós al dato viejo)
 * ============================================================================
 *
 *  IDEA CLAVE
 *  El total se CALCULA, no se GUARDA. Mientras `total` fue un campo, el
 *  bug del dato viejo era posible: bastaba que alguien olvidara
 *  actualizarlo. Sin el campo, esa posibilidad directamente no existe.
 *
 *  Regla de oro:
 *    - Estado fuente: precioUnitario y cantidad. Se guarda.
 *    - Estado derivado: el total. Se calcula on-demand.
 *
 *  Bonus de diseño: como no hay setter para el precio unitario (nace con
 *  la factura y es final), tampoco puede desincronizarse. Cada campo que
 *  NO existe es una invariante que NO hay que cuidar.
 * ============================================================================
 */
public class Solucion3GetterCalculado {

    static class Factura {
        private final double precioUnitario;
        private int cantidad;

        // Ni rastro de `total`: si estuviera, sería un candidato a dato viejo.

        public Factura(double precioUnitario, int cantidad) {
            this.precioUnitario = precioUnitario;
            this.cantidad = cantidad;
        }

        public void setCantidad(int cantidad) {
            // Un solo campo que tocar, cero totales que sincronizar.
            this.cantidad = cantidad;
        }

        /**
         * Siempre fresco por definición: se calcula desde el estado fuente
         * en el momento de la llamada. Es ESTRUCTURALMENTE imposible que
         * quede desactualizado, porque no hay copia guardada.
         */
        public double getTotal() {
            return precioUnitario * cantidad;
        }
    }

    public static void main(String[] args) {
        Factura factura = new Factura(1500.0, 2);

        System.out.println("Total inicial                  : " + factura.getTotal());

        // El mismo escenario que antes rompía todo...
        factura.setCantidad(5);

        // ...y ahora el total acompaña solo, porque NUNCA estuvo guardado.
        System.out.println("Total tras cambiar cantidad a 5: " + factura.getTotal());
        System.out.println("Dato viejo: imposible. No hay campo que pueda quedarlo.");
    }
}

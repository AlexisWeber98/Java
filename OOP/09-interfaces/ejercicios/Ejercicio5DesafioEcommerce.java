/*
 * =============================================================================
 *  Ejercicio 5 — Desafío: ecommerce con capacidades a la carta
 *  Módulo 09 · Interfaces
 * =============================================================================
 *
 *  ENUNCIADO
 *  ---------
 *  En un ecommerce los productos NO comparten todo: uno se envía, otro no;
 *  ambos se cobran; casi todos admiten descuento. Las interfaces te dejan
 *  modelar CAPACIDADES independientes del tipo de producto.
 *    1. Declará las interfaces:
 *         - Pagable      → void cobrar()
 *         - Enviable     → double calcularEnvio()
 *         - Descontable  → double aplicarDescuento(double porcentaje)
 *    2. ProductoFisico (con pesoKg) implementa LAS TRES: cobra con tarjeta,
 *       el envío sale de pesoKg x tarifa, y aplica descuentos.
 *    3. ProductoDigital (con tamanoMb) implementa SOLO Pagable y Descontable:
 *       cobra con billetera virtual y descuenta... pero NO SE ENVÍA.
 *    4. Recorré un Producto[] procesando cada ítem: cobralo siempre, y usá
 *       instanceof CON PATRÓN para aplicar envío/descuento solo a quien
 *       tenga esa capacidad.
 *
 *  REQUISITOS
 *  ----------
 *    - Tres interfaces con las firmas exactas del enunciado.
 *    - ProductoFisico: tres contratos. ProductoDigital: dos contratos.
 *    - Un único bucle que pregunte capacidades con instanceof patrón
 *      (ej.: if (p instanceof Enviable enviable) { ... }).
 *    - aplicarDescuento valida 0 < porcentaje <= 50; si no,
 *      lanzar IllegalArgumentException.
 *
 *  PISTAS
 *  ------
 *    - instanceof con patrón (Java 16+) hace el chequeo Y el cast en una línea.
 *    - Preguntate: ¿el bucle menciona alguna clase concreta? Si la respuesta
 *      es sí, revisá tu diseño: debería hablar de capacidades, no de clases.
 * =============================================================================
 */
public class Ejercicio5DesafioEcommerce {

    static class Producto {
        protected final String nombre;
        protected final double precioBase;

        Producto(String nombre, double precioBase) {
            this.nombre = nombre;
            this.precioBase = precioBase;
        }

        String getNombre() {
            return nombre;
        }

        double getPrecioBase() {
            return precioBase;
        }
    }

    // TODO 1: declará Pagable, Enviable y Descontable según el enunciado.

    static class ProductoFisico extends Producto {
        private final double pesoKg;

        ProductoFisico(String nombre, double precioBase, double pesoKg) {
            super(nombre, precioBase);
            this.pesoKg = pesoKg;
        }

        // TODO 2: implements las TRES interfaces y cumplí cada contrato.

        double getPesoKg() {
            return pesoKg;
        }
    }

    static class ProductoDigital extends Producto {
        private final double tamanoMb;

        ProductoDigital(String nombre, double precioBase, double tamanoMb) {
            super(nombre, precioBase);
            this.tamanoMb = tamanoMb;
        }

        // TODO 3: implements SOLO Pagable + Descontable (¡nada de Enviable!).

        double getTamanoMb() {
            return tamanoMb;
        }
    }

    public static void main(String[] args) {
        System.out.println("(Stub) Completá los TODO y volvé a ejecutar este archivo.");
        // TODO 4: armá un Producto[] con al menos un físico y un digital,
        //         recorrélo y procesá cada ítem según sus capacidades reales.
    }
}

/*
 * ===========================================================================
 *  Ejercicio 4 — Mezcla de concreto y abstracto · SOLUCIÓN
 *  Módulo 08 · Clases abstractas
 * ===========================================================================
 *
 *  Cambios clave: generar() (concreto) ya llama a generarContenido()
 *  (abstracto) y ReporteStock completa su parte. Encabezado y pie existen
 *  en un único lugar: la clase base.
 *
 *  Ejecutá así:  java Ejercicio4MezclaConcretoAbstracto.java
 */

// La clase principal de la solución se llama distinto (y sin public) para
// que ejercicios y soluciones puedan compilarse juntos sin choque de
// nombres. El lanzador java toma igualmente la primera clase del archivo.
class Solucion4MezclaConcretoAbstracto {

    static abstract class GeneradorDeReporte {

        abstract void generarContenido();

        // TEMPLATE METHOD en acción: el método concreto define EL ORDEN del
        // proceso y delega los huecos a las subclases. Cuando se ejecuta,
        // la JVM resuelve qué generarContenido() correr según el tipo real
        // del objeto, aunque la llamada viva en la base.
        void generar() {
            System.out.println("==============================");
            System.out.println("       REPORTE DEL DÍA");
            System.out.println("==============================");

            generarContenido();

            System.out.println("==============================");
            System.out.println("        FIN DEL REPORTE");
            System.out.println();
        }
    }

    static class ReporteVentas extends GeneradorDeReporte {
        @Override
        void generarContenido() {
            System.out.println("* Ventas: 12 ordenes por $ 84.500");
        }
    }

    // SOLUCIÓN: ReporteStock no toca encabezado ni pie. Solo aporta su
    // contenido; todo lo demás ya estaba resuelto en la base.
    static class ReporteStock extends GeneradorDeReporte {
        @Override
        void generarContenido() {
            System.out.println("* Stock: Tornillos x 340, Tuercas x 512");
        }
    }

    public static void main(String[] args) {
        new ReporteVentas().generar();
        new ReporteStock().generar();
    }
}

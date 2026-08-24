/*
 * ===========================================================================
 *  Ejercicio 4 — Mezcla de concreto y abstracto
 *  Módulo 08 · Clases abstractas
 * ===========================================================================
 *
 *  ENUNCIADO
 *  GeneradorDeReporte define EL ESQUELETO del proceso en un método concreto
 *  generar(): imprime el encabezado, delega el corazón del reporte en el
 *  método abstracto generarContenido() y cierra con el pie. Cada subclase
 *  solo rellena su parte: nadie copia encabezado ni pie.
 *
 *  REQUISITOS
 *  1. Dentro de generar(), activá la llamada a generarContenido().
 *  2. Descomentá ReporteStock y completá su generarContenido().
 *  3. Activá la línea correspondiente del main y comprobá que ambos
 *     reportes comparten encabezado y pie.
 *
 *  PISTAS
 *  - Un método CONCRETO de la clase abstracta puede llamar a métodos
 *    abstractos: la base arma el esqueleto hoy, el detalle lo aporta cada
 *    hija en tiempo de ejecución.
 *  - Este patrón tiene nombre propio: Template Method.
 *  - El flujo esperado por reporte es: encabezado -> contenido -> pie.
 *
 *  Ejecutá así:  java Ejercicio4MezclaConcretoAbstracto.java
 */

public class Ejercicio4MezclaConcretoAbstracto {

    static abstract class GeneradorDeReporte {

        // La parte variable: cada subclase decide qué imprimir acá.
        abstract void generarContenido();

        // La parte fija: el esqueleto es concreto y vive una sola vez acá.
        void generar() {
            System.out.println("==============================");
            System.out.println("       REPORTE DEL DÍA");
            System.out.println("==============================");

            // TODO 1: descomentá esta línea para que el esqueleto invoque la
            //         parte que cada subclase aporta:
            // generarContenido();

            System.out.println("==============================");
            System.out.println("        FIN DEL REPORTE");
            System.out.println();
        }
    }

    // Modelo a imitar: solo implementa su parte del contrato.
    static class ReporteVentas extends GeneradorDeReporte {
        @Override
        void generarContenido() {
            System.out.println("* Ventas: 12 ordenes por $ 84.500");
        }
    }

    // TODO 2: descomentá esta clase y completá generarContenido():
    //         mostrá dos artículos con su cantidad (p. ej., Tornillos x 340).
    //
    // static class ReporteStock extends GeneradorDeReporte {
    //     @Override
    //     void generarContenido() {
    //
    //     }
    // }

    public static void main(String[] args) {
        new ReporteVentas().generar();

        // TODO 3: descomentá al tener listo ReporteStock:
        // new ReporteStock().generar();
    }
}

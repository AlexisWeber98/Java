// Ejemplo: los límites de la herencia.
//   - `final` en una clase: nadie puede extenderla.
//   - `final` en un método: ninguna subclase puede redefinirlo.
//   - Caso comentado donde heredar es el diseño equivocado.
//
// Ejecutar:  java ejemplos/LimitesDeLaHerencia.java

public class LimitesDeLaHerencia {

    // Clase final: su contrato está cerrado a la extensión.
    // (String, Integer y muchas clases de la biblioteca son así.)
    static final class CuitValidator {
        private CuitValidator() { } // solo uso estático

        static boolean esValido(String cuit) {
            return cuit != null && cuit.matches("\\d{2}-\\d{8}-\\d");
        }
    }

    static class Factura {
        double importe;

        Factura(double importe) {
            this.importe = importe;
        }

        // Método final: el cálculo del IVA es regla fija del negocio;
        // ninguna subclase puede alterarlo silenciosamente.
        final double iva() {
            return importe * 0.21;
        }

        double total() {
            return importe + iva();
        }
    }

    static class FacturaExportacion extends Factura {

        FacturaExportacion(double importe) {
            super(importe);
        }

        @Override
        double total() {
            // La exportación exenta: total = importe (sin IVA).
            // Podemos redefinir total(), pero NO iva(): es final.
            return importe;
        }
    }

    /*
     * CASO DONDE LA HERENCIA ES UN ERROR:
     *
     *     class ImpresoraReporte extends ArrayList<String> { ... }
     *
     * ¿Un "impresor de reportes" ES UNA lista? No: USA una lista para
     * acumular líneas. Heredar acopla la impresora a toda la API de
     * ArrayList y rompe si la lista cambia. La solución correcta es
     * composición — tener un campo de tipo lista (módulo 07):
     *
     *     class ImpresoraReporte {
     *         private List<String> lineas = new ArrayList<>();
     *     }
     *
     * Regla: herencia para ES-UN; composición para USA-UN / TIENE-UN.
     */

    public static void main(String[] args) {
        System.out.println("CUIT valido? " + CuitValidator.esValido("20-12345678-9"));

        Factura local = new Factura(1000);
        System.out.println("Factura local -> IVA: " + local.iva() + ", total: " + local.total());

        FacturaExportacion expo = new FacturaExportacion(1000);
        System.out.println("Factura expo  -> total sin IVA: " + expo.total());
    }
}

/*
 * ============================================================================
 * Solución 2 — Tu primer puerto y adaptador
 * ============================================================================
 * Puntos clave:
 * - El puerto ProveedorDeTiempo es una interfaz PROPIEDAD DEL DOMINIO: nace de
 *   la necesidad del caso de uso, no de lo que ofrece el sistema.
 * - RelojReal y RelojCongelado son adaptadores intercambiables: el dominio no
 *   sabe cuál está enchufado.
 * - VerificaVencimiento es 100% testeable: con RelojCongelado el resultado es
 *   determinista, sin dormir hilos ni tocar el reloj del sistema.
 *
 * CÓMO COMPILAR Y CORRER (desde soluciones/):
 *   javac *.java && java Ejercicio2PrimerPuertoYAdaptador
 */
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Ejercicio2PrimerPuertoYAdaptador {

    // ===== DOMINIO ===========================================================
    static class ObligacionDePago {
        final String concepto;
        final double monto;
        final LocalDate fechaVencimiento;

        ObligacionDePago(String concepto, double monto, LocalDate fechaVencimiento) {
            this.concepto = concepto;
            this.monto = monto;
            this.fechaVencimiento = fechaVencimiento;
        }
    }

    // ===== PUERTO (definido por el dominio) ==================================
    interface ProveedorDeTiempo {
        LocalDateTime ahora();
    }

    // ===== ADAPTADORES =======================================================
    static class RelojReal implements ProveedorDeTiempo {
        @Override
        public LocalDateTime ahora() {
            // Único lugar del archivo que consulta el reloj del sistema.
            return LocalDateTime.now();
        }
    }

    static class RelojCongelado implements ProveedorDeTiempo {
        private final LocalDateTime instanteFijo;

        RelojCongelado(LocalDateTime instanteFijo) {
            this.instanteFijo = instanteFijo;
        }

        @Override
        public LocalDateTime ahora() {
            return instanteFijo;
        }
    }

    // ===== CASO DE USO (solo conoce el puerto) ===============================
    static class VerificaVencimiento {
        private final ProveedorDeTiempo tiempo;

        VerificaVencimiento(ProveedorDeTiempo tiempo) {
            this.tiempo = tiempo;
        }

        boolean estaVencida(ObligacionDePago obligacion) {
            // Decisión documentada: el MISMO día del vencimiento todavía NO
            // está vencida (hay todo el día para pagar). Por eso comparamos
            // solo la FECHA (isBefore) y no el instante completo.
            return obligacion.fechaVencimiento.isBefore(tiempo.ahora().toLocalDate());
        }
    }

    // ===== COMPOSITION ROOT + demo ============================================
    public static void main(String[] args) {
        ProveedorDeTiempo reloj = new RelojCongelado(LocalDateTime.of(2026, 9, 1, 10, 0));
        // Para probar con la hora real, cambiá SOLO esta línea:
        // ProveedorDeTiempo reloj = new RelojReal();

        VerificaVencimiento caso = new VerificaVencimiento(reloj);

        ObligacionDePago alquiler = new ObligacionDePago("Alquiler agosto", 350000.0, LocalDate.of(2026, 8, 25));
        ObligacionDePago luz      = new ObligacionDePago("Luz julio",       28500.0, LocalDate.of(2026, 9, 15));
        ObligacionDePago impuesto = new ObligacionDePago("Impuestos",       91200.0, LocalDate.of(2026, 9, 1));

        System.out.println("Reloj usado: " + reloj.ahora());
        reportar(caso, alquiler);
        reportar(caso, luz);
        reportar(caso, impuesto); // vence el mismo día del reloj congelado: aún al día
    }

    private static void reportar(VerificaVencimiento caso, ObligacionDePago obligacion) {
        String estado = caso.estaVencida(obligacion) ? "[VENCIDA] " : "[AL DIA]  ";
        System.out.println(estado + obligacion.concepto + " (vencia " + obligacion.fechaVencimiento + ")");
    }
}

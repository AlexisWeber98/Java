/*
 * ============================================================================
 * Ejercicio 2 — Tu primer puerto y adaptador
 * ============================================================================
 *
 * ENUNCIADO
 * El dominio necesita la hora actual para verificar vencimientos de pagos,
 * pero está PROHIBIDO que ObligacionDePago o VerificaVencimiento llamen a
 * LocalDate.now() o System directamente: eso los ata al reloj del sistema y
 * los vuelve imposibles de probar de forma determinista.
 *
 * Tu trabajo:
 *   - El puerto ProveedorDeTiempo ya está declarado (no cambies su firma).
 *   - Completá el adaptador RelojReal (delega en el reloj del sistema).
 *   - Completá el adaptador RelojCongelado (devuelve siempre una fecha fija).
 *   - Implementá el caso de uso VerificaVencimiento: depende SOLO del puerto.
 *
 * REQUISITOS
 * - El puerto está definido EN ESTE ARCHIVO, en lenguaje del dominio: quien lo
 *   necesita lo define, no quien lo implementa (inversión de dependencias).
 * - Cambiar de RelojReal a RelojCongelado debe tocar UNA sola línea: la del main.
 * - Ni el caso de uso ni el dominio mencionan RelojReal ni LocalDateTime.now().
 *
 * PISTAS
 * - Un puerto es una interfaz propiedad del hexágono; un adaptador es un
 *   detalle enchufable desde afuera.
 * - RelojCongelado es tu mejor amigo para tests: mismo input, mismo output.
 * - Decidí y documentá con un comentario qué pasa el MISMO día del vencimiento.
 *
 * CÓMO COMPILAR Y CORRER (desde esta carpeta):
 *   javac *.java && java Ejercicio2PrimerPuertoYAdaptador
 */
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Ejercicio2PrimerPuertoYAdaptador {

    // =========================================================================
    // DOMINIO
    // =========================================================================
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

    // =========================================================================
    // PUERTO SALIDA (lo define el dominio): abstracción de "de dónde sale la hora"
    // =========================================================================
    interface ProveedorDeTiempo {
        LocalDateTime ahora();
    }

    // ADAPTADOR 1: el reloj de verdad.
    static class RelojReal implements ProveedorDeTiempo {
        @Override
        public LocalDateTime ahora() {
            // TODO: devolvé la hora real del sistema.
            throw new UnsupportedOperationException("TODO: implementá RelojReal.ahora()");
        }
    }

    // ADAPTADOR 2: reloj congelado, para pruebas deterministas.
    static class RelojCongelado implements ProveedorDeTiempo {
        private final LocalDateTime instanteFijo;

        RelojCongelado(LocalDateTime instanteFijo) {
            this.instanteFijo = instanteFijo;
        }

        @Override
        public LocalDateTime ahora() {
            // TODO: devolvé SIEMPRE el instante fijo con el que nació este reloj.
            throw new UnsupportedOperationException("TODO: implementá RelojCongelado.ahora()");
        }
    }

    // =========================================================================
    // CASO DE USO: depende SOLO del puerto. No sabe si arriba hay un reloj real
    // o uno congelado, y eso es exactamente el punto.
    // =========================================================================
    static class VerificaVencimiento {
        private final ProveedorDeTiempo tiempo;

        VerificaVencimiento(ProveedorDeTiempo tiempo) {
            this.tiempo = tiempo;
        }

        boolean estaVencida(ObligacionDePago obligacion) {
            // TODO: compará obligacion.fechaVencimiento contra tiempo.ahora().
            //  Decidí qué pasa el mismo día del vencimiento y dejalo escrito
            //  en un comentario.
            throw new UnsupportedOperationException("TODO: implementá estaVencida");
        }
    }

    // =========================================================================
    // COMPOSITION ROOT: el único lugar donde se eligen adaptadores concretos.
    // =========================================================================
    public static void main(String[] args) {
        ProveedorDeTiempo reloj = new RelojCongelado(LocalDateTime.of(2026, 9, 1, 10, 0));
        // TODO: cuando funcione, probá también: ProveedorDeTiempo reloj = new RelojReal();

        ObligacionDePago alquiler = new ObligacionDePago("Alquiler agosto", 350000.0, LocalDate.of(2026, 8, 25));
        VerificaVencimiento caso = new VerificaVencimiento(reloj);

        System.out.println("Reloj usado: " + reloj.ahora());
        System.out.println("¿" + alquiler.concepto + " vencida? " + caso.estaVencida(alquiler));
    }
}

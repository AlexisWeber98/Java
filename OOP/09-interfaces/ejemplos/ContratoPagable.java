// ============================================================
// Módulo 09 · Ejemplo 1: tu primer contrato de capacidad
// Ejecutar con: java ContratoPagable.java
// ============================================================

// El CONTRATO: "todo lo pagable sabe cobrarse". No dice CÓMO.
// Sus métodos son implícitamente public abstract; no tiene estado.
interface Pagable {
    boolean cobrar(double monto); // punto y coma: solo la promesa
}

// Firma el contrato y define SU manera de cumplir la promesa.
class TarjetaCredito implements Pagable {
    private final String titular;
    private final double limiteDisponible;

    TarjetaCredito(String titular, double limiteDisponible) {
        this.titular = titular;
        this.limiteDisponible = limiteDisponible;
    }

    @Override
    public boolean cobrar(double monto) { // sin el public NO compila
        if (monto <= limiteDisponible) {
            System.out.println("[TARJETA] Cobro aprobado a " + titular
                    + ": $" + monto);
            return true;
        }
        System.out.println("[TARJETA] Rechazado para " + titular
                + ": límite insuficiente");
        return false;
    }
}

// Otra implementación del MISMO contrato, con otra lógica interna.
class PagoEfectivo implements Pagable {
    private final String cajero;

    PagoEfectivo(String cajero) {
        this.cajero = cajero;
    }

    @Override
    public boolean cobrar(double monto) {
        System.out.println("[EFECTIVO] " + cajero + " recibe $" + monto
                + " en mano. Siempre acepta.");
        return true;
    }
}

public class ContratoPagable {

    // Programamos contra la INTERFAZ: un solo método sirve para todos los
    // medios de pago existentes y futuros. No sabe si es tarjeta o efectivo.
    static void procesarCobro(Pagable medioPago, double monto) {
        medioPago.cobrar(monto);
    }

    public static void main(String[] args) {
        Pagable tarjeta = new TarjetaCredito("Ana", 1000);
        Pagable efectivo = new PagoEfectivo("Cajero 3");

        // Mismo llamado, distinto comportamiento: polimorfismo por contrato.
        procesarCobro(tarjeta, 750);   // aprueba (hay límite)
        procesarCobro(tarjeta, 5000);  // rechaza (supera el límite)
        procesarCobro(efectivo, 750);  // acepta

        // Mañana sumás MercadoPago: implementás Pagable y procesarCobro()
        // sigue funcionando SIN tocar una línea de este método.
    }
}

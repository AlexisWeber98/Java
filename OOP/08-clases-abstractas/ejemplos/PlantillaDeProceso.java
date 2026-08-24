// Módulo 08 · Clases abstractas
// Demo 2: TEMPLATE METHOD liviano. procesar() clava el orden de los pasos;
// cada pago implementa los pasos a su manera. La receta no se negocia.

public class PlantillaDeProceso {

    public static void main(String[] args) {
        ProcesoDePago[] pagos = {
                new PagoConTarjeta(1500.0, "4509 9535 6623 0004"),
                new PagoPorTransferencia(8200.0, "CBU-0003100001000045678901")
        };

        for (ProcesoDePago pago : pagos) {
            pago.procesar();
        }
    }
}

// ═══════════════ LA PLANTILLA ═══════════════

abstract class ProcesoDePago {

    protected final double monto;    // todo pago tiene monto: estado compartido

    protected ProcesoDePago(double monto) {
        this.monto = monto;
    }

    // GANCHOS ABSTRACTOS: los pasos que varían según el medio de pago.
    // Sin cuerpo, sin llaves: contrato puro para las subclases.
    abstract boolean validar();
    abstract String cobrar();

    // PASO CON VALOR POR DEFECTO: no todos los pasos deben ser abstractos.
    // Si el comportamiento común sirve, se escribe una vez acá.
    void notificar() {
        System.out.println("   Comprobante enviado al cliente.");
    }

    // TEMPLATE METHOD: define el ESQUELETO del algoritmo y delega los
    // pasos variables en los ganchos. final = la receta no se puede pisar.
    final void procesar() {
        System.out.println("== Pago de $" + monto + " ==");

        if (!validar()) {                       // paso 1: validar (gancho)
            System.out.println("   Rechazado en validación.");
            return;
        }
        System.out.println("   " + cobrar());   // paso 2: cobrar (gancho)
        notificar();                            // paso 3: avisar (concreto)
    }
}

// ═══════════════ DOS RECETAS CONCRETAS ═══════════════

class PagoConTarjeta extends ProcesoDePago {

    private final String numeroEnmascarado;

    PagoConTarjeta(double monto, String numero) {
        super(monto);
        this.numeroEnmascarado = "****" + numero.substring(numero.length() - 4);
    }

    @Override
    boolean validar() {
        System.out.println("   Validando tarjeta " + numeroEnmascarado);
        // Nunca loguees el número completo: solo los últimos dígitos.
        return monto > 0 && numeroEnmascarado.endsWith("0004");
    }

    @Override
    String cobrar() {
        return "Cobrado con tarjeta " + numeroEnmascarado;
    }
}

class PagoPorTransferencia extends ProcesoDePago {

    private final String cbu;

    PagoPorTransferencia(double monto, String cbu) {
        super(monto);
        this.cbu = cbu;
    }

    @Override
    boolean validar() {
        System.out.println("   Verificando CBU de origen...");
        return cbu.startsWith("CBU-") && monto <= 50_000;
    }

    @Override
    String cobrar() {
        return "Débito aplicado sobre cuenta CBU ...";
    }
}

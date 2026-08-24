/*
 * Módulo 23 - Arquitectura Hexagonal (3 de 3): CASO DE USO y DEMO.
 *
 * GestorCuentas es el CASO DE USO (conceptualmente, un puerto de ENTRADA):
 * orquesta dominio y puertos de salida. Depende SOLO de interfaces, así que
 * se puede probar con adaptadores falsos sin base de datos ni consola real.
 *
 * DemoHexagonal es el COMPOSITION ROOT: el ÚNICO lugar del programa donde
 * se instancian adaptadores y se conectan a los puertos ("wiring").
 * Cambiar de tecnología = cambiar UNA línea acá, y nada más.
 *
 * Compilar y ejecutar TODO el ejemplo juntos:
 *   javac *.java && java DemoHexagonal
 */

// CASO DE USO: verbos del negocio, cero detalles técnicos.
class GestorCuentas {

    // Regla de aplicación: umbral que dispara la notificación.
    static final double UMBRAL_SALDO_BAJO = 50.0;

    private final RepositorioCuentas repositorio;
    private final NotificadorSaldoBajo notificador;

    public GestorCuentas(RepositorioCuentas repositorio, NotificadorSaldoBajo notificador) {
        this.repositorio = repositorio;
        this.notificador = notificador;
    }

    public void abrirCuenta(String titular) {
        if (repositorio.buscarPorTitular(titular) != null) {
            throw new IllegalStateException("Ya existe una cuenta para " + titular);
        }
        repositorio.guardar(new CuentaBancaria(titular));
        System.out.println("Cuenta abierta para " + titular);
    }

    public void depositar(String titular, double monto) {
        CuentaBancaria cuenta = buscarOCrearError(titular);
        cuenta.depositar(monto);
        repositorio.guardar(cuenta);
        System.out.printf("Depósito de $%.2f en cuenta de %s. Saldo: $%.2f%n",
                monto, titular, cuenta.getSaldo());
    }

    public void retirar(String titular, double monto) {
        CuentaBancaria cuenta = buscarOCrearError(titular);
        cuenta.retirar(monto); // las reglas viven en el DOMINIO, no aquí
        repositorio.guardar(cuenta);

        // Regla de negocio + puerto de salida: avisar si el saldo quedó bajo.
        if (cuenta.getSaldo() < UMBRAL_SALDO_BAJO) {
            notificador.avisar(titular, cuenta.getSaldo());
        }
        System.out.printf("Retiro de $%.2f de %s. Saldo: $%.2f%n",
                monto, titular, cuenta.getSaldo());
    }

    private CuentaBancaria buscarOCrearError(String titular) {
        CuentaBancaria cuenta = repositorio.buscarPorTitular(titular);
        if (cuenta == null) {
            throw new IllegalStateException("No existe cuenta para " + titular);
        }
        return cuenta;
    }
}

// MAIN = COMPOSITION ROOT: único punto donde se "enchufan" los adaptadores.
// Sin modificador public para permitir compilarla dentro de este archivo.
class DemoHexagonal {

    public static void main(String[] args) {
        // Wiring: hoy memoria+consola; mañana JPA+email cambiando SOLO estas líneas.
        RepositorioCuentas repositorio = new RepositorioCuentasEnMemoria();
        NotificadorSaldoBajo notificador = new NotificadorConsola();
        GestorCuentas gestor = new GestorCuentas(repositorio, notificador);

        System.out.println("--- Escenario bancario hexagonal ---");
        gestor.abrirCuenta("Ana");
        gestor.abrirCuenta("Bruno");

        gestor.depositar("Ana", 500.0);
        gestor.retirar("Ana", 80.0);      // saldo queda alto: sin notificación

        gestor.depositar("Bruno", 60.0);
        gestor.retirar("Bruno", 30.0);    // saldo 30 < 50: ¡dispara el puerto!

        try {
            gestor.retirar("Ana", 100000.0); // regla del dominio: no sobregiro
        } catch (IllegalStateException e) {
            System.out.println("[DOMINIO rechazó] " + e.getMessage());
        }
    }
}

/**
 * PasoPorValorDemo.java — Java SIEMPRE pasa por valor.
 * 1) Primitivo: se copia el valor -> cambiarlo adentro no afecta afuera.
 * 2) Objeto: se copia la REFERENCIA -> mutar el objeto SÍ se ve afuera.
 * 3) Reasignar el parámetro: solo cambia tu copia local, el llamador ni se entera.
 * Ejecutar: java ejemplos/PasoPorValorDemo.java
 */
public class PasoPorValorDemo {

    static class Cuenta {
        double saldo;
        Cuenta(double saldoInicial) { this.saldo = saldoInicial; }
    }

    // Intento 1: "modificar" un primitivo. El parámetro numero es una COPIA
    // de edad; sumarle 10 solo cambia esa copia, que muere al salir del método.
    public static void intentarSumarDiez(int numero) {
        numero = numero + 10;
        System.out.println("  adentro del método, numero = " + numero);
    }

    // Intento 2: MUTAR el objeto recibido. La referencia se copió, pero
    // apunta al MISMO objeto: tocar saldo acá es tocar el saldo real.
    public static void depositar(Cuenta cuentaParametro) {
        cuentaParametro.saldo = cuentaParametro.saldo + 500;
        System.out.println("  adentro del método, saldo = " + cuentaParametro.saldo);
    }

    // Intento 3: REASIGNAR el parámetro. Ahora cuentaParametro apunta a un
    // objeto NUEVO; el llamador sigue apuntando al original. No hay efecto.
    public static void reasignar(Cuenta cuentaParametro) {
        cuentaParametro = new Cuenta(9999.0);   // cambio local y efímero
        System.out.println("  adentro del método, saldo = " + cuentaParametro.saldo);
    }

    public static void main(String[] args) {
        // ── Caso 1: primitivo ──────────────────────────────────────────────
        int edad = 30;
        System.out.println("Caso 1 — primitivo");
        System.out.println("  antes:  edad = " + edad);
        intentarSumarDiez(edad);
        System.out.println("  después: edad = " + edad + "  (¡sigue en 30!)");

        // ── Caso 2: mutar un objeto ───────────────────────────────────────
        Cuenta miCuenta = new Cuenta(100.0);
        System.out.println("Caso 2 — mutar el objeto");
        System.out.println("  antes:  saldo = " + miCuenta.saldo);
        depositar(miCuenta);
        System.out.println("  después: saldo = " + miCuenta.saldo + "  (¡sí cambió a 600.0!)");

        // ── Caso 3: reasignar el parámetro ────────────────────────────────
        System.out.println("Caso 3 — reasignar el parámetro");
        System.out.println("  antes:  saldo = " + miCuenta.saldo);
        reasignar(miCuenta);
        System.out.println("  después: saldo = " + miCuenta.saldo + "  (sigue en 600.0)");

        // MORALEJA:
        // - Pasar por valor NO significa "el método nunca puede afectar tus datos".
        //   Significa que el PARÁMETRO recibe una copia: del valor (primitivos)
        //   o de la referencia (objetos).
        // - Si querés que el llamador vea cambios en un objeto: mutá sus campos.
        // - Si NO querés efectos sobre el llamador: no mutés, o devolvé un
        //   objeto nuevo con return en lugar de tocar el recibido.
    }
}

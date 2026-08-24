/*
 * ============================================================================
 *  Ejercicio 3 — Encadenar constructores con this(...) · SOLUCIÓN COMENTADA
 *  Módulo 04 · Constructores
 * ============================================================================
 *  Idea clave (por qué DRY aplica acá): la inicialización vive en UN solo
 *  lugar, el constructor canónico. Si mañana agregamos una regla — por
 *  ejemplo "el saldo no puede ser negativo" — la escribimos UNA vez y las
 *  tres formas de nacer la respetan automáticamente. Con constructores que
 *  repiten asignaciones, cada regla nueva hay que copiarla N veces y tarde o
 *  temprano alguna copia queda desactualizada.
 * ============================================================================
 */

public class Ejercicio3EncadenarConThis {

    public static void main(String[] args) {
        CuentaBancaria completa = new CuentaBancaria("Lucía Fernández", 150000.0, "USD");
        CuentaBancaria enPesos = new CuentaBancaria("Martín Gómez", 25000.0);
        CuentaBancaria reciénAbierta = new CuentaBancaria("Sofía Torres");

        completa.mostrarEstado();
        enPesos.mostrarEstado();
        reciénAbierta.mostrarEstado();
    }
}

class CuentaBancaria {

    private String titular;
    private double saldo;
    private String moneda;

    // El canónico: única fuente de verdad de la inicialización.
    CuentaBancaria(String titular, double saldo, String moneda) {
        this.titular = titular;
        this.saldo = saldo;
        this.moneda = moneda;
    }

    // Los chicos NO asignan nada: solo rellenan lo que falta y delegan.
    // Primera sentencia obligatoria: this(...).
    CuentaBancaria(String titular, double saldo) {
        this(titular, saldo, "ARS");
    }

    // Cadena corta: este delega en el de dos parámetros, que a su vez delega
    // en el canónico. La cadena siempre termina en quien asigna.
    CuentaBancaria(String titular) {
        this(titular, 0.0);
    }

    void mostrarEstado() {
        System.out.printf("Cuenta -> titular=%s, saldo=%.2f, moneda=%s%n",
                titular, saldo, moneda);
    }
}

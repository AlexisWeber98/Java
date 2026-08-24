package solucion;

/**
 * Implementación completa del cajero del Ejercicio 5, escrita DESPUÉS de la
 * suite de tests (ciclo rojo → verde).
 */
public class Cajero {

    private int saldo;

    public Cajero(int saldoInicial) {
        this.saldo = saldoInicial;
    }

    public int consultarSaldo() {
        return saldo;
    }

    public void depositar(int monto) {
        saldo += monto;
    }

    public void retirar(int monto) {
        if (monto > saldo) {
            throw new SaldoInsuficienteException(
                    "Saldo insuficiente: el saldo disponible es " + saldo + " y pediste " + monto);
        }
        saldo -= monto;
    }
}

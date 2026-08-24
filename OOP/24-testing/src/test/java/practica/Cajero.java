package practica;

/**
 * Cajero automático mínimo. Tu trabajo en DesafioTDDCajero es escribir los
 * tests PRIMERO y recién después completar esta implementación.
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
        // TODO: sumá el monto al saldo.
    }

    public void retirar(int monto) {
        // TODO: si el monto supera el saldo disponible, lanzá
        //       SaldoInsuficienteException con un mensaje claro;
        //       si hay fondos, descontalo del saldo.
    }
}

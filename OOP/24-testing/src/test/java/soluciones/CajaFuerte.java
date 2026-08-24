package soluciones;

/**
 * SOLUCIÓN Ejercicio 5: implementación mínima que nació de los tests.
 * El saldo inicial se acepta tal cual; los montos operados nunca son negativos.
 */
class CajaFuerte {

    private double saldo;

    CajaFuerte(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    double getSaldo() {
        return saldo;
    }

    void depositar(double monto) {
        if (monto < 0) {
            throw new IllegalArgumentException("El monto a depositar no puede ser negativo");
        }
        saldo += monto;
    }

    void extraer(double monto) {
        if (monto < 0) {
            throw new IllegalArgumentException("El monto a extraer no puede ser negativo");
        }
        if (monto > saldo) {
            throw new SaldoInsuficienteException(
                "Saldo insuficiente: hay " + saldo + " y se piden " + monto);
        }
        saldo -= monto;
    }
}

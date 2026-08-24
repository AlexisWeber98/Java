/*
 * ============================================================================
 *  Solución 4 — Una clase con invariantes
 * ============================================================================
 *
 *  IDEA CLAVE
 *  La invariante no se "verifica de vez en cuando": se CONSTRUYE de forma
 *  que sea imposible romperla. Campos private + dos únicas puertas
 *  (depositar/extraer) que respetan las reglas = saldo negativo imposible.
 *
 *  Detalles de la solución:
 *   - El constructor valida: un objeto nace válido o no nace (fail-fast con
 *     IllegalArgumentException; las excepciones las profundizamos más adelante).
 *   - Operación rechazada => return temprano SIN tocar nada. Todo o nada.
 *   - El contador sube SOLO cuando la operación fue aceptada (I2).
 *   - La clase no imprime: devuelve true/false y el main decide qué mostrar
 *     (a diferencia del ejercicio 2, donde elegimos avisar por consola;
 *     acá el booleano es la respuesta y alcanza).
 * ============================================================================
 */
public class Solucion4ClaseConInvariantes {

    static class CuentaBancaria {
        private final String titular;
        private double saldo;
        private int movimientosRealizados;

        public CuentaBancaria(String titular, double saldoInicial) {
            if (saldoInicial < 0) {
                throw new IllegalArgumentException(
                        "El saldo inicial no puede ser negativo: " + saldoInicial);
            }
            this.titular = titular;
            this.saldo = saldoInicial;
        }

        /** Acepta solo montos positivos. Rechazada => estado intacto. */
        public boolean depositar(double monto) {
            if (monto <= 0) {
                return false; // rechazo limpio: no toca saldo ni contador
            }
            saldo += monto;
            movimientosRealizados++;
            return true;
        }

        /**
         * Acepta solo montos positivos que alcancen. La condición
         * monto > saldo es la que blinda I1: si llegara a entrar,
         * el saldo quedaría negativo... y por eso jamás entra.
         */
        public boolean extraer(double monto) {
            if (monto <= 0 || monto > saldo) {
                return false; // cubre "monto absurdo" y "fondos insuficientes"
            }
            saldo -= monto;
            movimientosRealizados++;
            return true;
        }

        public double getSaldo() {
            return saldo;
        }

        public int getCantidadMovimientos() {
            return movimientosRealizados;
        }

        public String getTitular() {
            return titular;
        }
    }

    private static void verificarInvariante(CuentaBancaria cuenta) {
        String estado = "saldo=" + cuenta.getSaldo()
                + ", movimientos=" + cuenta.getCantidadMovimientos();
        if (cuenta.getSaldo() >= 0) {
            System.out.println("   [OK] invariante vigente (" + estado + ")");
        } else {
            System.out.println("   [INVARIANTE ROTA] (" + estado + ")");
        }
    }

    public static void main(String[] args) {
        CuentaBancaria cuenta = new CuentaBancaria("Ana Gutiérrez", 1000.0);
        System.out.println("Se abre la cuenta de " + cuenta.getTitular());
        verificarInvariante(cuenta);

        System.out.println("depositar(500) -> aceptada? " + cuenta.depositar(500));
        verificarInvariante(cuenta);

        System.out.println("extraer(2000)  -> aceptada? " + cuenta.extraer(2000));
        verificarInvariante(cuenta);

        System.out.println("extraer(300)   -> aceptada? " + cuenta.extraer(300));
        verificarInvariante(cuenta);

        System.out.println("depositar(-50) -> aceptada? " + cuenta.depositar(-50));
        verificarInvariante(cuenta);

        System.out.println();
        System.out.println("Resumen: saldo final=" + cuenta.getSaldo()
                + ", movimientos=" + cuenta.getCantidadMovimientos());
        System.out.println("Las dos rechazadas (2000 y -50) no movieron NI el saldo"
                + " NI el contador.");
    }
}

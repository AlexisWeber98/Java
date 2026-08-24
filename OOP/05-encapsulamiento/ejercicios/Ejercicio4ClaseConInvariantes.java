/*
 * ============================================================================
 *  Ejercicio 4 — Una clase con invariantes
 * ============================================================================
 *
 *  ENUNCIADO
 *  Una invariante es una regla que se cumple SIEMPRE, antes y después de
 *  cada operación. CuentaBancaria tiene dos:
 *
 *    I1. El saldo nunca es negativo.
 *    I2. Los movimientos se cuentan solo si la operación fue aceptada.
 *
 *  Las operaciones informan su resultado con un booleano (true = aceptada)
 *  y NO imprimen: quien llama decide qué mostrar con el resultado.
 *
 *  Reglas de las operaciones:
 *   - depositar(monto): acepta solo montos mayores a cero; suma al saldo y
 *     cuenta el movimiento.
 *   - extraer(monto): acepta solo si el monto es mayor a cero Y alcanza el
 *     saldo; resta y cuenta el movimiento.
 *   - Operación rechazada: no toca saldo NI contador. Estado intacto.
 *   - El constructor rechaza un saldoInicial negativo: la cuenta nace válida.
 *
 *  REQUISITOS
 *   1. Implementá el constructor con su validación (TODO 1).
 *   2. Implementá depositar y extraer respetando I1 e I2 (TODOs 2 y 3).
 *   3. Corré el main ANTES de implementar (verás que nada se mueve) y
 *      DESPUÉS: en ambos casos, la invariante tiene que dar [OK] en cada
 *      paso, y las dos operaciones rechazadas del guion no deben mover
 *      ni el saldo ni el contador.
 *
 *  PISTAS
 *   - Si los campos son private y los únicos puntos de entrada son
 *     depositar/extraer, la invariante se sostiene sola: nadie desde
 *     afuera puede saltarse las reglas. ESO es encapsular.
 *   - Pensá "todo o nada": una operación rechazada deja el estado exactamente
 *     como estaba. Nada de restar primero y avisar después.
 *   - El helper verificarInvariante ya mira getSaldo() tras cada paso:
 *     usalo como red de seguridad mientras programás.
 * ============================================================================
 */
public class Ejercicio4ClaseConInvariantes {

    static class CuentaBancaria {
        private final String titular;
        private double saldo;
        private int movimientosRealizados;

        public CuentaBancaria(String titular, double saldoInicial) {
            // TODO 1: ¿y si te pasan saldoInicial negativo? La cuenta tiene
            //         que NACER válida: validá acá antes de asignar.
            this.titular = titular;
            this.saldo = saldoInicial;
        }

        /** Suma el monto al saldo. Devuelve true si fue aceptada. */
        public boolean depositar(double monto) {
            // TODO 2: regla: solo montos mayores a cero.
            return false;
        }

        /** Resta el monto del saldo. Devuelve true si fue aceptada. */
        public boolean extraer(double monto) {
            // TODO 3: regla: monto mayor a cero Y saldo suficiente.
            //         Rechazada => no toca saldo NI movimientos.
            return false;
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

    // Ayudante del main: después de CADA operación miramos la invariante.
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
    }
}

/*
 * CampoPublicoPeligroso.java
 *
 * Demostración de por qué los campos públicos son una trampa:
 * un saldo corrupto a -9999 sin que nadie "haga nada malo" a simple vista.
 */
public class CampoPublicoPeligroso {

    static class CuentaBancaria {
        // Campos PÚBLICOS: cualquier código puede escribirlos sin control.
        public String titular;
        public double saldo;

        @Override
        public String toString() {
            return "CuentaBancaria{titular='" + titular + "', saldo=" + saldo + "}";
        }
    }

    public static void main(String[] args) {
        CuentaBancaria cuenta = new CuentaBancaria();
        cuenta.titular = "Lucía Fernández";
        cuenta.saldo = 5000;
        System.out.println("Estado inicial:      " + cuenta);

        // --- El daño, paso a paso -------------------------------------------

        // 1) Un descuento "inofensivo" escrito con signo invertido.
        double descuento = 150;
        cuenta.saldo = -descuento;   // quería restar: saldo -= descuento
        System.out.println("Tras el descuento:   " + cuenta);

        // 2) Alguien más, en otro módulo, decide "arreglarlo" a mano...
        cuenta.saldo = cuenta.saldo - 9999;  // y lo empeora mucho más.

        // 3) Y por si faltaba algo: un valor basura directo.
        if (cuenta.saldo < -5000) {
            cuenta.saldo = -9999;    // ajuste manual sin ninguna validación.
        }

        // --- Informe de daños ------------------------------------------------

        System.out.println("\n========== INFORME DE DAÑOS ==========");
        System.out.println("Estado final:        " + cuenta);
        System.out.println("Saldo negativo:      " + (cuenta.saldo < 0));
        System.out.println("Titular vacío/null posible: sí, nadie lo impide");
        System.out.println("""
                \
                Diagnóstico:
                 - Tres líneas distintas corrompieron el estado.
                 - Ninguna lanzó error en el momento del daño.
                 - El bug aparecerá MUY lejos de su causa real.
                Culpa del diseño: el campo público permite esto.
                """);
    }
}

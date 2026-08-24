/**
 * StaticVsInstancia.java — Utilidades de clase (static) vs comportamiento
 * de instancia. Cuándo conviene cada uno y cómo se lee cada llamada.
 * Ejecutar: java ejemplos/StaticVsInstancia.java
 */
public class StaticVsInstancia {

    // ── STATIC: utilidad pura, sin estado ───────────────────────────────────

    // No toca ningún atributo: mismas entradas -> misma salida, siempre.
    // Se llama con el nombre de la CLASE, como Math.max(...) o Math.round(...).
    public static double celsiusAFahrenheit(double celsius) {
        return celsius * 9 / 5 + 32;
    }

    // Otra utilidad: tampoco depende de objetos.
    public static boolean esMayorDeEdad(int edad) {
        return edad >= 18;
    }

    // ── INSTANCIA: comportamiento que usa el estado del objeto ─────────────

    private String titular;
    private double saldo;

    public StaticVsInstancia(String titular, double saldoInicial) {
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    // Este método NECESITA los atributos: modifica el saldo del objeto
    // sobre el que se llama. No tiene sentido "sin objeto": ¿de quién sería?
    public void depositar(double monto) {
        if (monto <= 0) {
            System.out.println("Monto inválido para depositar");
            return;
        }
        saldo = saldo + monto;
        System.out.println(titular + " depositó " + monto + " — saldo: " + saldo);
    }

    // También lee estado, sin modificarlo.
    public void mostrarSaldo() {
        System.out.println("Cuenta de " + titular + ": " + saldo);
    }

    public static void main(String[] args) {
        // ── Uso de static: la clase es la puerta de entrada ────────────────
        // Se lee "a la clase Conversor le pido una conversión". No hay objeto.
        double f = celsiusAFahrenheit(25.0);
        System.out.println("25 °C son " + f + " °F");
        System.out.println("¿17 es mayor de edad? " + esMayorDeEdad(17));

        // Igual que Math.round(3.7): utilidad de la clase Math, no de un número.
        System.out.println("Math.round(3.7) = " + Math.round(3.7));

        // ── Uso de instancia: cada objeto lleva su propio estado ───────────
        StaticVsInstancia cuentaDeAna = new StaticVsInstancia("Ana", 1000.0);
        StaticVsInstancia cuentaDeLuis = new StaticVsInstancia("Luis", 50.0);

        // El método actúa SOBRE el objeto de la izquierda del punto.
        cuentaDeAna.depositar(500.0);   // toca el saldo de Ana...
        cuentaDeLuis.depositar(50.0);   // ...y este toca el de Luis.
        // Cada objeto recuerda lo suyo: eso es comportamiento de instancia.

        cuentaDeAna.mostrarSaldo();
        cuentaDeLuis.mostrarSaldo();

        // ── La prueba de fuego ─────────────────────────────────────────────
        // depositar necesita un objeto; esto NO compilaría:
        // StaticVsInstancia.depositar(100);   // ¿depositarle a QUIÉN?
        //
        // Y celsiusAFahrenheit no debería ser de instancia:
        // no hay nada en el objeto que el cálculo necesite.
    }
}

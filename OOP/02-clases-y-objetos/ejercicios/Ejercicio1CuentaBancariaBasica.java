/*
 * ============================================================================
 *  Ejercicio 1 — Cuenta bancaria básica
 *  Módulo 02 · Clases y objetos
 * ============================================================================
 *
 *  ENUNCIADO
 *  Llegó la hora de escribir tu PRIMERA clase desde cero. Vas a modelar una
 *  cuenta bancaria: le vas a dar estado (lo que sabe) y comportamiento (lo
 *  que sabe hacer), y después la vas a usar desde el main.
 *
 *  REQUISITOS
 *  1. Crear la clase CuentaBancaria con dos atributos: titular (String) y
 *     saldo (double).
 *  2. Método depositar(double monto): suma monto al saldo. Sin validaciones
 *     por ahora (eso viene en el módulo de encapsulamiento).
 *  3. Método consultarSaldo(): devuelve el saldo actual (double).
 *  4. En el main: creá una cuenta para "Ana", depositá 1500, después 500 más,
 *     y mostrá el saldo final por consola.
 *
 *  PISTAS
 *  - Anatomía mínima de una clase: class Nombre { atributos; métodos }
 *  - Un método que responde algo declara su tipo de retorno:
 *        double consultarSaldo() { ... }
 *  - Dentro de los métodos, el objeto puede leer y modificar SUS PROPIOS
 *    campos: saldo = saldo + monto;
 *  - Desde el main primero creás el objeto con new y DESPUÉS le asignás
 *    valores: cuenta.titular = "Ana";
 */
public class Ejercicio1CuentaBancariaBasica {

    public static void main(String[] args) {
        // TODO 1: crear un objeto CuentaBancaria para "Ana"

        // TODO 2: depositar 1500 usando cuenta.depositar(...)

        // TODO 3: depositar 500 más

        // TODO 4: imprimir "Saldo final de <titular>: <saldo>"
    }

    // TODO 5: definí acá tu clase CuentaBancaria.
    // Usamos una clase anidada static solo para que todo viva en un archivo
    // y puedas correr: java Ejercicio1CuentaBancariaBasica.java
    // Todavía SIN private ni otros modificadores en los atributos.

}

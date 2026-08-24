/*
 * ============================================================================
 *  Ejercicio 1 — Cuenta bancaria básica · SOLUCIÓN
 *  Módulo 02 · Clases y objetos
 * ============================================================================
 *
 *  QUÉ MIRAR DE ESTA SOLUCIÓN
 *  - La clase es el MOLDE: define qué datos tiene (atributos) y qué hace
 *    (métodos). El objeto concreto nace con new.
 *  - depositar() modifica un campo del PROPIO objeto: cada cuenta lleva su
 *    propio saldo, sin necesidad de pasar nada extra por parámetro.
 *  - Todavía no usamos private: el main toca los campos directamente
 *    (cuentaDeAna.titular = "Ana"). Funciona, pero es frágil: cualquiera
 *    puede poner saldo = -9999. El módulo 03/05 lo arregla.
 */
// Sin public y con nombre Solucion*: así ejercicios y soluciones compilan juntos.
class Solucion1CuentaBancariaBasica {

    public static void main(String[] args) {
        // new reserva memoria para un objeto nuevo del molde CuentaBancaria
        CuentaBancaria cuentaDeAna = new CuentaBancaria();
        cuentaDeAna.titular = "Ana";   // estado inicial

        cuentaDeAna.depositar(1500);   // mensaje 1 al objeto
        cuentaDeAna.depositar(500);    // mensaje 2 al MISMO objeto

        System.out.println("Titular: " + cuentaDeAna.titular);
        System.out.println("Saldo final de " + cuentaDeAna.titular
                + ": " + cuentaDeAna.consultarSaldo());
        // Salida: Saldo final de Ana: 2000.0
    }

    // Clase anidada static: así todo vive en un archivo y corre con
    // java Ejercicio1CuentaBancariaBasica.java
    static class CuentaBancaria {
        String titular;   // estado: lo que el objeto SABE
        double saldo;     // arranca en 0.0 automáticamente

        // comportamiento: lo que el objeto SABE HACER
        void depositar(double monto) {
            saldo = saldo + monto;   // muta SU estado interno
        }

        double consultarSaldo() {
            return saldo;   // lee SU campo, no necesita parámetros
        }
    }
}

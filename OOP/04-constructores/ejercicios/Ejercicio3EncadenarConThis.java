/*
 * ============================================================================
 *  Ejercicio 3 — Encadenar constructores con this(...)
 *  Módulo 04 · Constructores
 * ============================================================================
 *
 *  ENUNCIADO
 *  Si hiciste el Ejercicio 2, te quedó código repetido en cada constructor.
 *  El remedio es DRY (Don't Repeat Yourself): UN constructor principal
 *  ("canónico") que inicializa todo, y los demás DELEGAN en él con this(...).
 *
 *  Completá la clase CuentaBancaria:
 *    - CuentaBancaria(String titular, double saldo, String moneda)
 *      → el canónico: asigna TODOS los atributos.
 *    - CuentaBancaria(String titular, double saldo)
 *      → delega en el canónico usando "ARS" como moneda por defecto.
 *    - CuentaBancaria(String titular)
 *      → delega también, con saldo 0.0 y moneda "ARS".
 *
 *  En el main, creá las tres versiones de cuenta y mostrá su estado.
 *
 *  REQUISITOS
 *    1. Solo el canónico asigna atributos; los otros dos SOLO delegan.
 *    2. Cada this(...) es la primera sentencia del constructor.
 *    3. mostrarEstado() imprime titular, saldo y moneda.
 *    4. En un comentario explicá POR QUÉ DRY aplica acá.
 *
 *  PISTAS
 *    - this(...) debe ser la PRIMERA sentencia y solo puede aparecer UNA vez
 *      por constructor. No podés mezclarlo con otras sentencias antes.
 *    - La cadena siempre termina en el canónico: es la única fuente de verdad
 *      de la inicialización.
 *
 *  Ejecutalo:  java Ejercicio3EncadenarConThis.java
 * ============================================================================
 */

public class Ejercicio3EncadenarConThis {

    public static void main(String[] args) {
        // TODO: cuenta completa: titular, saldo y moneda explícitos

        // TODO: cuenta con titular y saldo (moneda por defecto)

        // TODO: cuenta solo con titular

        // TODO: mostrá el estado de las tres
    }
}

class CuentaBancaria {

    private String titular;
    private double saldo;
    private String moneda;

    // TODO: constructor canónico (titular, saldo, moneda): único que asigna

    // TODO: constructor (titular, saldo): delegá con this(...)

    // TODO: constructor (titular): delegá también

    // TODO: método mostrarEstado() que imprima titular, saldo y moneda
}

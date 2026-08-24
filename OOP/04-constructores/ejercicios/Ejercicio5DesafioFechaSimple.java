/*
 * ============================================================================
 *  Ejercicio 5 — Desafío: FechaSimple validada
 *  Módulo 04 · Constructores
 * ============================================================================
 *
 *  ENUNCIADO
 *  Ahora juntás todo el módulo: canónico con validación, constructor de
 *  conveniencia que delega con this(...), y un objeto que nace válido o no
 *  nace.
 *
 *  Completá la clase FechaSimple:
 *    - FechaSimple(int dia, int mes, int anio): el canónico. Valida ANTES de
 *      asignar y lanza IllegalArgumentException si algo no cierra:
 *          · mes en 1..12
 *          · anio en 1900..2100
 *          · dia entre 1 y la cantidad de días del mes (chequeo grueso: abril
 *            tiene 30, febrero admite hasta 29; bisiesto queda como mejora)
 *    - FechaSimple(): constructor de conveniencia que delega en el canónico
 *      con la fecha de HOY. Pista: LocalDate.now() de java.time.
 *    - mostrarEstado(): imprime día, mes y año.
 *
 *  En el main:
 *    - creá una fecha válida y mostrála,
 *    - usá el constructor de conveniencia (hoy) y mostrálo,
 *    - intentá fechas inválidas con try/catch y mostrá los mensajes.
 *
 *  REQUISITOS
 *    1. La validación vive SOLO en el canónico; el de conveniencia delega.
 *    2. Mensajes de error claros, con el dato rechazado incluido.
 *    3. Ningún objeto FechaSimple puede existir en estado inválido.
 *
 *  PISTAS
 *    - Un arreglo estático DIAS_POR_MES = {31,29,31,30,...} te resuelve el
 *      chequeo grueso por mes: DIAS_POR_MES[mes - 1].
 *    - Validá primero mes y año, después el día (así nunca indexás el
 *      arreglo con un mes inválido).
 *    - En los argumentos de this(...) pueden ir expresiones y llamadas a
 *      métodos: se evalúan antes de entrar al canónico.
 *
 *  Ejecutalo:  java Ejercicio5DesafioFechaSimple.java
 * ============================================================================
 */

import java.time.LocalDate;

public class Ejercicio5DesafioFechaSimple {

    public static void main(String[] args) {
        // TODO: creá una fecha válida (p. ej., 25-5-2010) y mostrála

        // TODO: creá una fecha con el constructor de conveniencia (hoy)

        // TODO: intentá crear 31-4-2026 con try/catch y mostrá el mensaje

        // TODO: intentá una fecha doblemente inválida (p. ej., 32-13-2200)
    }
}

class FechaSimple {

    private int dia;
    private int mes;
    private int anio;

    // TODO: constructor canónico: validar(dia, mes, anio) y recién ahí asignar

    // TODO: constructor de conveniencia sin argumentos → hoy, delegando con this(...)

    // TODO: validar(...) estático privado con las tres reglas del enunciado

    // TODO: método mostrarEstado()
}

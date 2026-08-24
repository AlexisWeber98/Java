/*
 * ============================================================================
 *  Ejercicio 5 — Desafío: FechaSimple validada · SOLUCIÓN COMENTADA
 *  Módulo 04 · Constructores
 * ============================================================================
 *  Idea clave: la validación vive en el canónico y el constructor de
 *  conveniencia hereda esa garantía gratis al delegar con this(...). Es el
 *  cierre del módulo: DRY + fail fast + sobrecarga trabajando juntos.
 *
 *  Tradeoff documentado: el chequeo del día es GRUESO. El 29-2-2027 (año no
 *  bisiesto) pasa, porque febrero admite hasta 29 siempre. Refinarlo con la
 *  regla de bisiestos queda propuesto como mejora; para este ejercicio nos
 *  interesa la estructura, no el calendario exacto.
 * ============================================================================
 */

import java.time.LocalDate;

public class Ejercicio5DesafioFechaSimple {

    public static void main(String[] args) {
        FechaSimple revolucion = new FechaSimple(25, 5, 2010);
        FechaSimple hoy = new FechaSimple();

        revolucion.mostrarEstado();
        hoy.mostrarEstado();

        // Abril tiene 30 días: el chequeo día-vs-mes lo rechaza.
        try {
            FechaSimple imposible = new FechaSimple(31, 4, 2026);
            imposible.mostrarEstado(); // nunca llega
        } catch (IllegalArgumentException e) {
            System.out.println("Rechazada: " + e.getMessage());
        }

        // Fallan varias reglas a la vez: gana la primera guardá (el mes).
        try {
            FechaSimple caos = new FechaSimple(32, 13, 2200);
            caos.mostrarEstado(); // nunca llega
        } catch (IllegalArgumentException e) {
            System.out.println("Rechazada: " + e.getMessage());
        }
    }
}

class FechaSimple {

    // Chequeo grueso por mes; febrero admite hasta 29 sin lógica de bisiestos.
    private static final int[] DIAS_POR_MES = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private final int dia;
    private final int mes;
    private final int anio;

    FechaSimple(int dia, int mes, int anio) {
        validar(dia, mes, anio);
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    // Conveniencia: hoy. Las expresiones en los argumentos de this(...) se
    // evalúan antes de entrar al canónico, así que la validación también
    // aplica a la fecha de hoy.
    FechaSimple() {
        this(LocalDate.now().getDayOfMonth(),
             LocalDate.now().getMonthValue(),
             LocalDate.now().getYear());
    }

    private static void validar(int dia, int mes, int anio) {
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("Mes fuera de rango (1-12): " + mes);
        }
        if (anio < 1900 || anio > 2100) {
            throw new IllegalArgumentException("Año fuera de rango (1900-2100): " + anio);
        }
        if (dia < 1 || dia > DIAS_POR_MES[mes - 1]) {
            throw new IllegalArgumentException(
                    "Día " + dia + " inválido para el mes " + mes);
        }
    }

    void mostrarEstado() {
        System.out.printf("FechaSimple -> %d/%d/%d%n", dia, mes, anio);
    }
}

/*
 * ============================================================================
 * Ejercicio 4 — Ordenar con criterio propio
 * ============================================================================
 *
 * ENUNCIADO:
 *   Tenés una lista de empleados (nombre, sueldo). Ordenala:
 *     1. Principalmente por sueldo, de MAYOR a MENOR.
 *     2. Ante empate de sueldo, por nombre alfabéticamente ascendente.
 *   Mostrá la lista ORIGINAL y después la lista ORDENADA.
 *
 * REQUISITOS:
 *   - Construir el criterio con Comparator.comparingDouble(...) encadenando
 *     .reversed() y .thenComparing(...) (sin escribir comparaciones manuales).
 *   - Incluir al menos dos empleados con EL MISMO sueldo para verificar el
 *     desempate por nombre.
 *   - Ordenar con lista.sort(comparador).
 *
 * PISTAS:
 *   - Comparator.comparingDouble(Empleado::getSueldo) ordena ascendente;
 *     .reversed() invierte el orden.
 *   - .thenComparing(Empleado::getNombre) solo actúa cuando el criterio
 *     anterior da empate.
 *   - sort() MUTA la lista que lo invoca: guardá una copia con
 *     new ArrayList<>(empleados) si querés conservar el orden de entrada.
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ejercicio4OrdenarConCriterio {

    static class Empleado {
        private final String nombre;
        private final double sueldo;

        Empleado(String nombre, double sueldo) {
            this.nombre = nombre;
            this.sueldo = sueldo;
        }

        String getNombre() {
            return nombre;
        }

        double getSueldo() {
            return sueldo;
        }

        @Override
        public String toString() {
            return nombre + " ($" + sueldo + ")";
        }
    }

    public static void main(String[] args) {
        List<Empleado> empleados = new ArrayList<>();
        empleados.add(new Empleado("Ana", 950000.0));
        // TODO: sumá al menos tres empleados más; dos de ellos CON EL MISMO sueldo

        // TODO 1: mostrá la lista en su orden original

        // TODO 2: creá el comparador con crearComparadorPorSueldoDescYNombre()

        // TODO 3: ordená con empleados.sort(...) y mostrá la lista ordenada
    }

    /**
     * Sueldo de mayor a menor; a igual sueldo, nombre alfabético ascendente.
     */
    private static Comparator<Empleado> crearComparadorPorSueldoDescYNombre() {
        // TODO: combiná comparingDouble(getSueldo).reversed() con thenComparing(getNombre)
        return null;
    }
}

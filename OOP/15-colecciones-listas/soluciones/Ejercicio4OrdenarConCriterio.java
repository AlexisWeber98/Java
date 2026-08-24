/*
 * ============================================================================
 * Ejercicio 4 — Ordenar con criterio propio (SOLUCIÓN)
 * ============================================================================
 * Comparator encadenado: sueldo descendente con desempate por nombre
 * ascendente. Se muestra el orden original y el orden final.
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
        empleados.add(new Empleado("Luis", 1200000.0));
        empleados.add(new Empleado("Carla", 950000.0)); // empata con Ana
        empleados.add(new Empleado("Bruno", 780000.0));

        System.out.println("Original: " + empleados);

        // Copia previa: sort() muta la lista que lo invoca. Si necesitás
        // preservar el orden de entrada, trabajá sobre una copia.
        List<Empleado> ordenados = new ArrayList<>(empleados);
        ordenados.sort(crearComparadorPorSueldoDescYNombre());

        System.out.println("Ordenado: " + ordenados);
    }

    /**
     * Sueldo de mayor a menor; a igual sueldo, nombre alfabético ascendente.
     */
    private static Comparator<Empleado> crearComparadorPorSueldoDescYNombre() {
        return Comparator.comparingDouble(Empleado::getSueldo)
                .reversed()
                .thenComparing(Empleado::getNombre);
    }
}

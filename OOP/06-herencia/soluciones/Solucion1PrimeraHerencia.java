/*
 * =============================================================================
 * Ejercicio 1 (SOLUCIÓN) — Primera herencia: extends y reutilización
 * Módulo 06 · Herencia
 * =============================================================================
 *
 * ENUNCIADO
 * Tu primer vínculo padre-hijo en Java. La empresa tiene Empleados; entre
 * ellos hay Gerentes, que hacen todo lo que hace un empleado y además lideran.
 *
 * REQUISITOS
 *   1. Empleado tiene: nombre (String), sueldoBase (double) y trabajar(),
 *      que imprime que la persona está trabajando.
 *   2. Gerente hereda de Empleado con EXTENDS.
 *   3. Gerente agrega: equipoACargo (int) y dirigirReunion(), que imprime
 *      cuántas personas tiene a cargo.
 *   4. El constructor de Gerente recibe nombre, sueldoBase y equipoACargo,
 *      y delega lo del padre con super(...).
 *   5. En main: creá un Empleado y un Gerente, y comprobá que el gerente
 *      también sabe trabajar() aunque nunca lo escribiste en su clase.
 *
 * PISTAS
 *   - class Gerente EXTENDS Empleado { ... }
 *   - Dentro del constructor del hijo, super(...) va SIEMPRE primero.
 *   - Un Gerente ES-UN Empleado: por eso hereda sus atributos y métodos.
 *
 * IDEAS CLAVE DE LA SOLUCIÓN
 *   - extends establece la relación ES-UN y trae gratis la API del padre.
 *   - super(...) delega la inicialización común; no repetís código del padre.
 *   - dirigirReunion() es comportamiento NUEVO que solo existe en Gerente.
 * =============================================================================
 */
public class Solucion1PrimeraHerencia {

    static class Empleado {
        String nombre;
        double sueldoBase;

        Empleado(String nombre, double sueldoBase) {
            this.nombre = nombre;
            this.sueldoBase = sueldoBase;
        }

        void trabajar() {
            System.out.println(nombre + " está trabajando.");
        }
    }

    // CLAVE: 'extends' crea el vínculo de herencia. Gerente ES-UN Empleado,
    // así que recibe sin escribir una línea: nombre, sueldoBase y trabajar().
    static class Gerente extends Empleado {
        int equipoACargo;

        // CLAVE: el hijo no reinicializa lo del padre; se lo delega a super(...).
        // Esa llamada debe ser SIEMPRE la primera sentencia del constructor.
        Gerente(String nombre, double sueldoBase, int equipoACargo) {
            super(nombre, sueldoBase);
            this.equipoACargo = equipoACargo;
        }

        // Comportamiento NUEVO: solo existe en Gerente, no en Empleado.
        void dirigirReunion() {
            System.out.println(nombre + " dirige una reunión con su equipo de "
                    + equipoACargo + " personas.");
        }
    }

    public static void main(String[] args) {
        Empleado empleado = new Empleado("Lucía", 850000);
        Gerente gerente = new Gerente("Ana", 1200000, 5);

        empleado.trabajar();
        gerente.dirigirReunion();

        // CLAVE: trabajar() no está escrito en Gerente... y funciona igual.
        // Vino por herencia desde Empleado.
        gerente.trabajar();
    }
}

/*
 * ===========================================================================
 *  Ejercicio 3 — Estado compartido y constructor de la base · SOLUCIÓN
 *  Módulo 08 · Clases abstractas
 * ===========================================================================
 *
 *  Cambios clave: Vendedor encadena su constructor con super(...) y la base
 *  implementa reciboDeSueldo() una sola vez, leyendo los campos protegidos.
 *
 *  Ejecutá así:  java Ejercicio3EstadoCompartidoConstructor.java
 */

// La clase principal de la solución se llama distinto (y sin public) para
// que ejercicios y soluciones puedan compilarse juntos sin choque de
// nombres. El lanzador java toma igualmente la primera clase del archivo.
class Solucion3EstadoCompartidoConstructor {

    static abstract class EmpleadoBase {
        protected final String nombre;
        protected final double salarioBase;

        // El estado vive ACÁ, una sola vez. Ninguna subclase repite estos
        // campos: eso es justamente lo que evita la herencia bien usada.
        protected EmpleadoBase(String nombre, double salarioBase) {
            this.nombre = nombre;
            this.salarioBase = salarioBase;
        }

        // SOLUCIÓN: método común que usa el estado compartido. Si mañana hay
        // que cambiar el formato del recibo, se cambia UNA vez y alcanza a
        // todos los tipos de empleado.
        void reciboDeSueldo() {
            System.out.println("+-----------------------------------+");
            System.out.println("|         RECIBO DE SUELDO          |");
            System.out.println("+-----------------------------------+");
            System.out.println("Empleado:      " + nombre);
            System.out.println("Salario base:  $ " + String.format("%.2f", salarioBase));
            System.out.println();
        }
    }

    static class Administrativo extends EmpleadoBase {
        Administrativo(String nombre, double salarioBase) {
            super(nombre, salarioBase);
        }
    }

    static class Vendedor extends EmpleadoBase {
        // SOLUCIÓN: como la base solo tiene el constructor (String, double),
        // esta primera línea no es opcional. Sin ella, el compilador busca un
        // super() sin argumentos que no existe y falla con:
        //
        //     error: constructor EmpleadoBase in class EmpleadoBase cannot
        //            be applied to given types
        Vendedor(String nombre, double salarioBase) {
            super(nombre, salarioBase);
        }
    }

    public static void main(String[] args) {
        Administrativo administrativo = new Administrativo("Ana", 850_000);
        administrativo.reciboDeSueldo();

        Vendedor vendedor = new Vendedor("Bruno", 900_000);
        vendedor.reciboDeSueldo();
    }
}

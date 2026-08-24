// Ejemplo: los dos usos de `super` —
//   1) super(...)  delega la construcción al constructor del padre
//      (debe ser la primera sentencia del constructor).
//   2) super.metodo() invoca la versión del padre de un método redefinido.
//
// Ejecutar:  java ejemplos/SuperEnAccion.java

public class SuperEnAccion {

    static class Empleado {
        String nombre;
        double salarioBase;

        // Al existir este constructor con parámetros, Java ya NO genera
        // el constructor vacío: toda subclase DEBE llamar super(...).
        Empleado(String nombre, double salarioBase) {
            this.nombre = nombre;
            this.salarioBase = salarioBase;
            System.out.println("  [constructor Empleado] valida y asigna datos");
        }

        String describir() {
            return nombre + " (empleado) cobra " + salarioBase;
        }
    }

    static class Gerente extends Empleado {
        double bono;

        Gerente(String nombre, double salarioBase, double bono) {
            super(nombre, salarioBase); // USO A: siempre primera sentencia
            this.bono = bono;
            System.out.println("  [constructor Gerente] agrega el bono");
        }

        @Override
        String describir() {
            // USO B: reutilizamos lo que el padre sabe hacer...
            String base = super.describir();
            // ...y extendemos con lo propio de Gerente.
            return base + " + bono " + bono;
        }
    }

    public static void main(String[] args) {
        System.out.println("Creando un Gerente (se ve el encadenamiento):");
        Gerente ana = new Gerente("Ana", 900000, 150000);

        System.out.println();
        System.out.println(ana.describir());
    }
}

// Upcasting y downcasting: subir en la jerarquía es gratis,
// bajar exige verificación con instanceof + pattern matching.
//
// Ejecutar: java UpcastingYDowncasting.java

class Empleado {
    public void trabajar() {
        System.out.println("Trabajando en general...");
    }
}

class Desarrollador extends Empleado {
    @Override
    public void trabajar() {
        System.out.println("Escribiendo código...");
    }

    // Método EXCLUSIVO de la subclase: no existe en Empleado.
    public void desplegarAAmbiente() {
        System.out.println("Desplegando a producción...");
    }
}

class Disenadora extends Empleado {
    @Override
    public void trabajar() {
        System.out.println("Diseñando interfaces...");
    }

    public void entregarFigma() {
        System.out.println("Compartiendo el archivo de Figma...");
    }
}

public class UpcastingYDowncasting {

    public static void main(String[] args) {
        // 1) UPCASTING: siempre seguro, implícito.
        //    Todo Desarrollador ES un Empleado; Java lo garantiza.
        Empleado general = new Desarrollador();
        general.trabajar(); // despacho dinámico → versión de Desarrollador

        // 2) LÍMITE: por la variable 'general' solo se ve lo que declara Empleado.
        //    Descomentar la línea siguiente da ERROR DE COMPILACIÓN:
        // general.desplegarAAmbiente(); // ❌ Empleado no declara ese método

        // 3) DOWNCASTING correcto: instanceof + PATTERN MATCHING (Java 16+).
        //    Si el patrón matchea, 'desarrollador' ya viene convertido.
        if (general instanceof Desarrollador desarrollador) {
            desarrollador.desplegarAAmbiente(); // ✅ seguro, verificamos antes
        } else {
            System.out.println("No es desarrollador.");
        }

        System.out.println();

        // 4) Cuando el tipo real NO coincide, el patrón simplemente no matchea:
        procesar(new Disenadora());
        procesar(new Desarrollador());

        // 5) Lo que NUNCA hay que hacer: downcastear sin verificar.
        Empleado empleado = new Empleado();
        // Descomentar lanza ClassCastException EN TIEMPO DE EJECUCIÓN:
        // Desarrollador falso = (Desarrollador) empleado;
        // falso.desplegarAAmbiente();
        System.out.println("\nUn downcast sin instanceof compila, pero explota en runtime.");
    }

    static void procesar(Empleado empleado) {
        // Programamos contra el tipo general; el comportamiento exclusivo
        // se habilita solo cuando el tipo real lo permite.
        empleado.trabajar();
        if (empleado instanceof Desarrollador dev) {
            dev.desplegarAAmbiente();
        }
        if (empleado instanceof Disenadora diseniadora) {
            diseniadora.entregarFigma();
        }
        System.out.println("---");
    }
}

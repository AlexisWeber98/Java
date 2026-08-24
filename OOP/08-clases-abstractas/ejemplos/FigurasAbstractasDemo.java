// Módulo 08 · Clases abstractas
// Demo 1: el plano FiguraGeometrica promete area() pero no sabe calcularla;
// cada figura concreta completa el hueco y hereda describir() ya funcionando.

public class FigurasAbstractasDemo {

    public static void main(String[] args) {
        // El array habla del PLANO, pero guarda CASAS CONCRETAS:
        // polimorfismo puro apoyado en la clase abstracta.
        FiguraGeometrica[] figuras = {
                new Circulo(3.0),
                new Rectangulo(4.0, 5.0)
        };

        for (FiguraGeometrica figura : figuras) {
            System.out.println(figura.describir());
        }

        /*
         * ── PRUEBA DE FUEGO ─────────────────────────────────────────────
         * Descomentá la línea siguiente y compilar no te va a dejar:
         *
         *   FiguraGeometrica misterio = new FiguraGeometrica("¿?");
         *
         *   error: FiguraGeometrica is abstract; cannot be instantiated
         *
         * El plano está aprobado (estructura válida), pero tiene un hueco
         * sin resolver: no podés vivir adentro del plano.
         * ────────────────────────────────────────────────────────────────
         */
    }
}

// ═══════════════ EL PLANO ═══════════════

abstract class FiguraGeometrica {

    protected final String nombre;   // estado compartido por toda la familia

    protected FiguraGeometrica(String nombre) {
        this.nombre = nombre;        // corre vía super(...) desde cada subclase
    }

    // CONTRATO SIN CUERPO: firma + punto y coma, sin llaves.
    // Toda subclase concreta está OBLIGADA a definir cómo calcula su área.
    abstract double area();

    // CÓDIGO COMPARTIDO Y YA FUNCIONAL: usa area() aunque todavía nadie
    // la haya implementado. Esto es lo que una interfaz NO puede darte igual.
    String describir() {
        return nombre + " -> área = " + area();
    }
}

// ═══════════════ LAS CASAS ═══════════════

class Circulo extends FiguraGeometrica {

    private final double radio;

    Circulo(double radio) {
        super("Círculo");            // inicializa el estado compartido
        this.radio = radio;
    }

    @Override
    double area() {
        return Math.PI * radio * radio;
    }
}

class Rectangulo extends FiguraGeometrica {

    private final double base;
    private final double altura;

    Rectangulo(double base, double altura) {
        super("Rectángulo");
        this.base = base;
        this.altura = altura;
    }

    @Override
    double area() {
        return base * altura;
    }
}

/*
 * ============================================================================
 * Ejercicio 3: Tu Propia Etiqueta
 * ============================================================================
 *
 * ENUNCIADO:
 * Las anotaciones estándar (@Override, @Deprecated) son solo el principio.
 * Cualquier equipo puede definir las suyas con @interface. Acá vas a usar
 * RevisionPendiente, una etiqueta propia para marcar métodos que necesitan
 * una revisión humana, y aplicarla en dos métodos.
 *
 * REQUISITOS:
 * 1. Leé la definición de @RevisionPendiente y explicá (en un comentario)
 *    qué hace @Retention(RetentionPolicy.RUNTIME) y qué hace
 *    @Target(ElementType.METHOD).
 * 2. Aplicá @RevisionPendiente a cargarInventario() indicando tu autoría
 *    y un motivo concreto.
 * 3. Aplicala a generarReporte() SOLO con autor: el motivo debe quedar en
 *    "sin especificar" gracias al valor por defecto.
 *
 * PISTAS:
 * - Sin RUNTIME, la anotación existe al compilar pero desaparece en tiempo
 *   de ejecución: la retención por defecto es CLASS, invisible para la
 *   reflexión. El Ejercicio 4 depende de que esté en RUNTIME.
 * - METHOD limita dónde se puede colgar: probá anotar un campo y mirá el
 *   error del compilador.
 * - Los atributos de una anotación son métodos sin cuerpo: String autor().
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Etiqueta de equipo: "este método espera revisión".
@Retention(RetentionPolicy.RUNTIME)   // sobrevive hasta runtime: la reflexión puede leerla
@Target(ElementType.METHOD)           // solo válida sobre métodos
@interface RevisionPendiente {
    String autor();                                   // obligatorio
    String motivo() default "sin especificar";        // opcional con default
}

public class Ejercicio3TuPropiaEtiqueta {

    // TODO 2: anotá acá con autor y motivo.
    void cargarInventario() {
        System.out.println("Cargando inventario...");
    }

    // TODO 3: anotá acá solo con autor (motivo por defecto).
    void generarReporte() {
        System.out.println("Generando reporte...");
    }

    public static void main(String[] args) {
        new Ejercicio3TuPropiaEtiqueta().cargarInventario();
        new Ejercicio3TuPropiaEtiqueta().generarReporte();
        // TODO 1: escribí como comentario tu explicación de @Retention y @Target.
    }
}

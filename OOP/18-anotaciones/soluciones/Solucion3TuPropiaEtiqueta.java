/*
 * ============================================================================
 * Solución 3: Tu Propia Etiqueta
 * ============================================================================
 *
 * Qué hace cada meta-anotación (anotaciones sobre anotaciones):
 *
 * - @Retention(RetentionPolicy.RUNTIME): define HASTA CUÁNDO vive la
 *   etiqueta. SOURCE muere al compilar, CLASS llega al .class pero la JVM
 *   no la expone (es el default), RUNTIME queda cargada en memoria y la
 *   reflexión puede leerla. Sin RUNTIME, el Ejercicio 4 no vería nada.
 *
 * - @Target(ElementType.METHOD): define DÓNDE se puede colgar. Con METHOD,
 *   anotar un campo o una clase es error de compilación. Existen otros:
 *   FIELD, TYPE, PARAMETER, CONSTRUCTOR...
 *
 * La anotación no ejecuta lógica por sí sola: es datos adjuntos al código.
 * Alguien tiene que LEERLA (compilador, IDE, framework o tu propio
 * scanner del Ejercicio 4). Esa lectura es lo que le da poder.
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
// Etiqueta de equipo, igual a la del Ejercicio 3: definida una sola vez
// en este directorio y consumida por varias clases.
@Retention(RetentionPolicy.RUNTIME)   // sobrevive hasta runtime: la reflexión puede leerla
@Target(ElementType.METHOD)           // solo válida sobre métodos
@interface RevisionPendiente {
    String autor();                                   // obligatorio
    String motivo() default "sin especificar";        // opcional con default
}

public class Solucion3TuPropiaEtiqueta {

    @RevisionPendiente(autor = "Ana", motivo = "Falta manejar el caso de lista vacía")
    void cargarInventario() {
        System.out.println("Cargando inventario...");
    }

    // Sin motivo: el default "sin especificar" entra en acción.
    @RevisionPendiente(autor = "Bruno")
    void generarReporte() {
        System.out.println("Generando reporte...");
    }

    public static void main(String[] args) {
        new Solucion3TuPropiaEtiqueta().cargarInventario();
        new Solucion3TuPropiaEtiqueta().generarReporte();

        // Prueba de que la etiqueta está viva en runtime:
        var metodo = reflectMetodo("generarReporte");
        RevisionPendiente etiqueta = metodo.getAnnotation(RevisionPendiente.class);
        System.out.println("Reflexión ve -> autor: " + etiqueta.autor()
                + ", motivo: " + etiqueta.motivo());
    }

    static java.lang.reflect.Method reflectMetodo(String nombre) {
        try {
            return Solucion3TuPropiaEtiqueta.class.getDeclaredMethod(nombre);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }
}

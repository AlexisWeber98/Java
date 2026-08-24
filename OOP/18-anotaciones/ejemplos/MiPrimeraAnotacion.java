import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Ejemplo 2 — Tu PRIMERA anotación propia.
 *
 * Una anotación se declara con @interface. Los "atributos" son métodos
 * sin cuerpo; `default` les da un valor opcional.
 *
 * Las meta-anotaciones responden dos preguntas:
 *   @Retention(RUNTIME) → ¿sobrevive hasta la ejecución? (obligatorio para reflexión)
 *   @Target(METHOD)     → ¿dónde se puede pegar? (acá: solo sobre métodos)
 *
 * Importante: esta etiqueta NO hace nada por sí sola. Es metadata.
 * El que la lee y reacciona es el escáner del ejemplo 3.
 */
// ---------- Declaración de la etiqueta ----------
// Tipo tope de nivel (el archivo puede tener varios; solo uno público),
// así el escáner del ejemplo 3 puede usarla directamente.

/** Marca métodos que son rutinas críticas, con responsable y prioridad. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface RutinaImportante {
    String responsable();
    int prioridad() default 1;
}

public class MiPrimeraAnotacion {

    // ---------- Aplicación de la etiqueta ----------

    @RutinaImportante(responsable = "Ana", prioridad = 1)
    public void backupBaseDeDatos() {
        System.out.println("[tarea] Backup de la base de datos");
    }

    @RutinaImportante(responsable = "Bruno", prioridad = 3)
    public void limpiarArchivosTemporales() {
        System.out.println("[tarea] Limpieza de archivos temporales");
    }

    public void metodoComun() {
        System.out.println("[común] Sin etiqueta: nadie especial me mira");
    }

    // ---------- Demo mínima de lectura ----------

    public static void main(String[] args) throws Exception {
        MiPrimeraAnotacion app = new MiPrimeraAnotacion();

        app.metodoComun();

        // Lectura directa con reflexión (gracias a RetentionPolicy.RUNTIME):
        RutinaImportante etiqueta = app.getClass()
                .getDeclaredMethod("backupBaseDeDatos")
                .getAnnotation(RutinaImportante.class);

        System.out.println("Etiqueta leída en runtime → responsable: "
                + etiqueta.responsable() + ", prioridad: " + etiqueta.prioridad());

        // Probar el escáner completo: java EscaneandoAnotaciones.java
    }
}

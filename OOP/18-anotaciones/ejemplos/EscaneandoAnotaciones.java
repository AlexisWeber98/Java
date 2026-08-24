import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Ejemplo 3 — El patrón ESCÁNER: así arranca un mini-framework.
 *
 * Este archivo recorre los métodos de una clase con reflexión, encuentra
 * los marcados con @RutinaImportante (declarada en MiPrimeraAnotacion.java)
 * y los ejecuta ordenados por prioridad.
 *
 * ESE loop es, literalmente, lo que hacen JUnit (@Test), Spring (@Component)
 * y JPA (@Entity): loop + reflexión + reacción. Nada más.
 *
 * Compilar y correr (desde esta carpeta):
 *   javac MiPrimeraAnotacion.java EscaneandoAnotaciones.java
 *   java EscaneandoAnotaciones
 */
public class EscaneandoAnotaciones {

    /** Clase "candidata": el escáner no sabe nada de ella salvo sus etiquetas. */
    static class TareasDelSistema {

        @RutinaImportante(responsable = "Ana", prioridad = 1)
        public void backupBaseDeDatos() {
            System.out.println("  [tarea] Backup de la base de datos");
        }

        @RutinaImportante(responsable = "Bruno", prioridad = 3)
        public void limpiarArchivosTemporales() {
            System.out.println("  [tarea] Limpieza de archivos temporales");
        }

        @RutinaImportante(responsable = "Carla", prioridad = 2)
        public void enviarReporteDiario() {
            System.out.println("  [tarea] Envío del reporte diario");
        }

        public void metodoComun() {
            System.out.println("  [común] No etiquetado → el escáner lo ignora");
        }
    }

    // ---------- El mini-framework ----------

    public static void main(String[] args) throws Exception {
        Class<?> clase = TareasDelSistema.class;
        Object instancia = clase.getDeclaredConstructor().newInstance();

        System.out.println("Escaneando: " + clase.getSimpleName());
        System.out.println("--- Métodos encontrados por reflexión ---");

        for (Method metodo : clase.getDeclaredMethods()) {
            String marca = metodo.isAnnotationPresent(RutinaImportante.class)
                    ? "@RutinaImportante"
                    : "(sin etiqueta)";
            System.out.println(marca + " → " + metodo.getName());
        }

        System.out.println("--- Ejecutando rutinas por prioridad ---");

        Arrays.stream(clase.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(RutinaImportante.class))
                .sorted(Comparator.comparingInt(
                        m -> m.getAnnotation(RutinaImportante.class).prioridad()))
                .forEach(metodo -> {
                    RutinaImportante etiqueta =
                            metodo.getAnnotation(RutinaImportante.class);
                    System.out.println("Prioridad " + etiqueta.prioridad()
                            + " · responsable: " + etiqueta.responsable());
                    try {
                        metodo.invoke(instancia);
                    } catch (ReflectiveOperationException e) {
                        throw new IllegalStateException(
                                "No se pudo invocar " + metodo.getName(), e);
                    }
                });

        System.out.println("--- Fin del mini-framework ---");
    }
}

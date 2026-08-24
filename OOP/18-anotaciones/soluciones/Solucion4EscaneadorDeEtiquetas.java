/*
 * ============================================================================
 * Solución 4: Escaneador De Etiquetas
 * ============================================================================
 *
 * Este scanner es, en miniatura, el motor de todos los frameworks modernos.
 * Spring escanea clases buscando @Component; JUnit busca @Test; Jackson
 * busca @JsonProperty. Todos hacen lo mismo que hacemos acá:
 *
 *   1. pedir por reflexión la lista de miembros,
 *   2. preguntarles si llevan una anotación determinada,
 *   3. reaccionar a esos metadatos.
 *
 * La diferencia de escala es que ellos lo hacen con millones de clases y
 * caches; el principio es idéntico. Cuando entiendes esto, los frameworks
 * dejan de ser magia y pasan ser código legible por alguien más.
 */
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

public class Solucion4EscaneadorDeEtiquetas {

    static class TareasDelSistema {
        @RevisionPendiente(autor = "Bruno", motivo = "Posible fuga de memoria con archivos grandes")
        void procesarLotes() {
            System.out.println("Procesando lotes...");
        }

        @RevisionPendiente(autor = "Ana", motivo = "Falta validar el formato de entrada")
        void importarDatos() {
            System.out.println("Importando datos...");
        }

        @RevisionPendiente(autor = "Ana")
        void exportarResultados() {
            System.out.println("Exportando resultados...");
        }

        void tareaAlDia() {
            System.out.println("Todo en orden acá.");
        }
    }

    public static void main(String[] args) {
        System.out.println("Metodos pendientes de revision:");
        System.out.println("--------------------------------");

        Method[] metodos = TareasDelSistema.class.getDeclaredMethods();

        Arrays.stream(metodos)
                .filter(m -> m.isAnnotationPresent(RevisionPendiente.class))
                .sorted(Comparator.comparing(
                        m -> m.getAnnotation(RevisionPendiente.class).autor()))
                .forEach(m -> {
                    RevisionPendiente etiqueta = m.getAnnotation(RevisionPendiente.class);
                    System.out.println(etiqueta.autor() + " -> " + m.getName()
                            + " (" + etiqueta.motivo() + ")");
                });

        // tareaAlDia() no aparece: no lleva la etiqueta. El filtro hizo su trabajo.
    }
}

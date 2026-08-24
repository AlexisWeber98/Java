/*
 * ============================================================================
 * Ejercicio 4: Escaneador De Etiquetas
 * ============================================================================
 *
 * ENUNCIADO:
 * Marcar métodos con @RevisionPendiente sirve poco si nadie lee las
 * etiquetas. Tu tarea: escribir un mini-scanner que recorra los métodos de
 * TareasDelSistema por reflexión, se quede solo con los marcados y los
 * liste ordenados por autor.
 *
 * REQUISITOS:
 * 1. Obtené todos los métodos declarados de TareasDelSistema.
 * 2. Filtrá los que tengan @RevisionPendiente.
 * 3. Ordenalos por autor e imprimí con el formato:
 *        autor -> nombreMetodo (motivo)
 *    Ejemplo: Ana -> exportarResultados (sin especificar)
 * 4. Verificá que tareaAlDia() NO aparezca en el listado.
 *
 * PISTAS:
 * - clase.getDeclaredMethods() devuelve Method[]; getAnnotation(...) te da
 *   la etiqueta o null si no está.
 * - Para ordenar: Arrays.sort(metodos, Comparator.comparing(...)) comparando
 *   el autor que vive DENTRO de la anotación.
 * - Esto es exactamente lo que hacen Spring (@Component), JUnit (@Test) o
 *   Jackson (@JsonProperty) por dentro: leer anotaciones y reaccionar.
 */
public class Ejercicio4EscaneadorDeEtiquetas {

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
        // TODO: escribí el scanner acá:
        //  1) TareasDelSistema.class.getDeclaredMethods()
        //  2) filtrar por isAnnotationPresent(RevisionPendiente.class)
        //  3) ordenar por autor y mostrar "autor -> metodo (motivo)"
        System.out.println("Metodos pendientes de revision:");
        // ...
    }
}

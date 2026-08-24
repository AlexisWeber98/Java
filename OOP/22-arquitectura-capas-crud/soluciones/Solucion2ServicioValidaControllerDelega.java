import java.util.ArrayList;
import java.util.List;

/*
 * ============================================================================
 * SOLUCIÓN · Módulo 22 · Ejercicio 2 — El servicio valida, el controlador delega
 * ============================================================================
 * Qué cambió respecto al starter:
 *   - Las tres reglas (vacío, corto, duplicado) ahora viven en el servicio.
 *   - El estado (lista de títulos) es privado del servicio; el main lo ve
 *     solo por listarTitulos(), que devuelve una copia inmodificable.
 *   - El main quedó en tres líneas de responsabilidad: leer, delegar, mostrar.
 *     No tiene ni un `if` de negocio: convierte excepciones en mensajes.
 * Beneficio concreto: esta misma clase de servicio se puede llamar mañana
 * desde una API REST o desde un test JUnit sin tocar una línea.
 * ============================================================================
 */
public class Solucion2ServicioValidaControllerDelega {

    public static void main(String[] args) {
        ServicioTareasSimpleSol servicio = new ServicioTareasSimpleSol();

        // Escenario completo en una corrida: vacío, corto, duplicado y dos válidos.
        String[] escrituras = { "", "ab", "Comprar leche", "Comprar leche", "Pasear al perro" };

        for (String titulo : escrituras) {
            System.out.println("> escribió: \"" + titulo + "\"");
            try {
                servicio.agregar(titulo);
                System.out.println("  Tarea agregada.");
            } catch (IllegalArgumentException e) {
                System.out.println("  Error: " + e.getMessage());
            }
        }
        System.out.println("Estado final: " + servicio.listarTitulos());
    }

    /**
     * CAPA DE SERVICIO: dueña exclusiva de las reglas y del estado.
     * Notá que valida sobre titulo.trim(): el dato se normaliza UNA vez, acá.
     */
    static class ServicioTareasSimpleSol {
        private static final int TITULO_MINIMO = 3;
        private final List<String> titulosGuardados = new ArrayList<>();

        void agregar(String titulo) {
            if (titulo == null || titulo.isBlank()) {
                throw new IllegalArgumentException("el título no puede estar vacío");
            }
            String normalizado = titulo.trim();
            if (normalizado.length() < TITULO_MINIMO) {
                throw new IllegalArgumentException(
                        "el título debe tener al menos " + TITULO_MINIMO + " caracteres");
            }
            if (titulosGuardados.contains(normalizado)) {
                throw new IllegalArgumentException("ya existe una tarea con ese título");
            }
            titulosGuardados.add(normalizado);
        }

        List<String> listarTitulos() {
            return List.copyOf(titulosGuardados);
        }
    }
}

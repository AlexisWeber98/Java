import java.util.ArrayList;
import java.util.List;

/*
 * ============================================================================
 * Módulo 22 · Arquitectura en capas — Ejercicio 2
 * El servicio valida, el controlador delega
 * ============================================================================
 *
 * ENUNCIADO
 * Este controlador nació apurado y quedó con las reglas de negocio adentro:
 * valida el título Y guarda la tarea en el propio main. Eso hace imposible
 * reutilizar las reglas desde otra interfaz (web, API) y las deja sin tests
 * limpios.
 *
 * Tu misión: mover TODAS las reglas a ServicioTareasSimple y dejar el
 * controlador haciendo solo tres cosas: leer la entrada, delegar en el
 * servicio y mostrar el resultado.
 *
 * REQUISITOS
 *   1. ServicioTareasSimple.agregar(String titulo) debe:
 *      - rechazar título vacío o con solo espacios,
 *      - rechazar título de menos de 3 caracteres,
 *      - rechazar título duplicado,
 *      - guardar y confirmar si todo está bien.
 *      Usá IllegalArgumentException con mensajes claros para los errores.
 *   2. El controlador no puede contener ni un solo `if` de validación.
 *   3. El estado (la lista de títulos) vive en el servicio, no en el main.
 *
 * PISTAS
 *   - try/catch alrededor de servicio.agregar(...) es la forma limpia de que
 *     la presentación convierta errores en mensajes para el usuario.
 *   - Corrélo tal cual está: entra "" y vas a ver la ruta de error andar.
 *   - Cambiá lo que devuelve leerTitulo() para ensayar los otros caminos.
 * ============================================================================
 */
public class Ejercicio2ServicioValidaControllerDelega {

    /** Simula la terminal: devuelve lo que "escribió" el usuario, sin bloquear la ejecución. */
    static String leerTitulo() {
        return ""; // Cambiá este valor para probar: "ab", "Comprar leche", etc.
    }

    public static void main(String[] args) {
        ServicioTareasSimple servicio = new ServicioTareasSimple();

        // === CAPA DE PRESENTACIÓN (controlador) =============================
        String titulo = leerTitulo();

        // TODO: todo este bloque de reglas tiene que mudarse al servicio.
        // El controlador debe quedar así de flaco:
        //
        //     try {
        //         servicio.agregar(titulo);
        //         System.out.println("Tarea agregada: " + titulo);
        //     } catch (IllegalArgumentException e) {
        //         System.out.println("Error: " + e.getMessage());
        //     }
        //
        if (titulo.isBlank()) {
            System.out.println("Error: el título no puede estar vacío.");
            return;
        }
        if (titulo.trim().length() < 3) {
            System.out.println("Error: el título debe tener al menos 3 caracteres.");
            return;
        }
        if (servicio.existe(titulo)) {
            System.out.println("Error: ya existe una tarea con ese título.");
            return;
        }

        // TODO: el guardado también es responsabilidad del servicio.
        servicio.guardarDirecto(titulo); // método puente provisorio: eliminámelo al refactorizar
        System.out.println("Tarea agregada: " + titulo);
        System.out.println("Tareas guardadas: " + servicio.listarTitulos());
    }
}

/**
 * CAPA DE SERVICIO (negocio). Hoy es una cáscara casi vacía: después del
 * refactor, TODAS las reglas del ejercicio tienen que vivir acá adentro.
 */
class ServicioTareasSimple {
    private final List<String> titulosGuardados = new ArrayList<>();

    // TODO: reemplazá existe() + guardarDirecto() por un único método:
    //   void agregar(String titulo)
    // que valide las tres reglas y lance IllegalArgumentException con un
    // mensaje claro cuando algo falle. Tip: guardá siempre titulo.trim().
    boolean existe(String titulo) {
        return titulosGuardados.contains(titulo);
    }

    void guardarDirecto(String titulo) {
        titulosGuardados.add(titulo);
    }

    List<String> listarTitulos() {
        return List.copyOf(titulosGuardados);
    }
}

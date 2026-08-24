import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

/*
 * ============================================================================
 * Módulo 22 · Arquitectura en capas — Ejercicio 5 (DESAFÍO)
 * CRUD de tareas con las cinco capas bien atadas
 * ============================================================================
 *
 * ENUNCIADO
 * Armá el sistema completo respetando esta estructura:
 *
 *     TareasApp (consola)          ← PRESENTACIÓN: lee/escribe, NUNCA valida reglas
 *        │ delega en
 *     ServicioTareas               ← NEGOCIO: validaciones y coordinación
 *        │ usa
 *     RepositorioTareas (interfaz) ← PERSISTENCIA: contrato
 *        │ implementado por
 *     RepositorioTareasEnMemoria   ← INFRAESTRUCTURA: HashMap
 *
 * El modelo (Tarea) y el DTO (TareaDto) viajan entre capas: el servicio
 * devuelve SIEMPRE dtos a la consola, jamás entidades.
 *
 * REQUISITOS
 *   1. ServicioTareas:
 *      - crear(titulo): rechaza título vacío y título mayor a 50 caracteres.
 *      - Los ids los genera el servicio con su secuenciador: no pueden
 *        duplicarse por construcción; igual, completar/eliminar validan que
 *        el id exista antes de operar.
 *      - listar(): devuelve List<TareaDto>.
 *   2. TareasApp.ejecutar(): bucle que consume comandos de la cola de entrada
 *      y despacha: agregar | listar | completar <id> | eliminar <id> | salir.
 *   3. La consola convierte IllegalArgumentException en mensajes; no valida
 *      nada ella misma.
 *   4. Todo tiene que correr sin interacción: la entrada es una cola de
 *      Strings ya escrita en main.
 *
 * ¿POR QUÉ ENTRADA SIMULADA Y NO SCANNER?
 *   Determinismo: misma entrada, misma salida siempre. No bloquea la corrección
 *   automática ni los entornos sin teclado. Y el día de mañana enchufás un
 *   Scanner (o una web) en el mismo lugar, sin tocar servicio ni repositorio.
 *
 * PISTAS
 *   - Orden de armado sugerido: modelo → dto → repositorio → servicio → app.
 *     Cada pieza se apoya en la anterior.
 *   - completar(id): buscá la tarea, pedile marcarCompletada() y guardá el
 *     resultado (guardar crea O actualiza).
 *   - En la consola, Long.parseLong(idTexto) puede fallar: atrapalo.
 * ============================================================================
 */
public class Ejercicio5DesafioCrudCapasCompleto {

    public static void main(String[] args) {
        // Cableado explícito: acá se ve quién depende de quién.
        RepositorioTareas repositorio = new RepositorioTareasEnMemoria();
        ServicioTareas servicio = new ServicioTareas(repositorio);

        // Entrada simulada: la "terminal" entrega comandos de una cola pre-escrita.
        Queue<String> comandos = new ArrayDeque<>(List.of(
                "agregar", "Comprar leche",
                "agregar", "Estudiar capas",
                "agregar", "",
                "completar", "1",
                "listar",
                "eliminar", "2",
                "listar",
                "completar", "99",
                "salir"));

        TareasApp app = new TareasApp(servicio, comandos);
        app.ejecutar();
    }
}

/** MODELO del dominio: inmutable, con identidad propia (id). */
record Tarea(long id, String titulo, boolean completada) {

    /** Devuelve una copia completada: los records no mutan. */
    Tarea marcarCompletada() {
        return new Tarea(id, titulo, true);
    }
}

/** DTO de presentación: lo único que la consola tiene permitido conocer. */
record TareaDto(long id, String titulo, boolean completada) { }

/** CONTRATO de persistencia: guardar crea o actualiza según el id. */
interface RepositorioTareas {
    Tarea guardar(Tarea tarea);

    Optional<Tarea> buscarPorId(long id);

    List<Tarea> listarTodas();

    void eliminar(long id);
}

/** INFRAESTRUCTURA: persistencia en memoria, un simple HashMap. */
class RepositorioTareasEnMemoria implements RepositorioTareas {
    private final Map<Long, Tarea> tareas = new HashMap<>();

    @Override
    public Tarea guardar(Tarea tarea) {
        tareas.put(tarea.id(), tarea);
        return tarea;
    }

    @Override
    public Optional<Tarea> buscarPorId(long id) {
        return Optional.ofNullable(tareas.get(id));
    }

    @Override
    public List<Tarea> listarTodas() {
        return List.copyOf(tareas.values());
    }

    @Override
    public void eliminar(long id) {
        tareas.remove(id);
    }
}

/**
 * NEGOCIO: dueña de las reglas. Recibe entidades del repositorio y entrega
 * dtos a la presentación.
 */
class ServicioTareas {
    static final int TITULO_MAXIMO = 50;

    private final RepositorioTareas repositorio;
    private long secuenciador = 0;

    ServicioTareas(RepositorioTareas repositorio) {
        this.repositorio = repositorio;
    }

    // TODO: validar el título (no vacío, máximo 50 caracteres), asignar el
    //  próximo id con el secuenciador, guardar y devolver el dto.
    TareaDto crear(String titulo) {
        throw new UnsupportedOperationException("TODO: implementar crear");
    }

    // TODO: listar todo, mapeado a dto.
    List<TareaDto> listar() {
        throw new UnsupportedOperationException("TODO: implementar listar");
    }

    // TODO: buscar la tarea (si no existe: IllegalArgumentException), marcarla
    //  completada, guardar y devolver el dto actualizado.
    TareaDto completar(long id) {
        throw new UnsupportedOperationException("TODO: implementar completar");
    }

    // TODO: validar que exista y recién ahí eliminar.
    void eliminar(long id) {
        throw new UnsupportedOperationException("TODO: implementar eliminar");
    }
}

/**
 * PRESENTACIÓN: consola por turnos. Consume comandos de la cola, delega en el
 * servicio y muestra resultados o errores.
 */
class TareasApp {
    private final ServicioTareas servicio;
    private final Queue<String> entrada;

    TareasApp(ServicioTareas servicio, Queue<String> entrada) {
        this.servicio = servicio;
        this.entrada = entrada;
    }

    // TODO: bucle while (!entrada.isEmpty()): poll del comando + switch.
    //  "agregar" consume otra línea como título; "completar"/"eliminar"
    //  consumen otra línea como id; "salir" corta; lo demás es desconocido.
    void ejecutar() {
        throw new UnsupportedOperationException("TODO: implementar ejecutar");
    }
}

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

/*
 * ============================================================================
 * SOLUCIÓN · Módulo 22 · Ejercicio 5 — CRUD en capas, implementación de referencia
 * ============================================================================
 * Decisiones clave:
 *   - Cableado en main: la consola conoce al SERVICIO (y solo al servicio);
 *     el servicio conoce a la INTERFAZ del repositorio; la memoria es un
 *     detalle que se enchufa al final. Cambiar el almacén no toca ni app ni
 *     servicio.
 *   - El secuenciador vive en el servicio: los ids son únicos por
 *     construcción; completar/eliminar igual validan existencia para dar
 *     errores amables ("no existe una tarea con id 99").
 *   - El servicio devuelve SIEMPRE TareaDto: la consola jamás ve la entidad.
 *   - La consola no valida nada: traduce excepciones a mensajes. Las reglas
 *     viven en un único lugar y sirven para cualquier interfaz futura.
 *   - Tipos anidados: así cada solución queda autocontenida en un archivo
 *     compilable junto con los ejercicios (sin packages no pueden repetirse
 *     nombres de nivel superior).
 * ============================================================================
 */
public class Solucion5DesafioCrudCapasCompleto {

    public static void main(String[] args) {
        // Cableado: infraestructura → negocio → presentación.
        RepositorioTareas repositorio = new RepositorioTareasEnMemoria();
        ServicioTareas servicio = new ServicioTareas(repositorio);

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

        new TareasApp(servicio, comandos).ejecutar();
    }

    /** MODELO: inmutable, con identidad. */
    record Tarea(long id, String titulo, boolean completada) {
        Tarea marcarCompletada() {
            return new Tarea(id, titulo, true);
        }
    }

    /** DTO: contrato explícito con la pantalla. */
    record TareaDto(long id, String titulo, boolean completada) { }

    /** PERSISTENCIA: contrato. guardar crea o actualiza según el id. */
    interface RepositorioTareas {
        Tarea guardar(Tarea tarea);

        Optional<Tarea> buscarPorId(long id);

        List<Tarea> listarTodas();

        void eliminar(long id);
    }

    /** INFRAESTRUCTURA: HashMap en memoria. */
    static class RepositorioTareasEnMemoria implements RepositorioTareas {
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

    /** NEGOCIO: reglas + coordinación + mapeo entidad→dto. */
    static class ServicioTareas {
        static final int TITULO_MAXIMO = 50;

        private final RepositorioTareas repositorio;
        private long secuenciador = 0;

        ServicioTareas(RepositorioTareas repositorio) {
            this.repositorio = repositorio;
        }

        TareaDto crear(String titulo) {
            validarTitulo(titulo);
            secuenciador++;
            Tarea guardada = repositorio.guardar(new Tarea(secuenciador, titulo.trim(), false));
            return aDto(guardada);
        }

        List<TareaDto> listar() {
            return repositorio.listarTodas().stream().map(this::aDto).toList();
        }

        TareaDto completar(long id) {
            Tarea tarea = buscarOError(id);
            return aDto(repositorio.guardar(tarea.marcarCompletada()));
        }

        void eliminar(long id) {
            buscarOError(id);
            repositorio.eliminar(id);
        }

        private Tarea buscarOError(long id) {
            return repositorio.buscarPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("no existe una tarea con id " + id));
        }

        private void validarTitulo(String titulo) {
            if (titulo == null || titulo.isBlank()) {
                throw new IllegalArgumentException("el título no puede estar vacío");
            }
            if (titulo.length() > TITULO_MAXIMO) {
                throw new IllegalArgumentException(
                        "el título no puede superar " + TITULO_MAXIMO + " caracteres");
            }
        }

        private TareaDto aDto(Tarea tarea) {
            return new TareaDto(tarea.id(), tarea.titulo(), tarea.completada());
        }
    }

    /** PRESENTACIÓN: consola por turnos sobre entrada simulada. */
    static class TareasApp {
        private final ServicioTareas servicio;
        private final Queue<String> entrada;

        TareasApp(ServicioTareas servicio, Queue<String> entrada) {
            this.servicio = servicio;
            this.entrada = entrada;
        }

        void ejecutar() {
            System.out.println("MENÚ — comandos: agregar | listar | completar <id> | eliminar <id> | salir");
            while (!entrada.isEmpty()) {
                String comando = entrada.poll();
                System.out.println("> " + comando);
                switch (comando) {
                    case "agregar" -> agregar();
                    case "listar" -> listar();
                    case "completar" -> completar();
                    case "eliminar" -> eliminar();
                    case "salir" -> {
                        System.out.println("¡Hasta luego!");
                        return;
                    }
                    default -> System.out.println("  Comando desconocido: " + comando);
                }
            }
            System.out.println("(fin de la entrada)");
        }

        private void agregar() {
            String titulo = entrada.poll();
            if (titulo == null) {
                System.out.println("  Error: faltó escribir el título.");
                return;
            }
            try {
                TareaDto dto = servicio.crear(titulo);
                System.out.println("  Alta OK: " + dto);
            } catch (IllegalArgumentException e) {
                System.out.println("  Error: " + e.getMessage());
            }
        }

        private void listar() {
            List<TareaDto> tareas = servicio.listar();
            if (tareas.isEmpty()) {
                System.out.println("  (sin tareas)");
                return;
            }
            for (TareaDto dto : tareas) {
                String estado = dto.completada() ? "Completada" : "Pendiente";
                System.out.println("  [" + dto.id() + "] " + dto.titulo() + " — " + estado);
            }
        }

        private void completar() {
            operarSobreId(id -> System.out.println("  Completada: " + servicio.completar(id)));
        }

        private void eliminar() {
            operarSobreId(id -> {
                servicio.eliminar(id);
                System.out.println("  Eliminada la tarea " + id);
            });
        }

        /** Lee el id de la entrada, lo parsea y ejecuta la operación del caso. */
        private void operarSobreId(java.util.function.LongConsumer operacion) {
            String texto = entrada.poll();
            try {
                operacion.accept(Long.parseLong(texto));
            } catch (NumberFormatException e) {
                System.out.println("  Error: \"" + texto + "\" no es un id válido.");
            } catch (IllegalArgumentException e) {
                System.out.println("  Error: " + e.getMessage());
            }
        }
    }
}

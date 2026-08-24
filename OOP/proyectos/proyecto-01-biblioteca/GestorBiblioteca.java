/*
 * GestorBiblioteca.java — Proyecto Integrador N°1: Biblioteca
 * Clase estilo SERVICIO: conecta los repositorios genéricos con las REGLAS
 * del negocio (quién puede llevarse qué) y mantiene el historial de
 * movimientos como lista de records.
 *
 * Acá no hay System.out: un servicio decide y valida; mostrar es tarea de Main.
 *
 * Compilación y ejecución: javac *.java && java Main
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GestorBiblioteca {

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final RepositorioGenerico<ItemBiblioteca, String> repositorioItems;
    private final RepositorioGenerico<Socio, Integer> repositorioSocios;
    private final List<RegistroPrestamo> historial = new ArrayList<>();

    public GestorBiblioteca(RepositorioGenerico<ItemBiblioteca, String> repositorioItems,
                            RepositorioGenerico<Socio, Integer> repositorioSocios) {
        this.repositorioItems = repositorioItems;
        this.repositorioSocios = repositorioSocios;
    }

    // ---------------- altas ----------------

    /** Da de alta un ítem validando que su código no esté repetido. */
    public void agregarItem(ItemBiblioteca item) {
        if (repositorioItems.existe(item.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un ítem con el código " + item.getCodigo());
        }
        repositorioItems.guardar(item.getCodigo(), item);
    }

    /** Inscribe un socio con id automático gracias al contador del repositorio. */
    public Socio inscribirSocio(String nombre) {
        int idNuevo = repositorioSocios.generarProximoId();
        Socio socioNuevo = new Socio(idNuevo, nombre);
        repositorioSocios.guardar(idNuevo, socioNuevo);
        return socioNuevo;
    }

    // ---------------- búsquedas ----------------

    public ItemBiblioteca buscarItem(String codigo) throws ItemInexistenteException {
        ItemBiblioteca item = repositorioItems.buscarPorId(codigo);
        if (item == null) {
            throw new ItemInexistenteException(codigo, "catálogo de ítems");
        }
        return item;
    }

    public Socio buscarSocio(int idSocio) throws ItemInexistenteException {
        Socio socio = repositorioSocios.buscarPorId(idSocio);
        if (socio == null) {
            throw new ItemInexistenteException("#" + idSocio, "padrón de socios");
        }
        return socio;
    }

    /** Variante sin excepción para usos internos y demos; devuelve null si no existe. */
    public ItemBiblioteca buscarItemSilencioso(String codigo) {
        return repositorioItems.buscarPorId(codigo);
    }

    /** Variante sin excepción para usos internos y demos; devuelve null si no existe. */
    public Socio buscarSocioSilencioso(int idSocio) {
        return repositorioSocios.buscarPorId(idSocio);
    }

    // ---------------- regla principal: préstamo ----------------

    /**
     * Corazón del negocio: valida TODO antes de mover un solo estado.
     * Falla rápido con la excepción específica que corresponda:
     *   1) ¿existen el ítem y el socio?
     *   2) ¿el ítem está disponible SEGÚN SU PROPIA REGLA (polimorfismo)?
     *   3) ¿el socio tiene cupo?
     */
    public void prestar(String codigoItem, int idSocio)
            throws ItemInexistenteException, ItemNoDisponibleException, SocioConLimiteAlcanzadoException {
        ItemBiblioteca item = buscarItem(codigoItem);
        Socio socio = buscarSocio(idSocio);

        if (!item.estaDisponibleParaPrestamo()) {
            throw new ItemNoDisponibleException(item.getCodigo(), item.getTitulo(), item.getEstado());
        }
        if (!socio.puedeTomarPrestamo()) {
            throw new SocioConLimiteAlcanzadoException(socio);
        }

        item.prestar(socio);
        historial.add(new RegistroPrestamo(item.getTitulo(), socio.getNombre(),
                ahora(), RegistroPrestamo.Accion.PRESTAMO));
    }

    /** Devolución: repone estados en ítem y socio, y deja constancia. */
    public void devolver(String codigoItem, int idSocio) throws ItemInexistenteException {
        ItemBiblioteca item = buscarItem(codigoItem);
        Socio socio = buscarSocio(idSocio);

        item.devolver();
        socio.quitarPrestamo(codigoItem);
        historial.add(new RegistroPrestamo(item.getTitulo(), socio.getNombre(),
                ahora(), RegistroPrestamo.Accion.DEVOLUCION));
    }

    // ---------------- consultas ----------------

    /** Historial completo en orden de ocurrencia (copia inmutable hacia afuera). */
    public List<RegistroPrestamo> getHistorial() {
        return List.copyOf(historial);
    }

    /** Catálogo ordenado alfabéticamente por título. */
    public List<ItemBiblioteca> itemsOrdenadosPorTitulo() {
        List<ItemBiblioteca> copia = repositorioItems.listarTodos();
        copia.sort(Comparator.comparing(ItemBiblioteca::getTitulo));
        return copia;
    }

    /** Padrón de socios ordenado por id. */
    public List<Socio> sociosPorId() {
        List<Socio> copia = repositorioSocios.listarTodos();
        copia.sort(Comparator.comparingInt(Socio::getId));
        return copia;
    }

    private String ahora() {
        return LocalDateTime.now().format(FORMATO_FECHA);
    }
}

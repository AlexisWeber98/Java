/*
 * Main.java — Proyecto Integrador N°1: Biblioteca
 * Punto de entrada con DOS modos:
 *
 *   java Main              → demo guiada: recorre TODOS los escenarios del dominio
 *   java Main interactivo  → menú por consola con Scanner sobre datos sembrados
 *
 * Compilación y ejecución: javac *.java && java Main
 */
import java.util.List;
import java.util.Scanner;

public class Main {

    private final GestorBiblioteca gestor;
    private final Scanner entrada = new Scanner(System.in);

    public Main(GestorBiblioteca gestor) {
        this.gestor = gestor;
    }

    public static void main(String[] args) {
        Main app = new Main(crearGestorConCatalogo());
        if (List.of(args).contains("interactivo")) {
            app.ejecutarModoInteractivo();
        } else {
            app.ejecutarDemoGuiada();
        }
    }

    /** Armado inicial común a ambos modos: repos + gestor + semilla de datos. */
    private static GestorBiblioteca crearGestorConCatalogo() {
        RepositorioGenerico<ItemBiblioteca, String> repositorioItems = new RepositorioEnMemoria<>();
        RepositorioGenerico<Socio, Integer> repositorioSocios = new RepositorioEnMemoria<>();
        GestorBiblioteca gestor = new GestorBiblioteca(repositorioItems, repositorioSocios);
        sembrarCatalogo(gestor);
        return gestor;
    }

    private static void sembrarCatalogo(GestorBiblioteca gestor) {
        gestor.agregarItem(new Libro("LIB-001", "Cien años de soledad", 1967,
                CategoriaItem.NOVELA, "Gabriel García Márquez", 417));
        gestor.agregarItem(new Libro("LIB-002", "El universo elegante", 1999,
                CategoriaItem.CIENCIA, "Brian Greene", 448));
        gestor.agregarItem(new Libro("LIB-003", "Breve historia del tiempo", 1988,
                CategoriaItem.CIENCIA, "Stephen Hawking", 256));
        gestor.agregarItem(new Libro("LIB-004", "Ficciones", 1944,
                CategoriaItem.NOVELA, "Jorge Luis Borges", 174));
        gestor.agregarItem(new Revista("REV-001", "Muy Interesante", 2019,
                CategoriaItem.CIENCIA, 243, "mensual"));
        gestor.agregarItem(new Revista("REV-002", "National Geographic", 2026,
                CategoriaItem.CIENCIA, 290, "mensual"));
        gestor.agregarItem(new DVD("DVD-001", "Cosmos: una odisea del espacio-tiempo", 2014,
                CategoriaItem.AUDIOVISUAL, "Brannon Braga", 550));
        gestor.agregarItem(new DVD("DVD-002", "El hijo de la novia", 2001,
                CategoriaItem.AUDIOVISUAL, "Juan José Campanella", 124));

        gestor.inscribirSocio("Ana Gómez");
        gestor.inscribirSocio("Bruno Díaz");
        gestor.inscribirSocio("Carla Ruiz");
    }

    // =====================================================================
    // MODO 1: DEMO GUIADA (sin interacción)
    // =====================================================================

    private void ejecutarDemoGuiada() {
        titulo("DEMO GUIADA — BIBLIOTECA COMUNITARIA");
        System.out.println("Cada sección ejercita un pilar distinto. Mirá los mensajes [OK] y [RECHAZADO].");

        seccion("1) Catálogo inicial: alta de los tres tipos (abstracción + polimorfismo)");
        verCatalogoDetallado();

        seccion("2) Padrón de socios");
        gestor.sociosPorId().forEach(s -> System.out.println("  " + s));

        seccion("3) Préstamos exitosos (reglas felices)");
        prestarConMensajeAmable("LIB-001", 1);
        prestarConMensajeAmable("DVD-002", 1);
        prestarConMensajeAmable("LIB-002", 2);
        prestarConMensajeAmable("REV-001", 3); // edición de 2019 → anterior → sí sale

        seccion("4) Doble préstamo rechazado (excepción: ítem no disponible)");
        prestarConMensajeAmable("LIB-001", 2); // ya lo tiene Ana

        seccion("5) Regla polimórfica: la edición del año NO sale de sala");
        prestarConMensajeAmable("REV-002", 2); // figura DISPONIBLE pero su regla lo bloquea

        seccion("6) Código inexistente (excepción: ítem inexistente)");
        prestarConMensajeAmable("XXX-999", 1);

        seccion("7) Estados del enum: el taller bloquea préstamos");
        devolverConMensajeAmable("DVD-002", 1);
        gestor.buscarItemSilencioso("DVD-002").enviarAReparacion();
        System.out.println("  DVD-002 enviado a reparación.");
        prestarConMensajeAmable("DVD-002", 2);
        boolean salioDelTaller = gestor.buscarItemSilencioso("DVD-002").finalizarReparacion();
        System.out.println("  Reparación finalizada (" + salioDelTaller + "). Reintentamos:");
        prestarConMensajeAmable("DVD-002", 2);

        seccion("8) Límite de préstamos alcanzado (excepción: socio en su cupo)");
        prestarConMensajeAmable("LIB-003", 1); // segundo préstamo de Ana: OK
        prestarConMensajeAmable("DVD-001", 1); // tercer préstamo de Ana: OK
        prestarConMensajeAmable("LIB-004", 1); // cuarto intento de Ana: RECHAZADO

        seccion("9) Devoluciones y ciclo completo");
        devolverConMensajeAmable("REV-001", 3);
        devolverConMensajeAmable("LIB-001", 1);
        prestarConMensajeAmable("LIB-001", 3); // re-préstamo tras devolución: OK

        seccion("10) Listado final ordenado por título");
        for (ItemBiblioteca item : gestor.itemsOrdenadosPorTitulo()) {
            System.out.printf("  %-8s %-40s %s%n", item.getCodigo(), item.getTitulo(), item.getEstado());
        }

        seccion("11) Socios al cierre del día");
        gestor.sociosPorId().forEach(s -> System.out.println("  " + s));

        mostrarHistorial();

        titulo("FIN DE LA DEMO — ¡Ahora abrí el modo interactivo!");
    }

    /** Envuelve un préstamo mostrando OK o un mensaje amable si se rechaza. */
    private void prestarConMensajeAmable(String codigo, int idSocio) {
        try {
            gestor.prestar(codigo, idSocio);
            ItemBiblioteca item = gestor.buscarItemSilencioso(codigo);
            Socio socio = gestor.buscarSocioSilencioso(idSocio);
            System.out.printf("  [OK] «%s» se lo lleva %s.%n", item.getTitulo(), socio.getNombre());
        } catch (ExcepcionBiblioteca e) {
            System.out.println("  [RECHAZADO] " + e.getMessage());
        }
    }

    /** Envuelve una devolución con el mismo trato amable. */
    private void devolverConMensajeAmable(String codigo, int idSocio) {
        try {
            gestor.devolver(codigo, idSocio);
            System.out.println("  [OK] Devolución registrada para " + codigo + ".");
        } catch (ExcepcionBiblioteca e) {
            System.out.println("  [RECHAZADO] " + e.getMessage());
        }
    }

    private void mostrarHistorial() {
        seccion("Historial completo de movimientos");
        List<RegistroPrestamo> historial = gestor.getHistorial();
        if (historial.isEmpty()) {
            System.out.println("  (todavía no hubo movimientos)");
            return;
        }
        int n = 1;
        for (RegistroPrestamo registro : historial) {
            System.out.printf("  %2d. %s%n", n++, registro);
        }
    }

    // =====================================================================
    // MODO 2: INTERACTIVO (menú con Scanner)
    // =====================================================================

    private void ejecutarModoInteractivo() {
        System.out.println("=== BIBLIOTECA COMUNITARIA — MODO INTERACTIVO ===");
        System.out.println("(el catálogo arranca sembrado con datos de ejemplo)");
        boolean seguir = true;
        while (seguir) {
            imprimirMenu();
            String opcion = entrada.nextLine().trim();
            switch (opcion) {
                case "1" -> verCatalogoDetallado();
                case "2" -> altaDeLibro();
                case "3" -> inscripcionDeSocio();
                case "4" -> prestamoInteractivo();
                case "5" -> devolucionInteractiva();
                case "6" -> mostrarHistorial();
                case "0" -> {
                    seguir = false;
                    System.out.println("¡Hasta la próxima lectura!");
                }
                default -> System.out.println("Opción inválida: " + opcion);
            }
        }
    }

    private static void imprimirMenu() {
        System.out.println();
        System.out.println("---------- MENÚ ----------");
        System.out.println(" 1) Ver catálogo");
        System.out.println(" 2) Alta de libro");
        System.out.println(" 3) Inscribir socio");
        System.out.println(" 4) Prestar ítem");
        System.out.println(" 5) Devolver ítem");
        System.out.println(" 6) Historial de movimientos");
        System.out.println(" 0) Salir");
        System.out.print("> ");
    }

    private void verCatalogoDetallado() {
        seccion("Catálogo ordenado por título");
        for (ItemBiblioteca item : gestor.itemsOrdenadosPorTitulo()) {
            System.out.println("  " + item.descripcionDetallada());
            System.out.printf("      estado actual: %s (%s)%n",
                    item.getEstado(), item.getEstado().getDescripcion());
        }
    }

    private void altaDeLibro() {
        System.out.print("  Código (ej. LIB-010): ");
        String codigo = entrada.nextLine().trim();
        System.out.print("  Título: ");
        String tituloTexto = entrada.nextLine().trim();
        int anio = leerEntero("  Año de publicación: ");
        System.out.print("  Autor/a: ");
        String autor = entrada.nextLine().trim();
        int paginas = leerEntero("  Cantidad de páginas: ");
        try {
            gestor.agregarItem(new Libro(codigo, tituloTexto, anio,
                    elegirCategoria(), autor, paginas));
            System.out.println("  [OK] Libro incorporado al catálogo.");
        } catch (IllegalArgumentException e) {
            System.out.println("  [RECHAZADO] " + e.getMessage());
        }
    }

    private CategoriaItem elegirCategoria() {
        CategoriaItem[] opciones = CategoriaItem.values();
        System.out.println("  Categorías disponibles:");
        for (int i = 0; i < opciones.length; i++) {
            System.out.printf("    %d) %-12s (%d días máx.)%n",
                    i + 1, opciones[i], opciones[i].getDiasMaximoPrestamo());
        }
        int elegida = leerEntero("  Número de categoría: ");
        if (elegida < 1 || elegida > opciones.length) {
            System.out.println("  Número fuera de rango: uso NOVELA por defecto.");
            return CategoriaItem.NOVELA;
        }
        return opciones[elegida - 1];
    }

    private void inscripcionDeSocio() {
        System.out.print("  Nombre completo: ");
        String nombre = entrada.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("  [RECHAZADO] El nombre no puede quedar vacío.");
            return;
        }
        Socio socioNuevo = gestor.inscribirSocio(nombre);
        System.out.println("  [OK] Inscripto/a: " + socioNuevo);
    }

    private void prestamoInteractivo() {
        System.out.print("  Código del ítem a prestar: ");
        String codigo = entrada.nextLine().trim();
        int idSocio = leerEntero("  Id del socio: ");
        try {
            gestor.prestar(codigo, idSocio);
            System.out.println("  [OK] Préstamo registrado. ¡Buen material elegido!");
        } catch (ExcepcionBiblioteca e) {
            System.out.println("  [RECHAZADO] " + e.getMessage());
        }
    }

    private void devolucionInteractiva() {
        System.out.print("  Código del ítem a devolver: ");
        String codigo = entrada.nextLine().trim();
        int idSocio = leerEntero("  Id del socio que devuelve: ");
        try {
            gestor.devolver(codigo, idSocio);
            System.out.println("  [OK] Devolución registrada.");
        } catch (ExcepcionBiblioteca e) {
            System.out.println("  [RECHAZADO] " + e.getMessage());
        }
    }

    /** Lee un entero reintentando hasta que el usuario escriba algo válido. */
    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(entrada.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Eso no fue un número válido; probá otra vez.");
            }
        }
    }

    // ---------------- utilidades de impresión ----------------

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("=".repeat(78));
        System.out.println(texto);
        System.out.println("=".repeat(78));
    }

    private static void seccion(String texto) {
        System.out.println();
        System.out.println("--- " + texto);
    }
}

// Módulo 22 · Arquitectura en capas — Archivo 4 de 4: PRESENTACIÓN + MAIN.
//
// Cómo ejecutar la demo completa (las 4 clases viven en esta misma carpeta):
//   javac *.java && java ControllerConsolaYMain    <- sin extensión .java
// Compilá TODO junto: cada archivo usa clases de otro. Gotcha: el lanzador
// de fuente única ("java X.java") busca dependencias por nombre de archivo
// (Producto -> Producto.java); acá agrupamos clases por capa, así que no anda.

import java.util.List;
import java.util.Scanner;

// CAPA PRESENTACIÓN: el controlador lee input, DELEGA al servicio y muestra
// resultados. Cero reglas de negocio acá: si un nombre es inválido, el
// servicio avisa lanzando excepción y este controller solo la traduce.
class ControladorConsola {

    private final ServicioProductos servicio;
    private final Scanner scanner = new Scanner(System.in);

    ControladorConsola(ServicioProductos servicio) {
        this.servicio = servicio;
    }

    void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Opción: ");
            procesar(opcion);
        } while (opcion != 0);
        System.out.println("¡Hasta la próxima!");
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("=== CRUD de Productos (por capas) ===");
        System.out.println("1) Alta");
        System.out.println("2) Baja");
        System.out.println("3) Listado");
        System.out.println("4) Buscar por id");
        System.out.println("0) Salir");
    }

    private void procesar(int opcion) {
        try {
            switch (opcion) {
                case 1 -> darDeAlta();
                case 2 -> darDeBaja();
                case 3 -> listar();
                case 4 -> buscarPorId();
                case 0 -> { }
                default -> System.out.println("Opción inexistente.");
            }
        } catch (IllegalArgumentException e) {
            // Las reglas fallaron EN EL SERVICIO; acá solo informamos.
            System.out.println("✗ " + e.getMessage());
        }
    }

    private void darDeAlta() {
        String nombre = leerTexto("Nombre: ");
        double precio = leerDecimal("Precio: ");
        Producto creado = servicio.crear(nombre, precio);   // delega
        ProductoDto dto = ProductoDto.desde(creado);        // entidad -> DTO
        System.out.println("✔ Creado: " + dto.id() + " - " + dto.nombre() + " $" + dto.precio());
    }

    private void darDeBaja() {
        int id = leerEntero("Id a eliminar: ");
        servicio.eliminar(id);
        System.out.println("✔ Eliminado el producto con id " + id + ".");
    }

    private void listar() {
        List<Producto> todos = servicio.listarTodos();
        if (todos.isEmpty()) {
            System.out.println("(sin productos)");
            return;
        }
        for (Producto p : todos) {
            ProductoDto dto = ProductoDto.desde(p);
            System.out.println(dto.id() + " - " + dto.nombre() + " $" + dto.precio());
        }
    }

    private void buscarPorId() {
        int id = leerEntero("Id a buscar: ");
        servicio.buscarPorId(id).ifPresentOrElse(
                p -> System.out.println("Encontrado: " + p),
                () -> System.out.println("No existe un producto con ese id."));
    }

    private String leerTexto(String etiqueta) {
        System.out.print(etiqueta);
        return scanner.nextLine();
    }

    private int leerEntero(String etiqueta) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(etiqueta).trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida: esperaba un número entero.");
            }
        }
    }

    private double leerDecimal(String etiqueta) {
        while (true) {
            try {
                return Double.parseDouble(leerTexto(etiqueta).trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida: esperaba un número decimal.");
            }
        }
    }
}

// El main hace el "cableado" manual de las capas: repo -> servicio ->
// controller. En módulos futuros, un framework hará esto por nosotros.
public class ControllerConsolaYMain {

    public static void main(String[] args) {
        RepositorioProductos repositorio = new RepositorioProductosEnMemoria();
        ServicioProductos servicio = new ServicioProductos(repositorio);
        ControladorConsola controller = new ControladorConsola(servicio);
        controller.iniciar();
    }
}

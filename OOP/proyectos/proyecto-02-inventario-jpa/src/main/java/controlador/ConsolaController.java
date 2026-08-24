package controlador;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import dto.ProductoDto;
import modelo.Categoria;
import servicio.EntidadNoEncontradaException;
import servicio.InventarioService;
import servicio.ValidacionException;

/**
 * Capa de presentación (módulo 22): menú de consola con Scanner. Solo dialoga
 * con el usuario y delega TODA la lógica en InventarioService. Trabaja con
 * DTOs: acá ni se huele a JPA.
 */
public class ConsolaController {

    private static final int OPCION_SALIR = 0;

    private final InventarioService servicio;
    private final Scanner entrada;

    public ConsolaController(InventarioService servicio, Scanner entrada) {
        this.servicio = servicio;
        this.entrada = entrada;
    }

    public void ejecutar() {
        System.out.println("=== Sistema de Inventario ===");
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            try {
                procesar(opcion);
            } catch (ValidacionException | EntidadNoEncontradaException error) {
                System.out.println("[ERROR] " + error.getMessage());
            }
        } while (opcion != OPCION_SALIR);
        System.out.println("Hasta la próxima.");
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("1) Alta de producto");
        System.out.println("2) Listar productos");
        System.out.println("3) Buscar por nombre");
        System.out.println("4) Modificar precio");
        System.out.println("5) Modificar stock");
        System.out.println("6) Eliminar producto");
        System.out.println("7) Reporte: valor total y stock bajo");
        System.out.println("0) Salir");
    }

    private void procesar(int opcion) {
        switch (opcion) {
            case 1 -> darDeAlta();
            case 2 -> listarProductos(servicio.listarTodos());
            case 3 -> buscarPorNombre();
            case 4 -> modificarPrecio();
            case 5 -> modificarStock();
            case 6 -> eliminarProducto();
            case 7 -> mostrarReporte();
            case OPCION_SALIR -> { /* el bucle corta solo */ }
            default -> System.out.println("[ERROR] Opción inválida.");
        }
    }

    // ---------- Opciones del menú ----------

    private void darDeAlta() {
        String nombre = leerTexto("Nombre (mínimo 3 caracteres): ");
        BigDecimal precio = leerDecimal("Precio (> 0): ");
        Integer stock = leerEntero("Stock (>= 0): ");
        Categoria categoria = elegirCategoria();
        ProductoDto creado = servicio.alta(nombre, precio, stock, categoria);
        System.out.println("Alta exitosa -> " + creado);
    }

    private void listarProductos(List<ProductoDto> productos) {
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        System.out.printf("%-6s %-30s %12s %8s  %-40s%n", "ID", "NOMBRE", "PRECIO", "STOCK", "CATEGORÍA");
        for (ProductoDto dto : productos) {
            System.out.printf("%-6d %-30s %12s %8d  %-40s%n",
                    dto.id(), dto.nombre(), dto.precio(), dto.stock(), dto.categoriaDescripcion());
        }
    }

    private void buscarPorNombre() {
        String texto = leerTexto("Nombre a buscar: ");
        Optional<ProductoDto> encontrado = servicio.buscarPorNombre(texto);
        if (encontrado.isPresent()) {
            System.out.println("Encontrado -> " + encontrado.get());
        } else {
            System.out.println("Sin resultados para '" + texto + "'.");
        }
    }

    private void modificarPrecio() {
        Long id = (long) leerEntero("ID del producto: ");
        mostrarActual(id);
        BigDecimal nuevoPrecio = leerDecimal("Nuevo precio (> 0): ");
        ProductoDto actualizado = servicio.modificarPrecio(id, nuevoPrecio);
        System.out.println("Precio actualizado -> " + actualizado);
    }

    private void modificarStock() {
        Long id = (long) leerEntero("ID del producto: ");
        mostrarActual(id);
        Integer nuevoStock = leerEntero("Nuevo stock (>= 0): ");
        ProductoDto actualizado = servicio.modificarStock(id, nuevoStock);
        System.out.println("Stock actualizado -> " + actualizado);
    }

    private void eliminarProducto() {
        Long id = (long) leerEntero("ID del producto a eliminar: ");
        servicio.eliminar(id);
        System.out.println("Producto " + id + " eliminado.");
    }

    private void mostrarReporte() {
        System.out.printf("Valor total del inventario: $%s%n", servicio.valorTotalInventario());
        List<ProductoDto> bajos = servicio.productosConStockBajo();
        if (bajos.isEmpty()) {
            System.out.println("No hay productos con stock bajo (< " + InventarioService.UMBRAL_STOCK_BAJO + ").");
        } else {
            System.out.println("Productos con stock bajo (< " + InventarioService.UMBRAL_STOCK_BAJO + "):");
            listarProductos(bajos);
        }
    }

    private void mostrarActual(Long id) {
        servicio.buscarPorId(id).ifPresentOrElse(
                dto -> System.out.println("Actual -> " + dto),
                () -> System.out.println("(ojo: no existe un producto con ese id, igual se validará)"));
    }

    private Categoria elegirCategoria() {
        Categoria[] categorias = Categoria.values();
        System.out.println("Categorías disponibles:");
        for (int i = 0; i < categorias.length; i++) {
            System.out.printf("  %d) %-12s - %s%n", i + 1, categorias[i].name(), categorias[i].getDescripcion());
        }
        while (true) {
            int eleccion = leerEntero("Número de categoría: ") - 1;
            if (eleccion >= 0 && eleccion < categorias.length) {
                return categorias[eleccion];
            }
            System.out.println("[ERROR] Número fuera de rango, probá de nuevo.");
        }
    }

    // ---------- Lectura robusta por consola ----------

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return entrada.nextLine().trim();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(entrada.nextLine().trim());
            } catch (NumberFormatException error) {
                System.out.println("[ERROR] Esperaba un número entero.");
            }
        }
    }

    private BigDecimal leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String bruto = entrada.nextLine().trim().replace(',', '.');
            try {
                return new BigDecimal(bruto);
            } catch (NumberFormatException error) {
                System.out.println("[ERROR] Esperaba un número decimal (ej.: 1234.50).");
            }
        }
    }
}

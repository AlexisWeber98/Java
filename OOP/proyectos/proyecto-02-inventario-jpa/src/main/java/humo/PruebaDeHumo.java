package humo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import dto.ProductoDto;
import modelo.Categoria;
import servicio.InventarioService;
import util.JpaUtil;

/**
 * Prueba de humo end-to-end (main independiente): recorre alta -> validaciones
 * -> consultas -> modificaciones -> reportes -> baja, verificando conteos
 * esperados. Imprime PASS o FAIL y termina con código de salida 0/1.
 */
public class PruebaDeHumo {

    private int aprobadas;
    private int falladas;

    public static void main(String[] args) {
        boolean ok = ejecutarFlujoCompleto();
        JpaUtil.cerrar();
        System.exit(ok ? 0 : 1);
    }

    /** Reutilizada por AppMain para su demo guiada por defecto. */
    public static boolean ejecutarFlujoCompleto() {
        return new PruebaDeHumo().ejecutar();
    }

    private boolean ejecutar() {
        InventarioService servicio = new InventarioService();
        System.out.println("=== PRUEBA DE HUMO: INVENTARIO JPA ===");

        // [1] Alta: tres productos válidos
        System.out.println("\n[1] Alta de productos");
        ProductoDto teclado = servicio.alta("Teclado mecánico K70",
                new BigDecimal("45999.90"), 12, Categoria.ELECTRONICA);
        System.out.println("  alta -> " + teclado);
        ProductoDto yerba = servicio.alta("Yerba Playadito 1kg",
                new BigDecimal("3200.00"), 3, Categoria.ALIMENTACION);
        System.out.println("  alta -> " + yerba);
        ProductoDto cuaderno = servicio.alta("Cuaderno Rivadavia 100 hojas",
                new BigDecimal("1850.75"), 40, Categoria.OFICINA);
        System.out.println("  alta -> " + cuaderno);

        List<ProductoDto> todos = servicio.listarTodos();
        System.out.println("  listarTodos -> " + todos.size() + " productos");
        verificar(todos.size() == 3, "listarTodos devuelve 3 productos (obtuve " + todos.size() + ")");

        // [2] Reglas de negocio: cada caso debe ser rechazado
        System.out.println("\n[2] Validaciones");
        verificarRechazo(() -> servicio.alta("AB", new BigDecimal("10"), 1, Categoria.GENERAL),
                "nombre menor a 3 caracteres rechazado");
        verificarRechazo(() -> servicio.alta("Producto válido", BigDecimal.ZERO, 1, Categoria.GENERAL),
                "precio igual a cero rechazado");
        verificarRechazo(() -> servicio.alta("Producto válido", new BigDecimal("10"), -1, Categoria.GENERAL),
                "stock negativo rechazado");
        verificarRechazo(() -> servicio.alta("Yerba Playadito 1kg", new BigDecimal("10"), 1, Categoria.ALIMENTACION),
                "duplicado por nombre rechazado");
        verificarRechazo(() -> servicio.modificarPrecio(999L, new BigDecimal("10")),
                "modificación sobre id inexistente rechazada");

        // [3] Consultas
        System.out.println("\n[3] Consultas");
        Optional<ProductoDto> porId = servicio.buscarPorId(teclado.id());
        verificar(porId.isPresent(), "buscarPorId encuentra el producto recién creado");
        Optional<ProductoDto> porNombre = servicio.buscarPorNombre("yerba playadito 1kg");
        verificar(porNombre.isPresent() && porNombre.get().id().equals(yerba.id()),
                "buscarPorNombre es insensible a mayúsculas/minúsculas");

        // [4] Modificaciones
        System.out.println("\n[4] Modificaciones");
        ProductoDto conPrecioNuevo = servicio.modificarPrecio(teclado.id(), new BigDecimal("47999.90"));
        System.out.println("  precio modificado -> " + conPrecioNuevo);
        verificar(new BigDecimal("47999.90").compareTo(conPrecioNuevo.precio()) == 0,
                "modificarPrecio actualiza a 47999.90");
        ProductoDto conStockNuevo = servicio.modificarStock(yerba.id(), 7);
        System.out.println("  stock modificado -> " + conStockNuevo);
        verificar(Integer.valueOf(7).equals(conStockNuevo.stock()), "modificarStock actualiza a 7");

        // [5] Reportes
        System.out.println("\n[5] Reportes");
        BigDecimal conCuaderno = new BigDecimal("47999.90").multiply(new BigDecimal("12"))
                .add(new BigDecimal("3200.00").multiply(new BigDecimal("7")))
                .add(new BigDecimal("1850.75").multiply(new BigDecimal("40"))); // 672428.80
        BigDecimal total = servicio.valorTotalInventario();
        System.out.println("  valor total -> " + total);
        verificar(conCuaderno.compareTo(total) == 0,
                "valorTotalInventario suma " + conCuaderno + " con los 3 productos (obtuve " + total + ")");
        List<ProductoDto> bajos = servicio.productosConStockBajo();
        System.out.println("  stock bajo -> " + bajos.size() + " productos");
        verificar(bajos.isEmpty(), "sin productos con stock bajo luego del ajuste");

        // [6] Baja
        System.out.println("\n[6] Baja");
        servicio.eliminar(cuaderno.id());
        System.out.println("  eliminado -> id " + cuaderno.id());
        verificar(servicio.listarTodos().size() == 2, "eliminar deja 2 productos en el listado");
        verificar(servicio.buscarPorId(cuaderno.id()).isEmpty(),
                "buscarPorId ya no encuentra el producto eliminado");
        verificarRechazo(() -> servicio.eliminar(cuaderno.id()),
                "eliminar dos veces el mismo id rechazado");
        BigDecimal sinCuaderno = new BigDecimal("47999.90").multiply(new BigDecimal("12"))
                .add(new BigDecimal("3200.00").multiply(new BigDecimal("7"))); // 598398.80
        BigDecimal totalPostBaja = servicio.valorTotalInventario();
        verificar(sinCuaderno.compareTo(totalPostBaja) == 0,
                "valorTotalInventario baja a " + sinCuaderno + " tras eliminar (obtuve " + totalPostBaja + ")");

        // Resumen final
        System.out.println();
        System.out.println("=========================================");
        System.out.println("PRUEBA DE HUMO: " + aprobadas + " verificaciones OK, " + falladas + " fallidas");
        System.out.println("RESULTADO FINAL: " + (falladas == 0 ? "PASS" : "FAIL"));
        System.out.println("=========================================");
        return falladas == 0;
    }

    private void verificar(boolean condicion, String descripcion) {
        if (condicion) {
            aprobadas++;
            System.out.println("  OK    - " + descripcion);
        } else {
            falladas++;
            System.out.println("  FALLO - " + descripcion);
        }
    }

    private void verificarRechazo(Runnable accion, String descripcion) {
        try {
            accion.run();
            verificar(false, descripcion + " (no se rechazó)");
        } catch (RuntimeException esperada) {
            verificar(true, descripcion + " [" + esperada.getClass().getSimpleName() + "]");
        }
    }
}

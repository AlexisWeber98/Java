package servicio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import dto.ProductoDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import modelo.Categoria;
import modelo.Producto;
import repositorio.ProductoJpaRepository;
import repositorio.ProductoRepositorio;
import util.JpaUtil;

/**
 * Capa de negocio: valida reglas y es el ÚNICO dueño de las transacciones
 * (begin/commit/rollback, módulos 20-21). Los casos de uso devuelven DTOs,
 * nunca entidades.
 */
public class InventarioService {

    /** Umbral que define cuándo un producto se considera con stock bajo. */
    public static final int UMBRAL_STOCK_BAJO = 5;

    // ---------- Casos de uso (escritura, transaccionales) ----------

    public ProductoDto alta(String nombre, BigDecimal precio, Integer stock, Categoria categoria) {
        String nombreNormalizado = validarNombre(nombre);
        validarPrecio(precio);
        validarStock(stock);
        return enTransaccion(em -> {
            ProductoRepositorio repositorio = new ProductoJpaRepository(em);
            if (repositorio.buscarPorNombre(nombreNormalizado).isPresent()) {
                throw new ValidacionException("Ya existe un producto con el nombre '" + nombreNormalizado + "'.");
            }
            Producto guardado = repositorio.guardar(new Producto(nombreNormalizado, precio, stock, categoria));
            return ProductoDto.desde(guardado);
        });
    }

    public ProductoDto modificarPrecio(Long id, BigDecimal nuevoPrecio) {
        validarPrecio(nuevoPrecio);
        return enTransaccion(em -> {
            Producto producto = obtenerExistente(new ProductoJpaRepository(em), id);
            producto.setPrecio(nuevoPrecio); // dirty checking: flush automático en commit
            return ProductoDto.desde(producto);
        });
    }

    public ProductoDto modificarStock(Long id, Integer nuevoStock) {
        validarStock(nuevoStock);
        return enTransaccion(em -> {
            Producto producto = obtenerExistente(new ProductoJpaRepository(em), id);
            producto.setStock(nuevoStock);
            return ProductoDto.desde(producto);
        });
    }

    public void eliminar(Long id) {
        enTransaccion(em -> {
            ProductoRepositorio repositorio = new ProductoJpaRepository(em);
            repositorio.eliminar(obtenerExistente(repositorio, id));
        });
    }

    // ---------- Casos de uso (lectura, sin transacción explícita) ----------

    public Optional<ProductoDto> buscarPorId(Long id) {
        return sinTransaccion(em ->
                new ProductoJpaRepository(em).buscarPorId(id).map(ProductoDto::desde));
    }

    public Optional<ProductoDto> buscarPorNombre(String nombre) {
        return sinTransaccion(em ->
                new ProductoJpaRepository(em).buscarPorNombre(validarNombre(nombre)).map(ProductoDto::desde));
    }

    public List<ProductoDto> listarTodos() {
        return sinTransaccion(em ->
                new ProductoJpaRepository(em).listarTodos().stream().map(ProductoDto::desde).toList());
    }

    /** Reporte: valor total del inventario (suma precio * stock), JPQL con agregación. */
    public BigDecimal valorTotalInventario() {
        return sinTransaccion(em -> {
            Object bruto = em.createQuery(
                            "SELECT COALESCE(SUM(p.precio * p.stock), 0) FROM Producto p")
                    .getSingleResult();
            return new BigDecimal(bruto.toString()).setScale(2, RoundingMode.HALF_UP);
        });
    }

    /** Reporte: productos cuyo stock quedó por debajo del umbral (< 5). */
    public List<ProductoDto> productosConStockBajo() {
        return sinTransaccion(em ->
                em.createQuery("SELECT p FROM Producto p WHERE p.stock < :umbral ORDER BY p.stock, p.nombre",
                                Producto.class)
                        .setParameter("umbral", UMBRAL_STOCK_BAJO)
                        .getResultList().stream().map(ProductoDto::desde).toList());
    }

    // ---------- Plantillas transaccionales ----------

    private <T> T enTransaccion(Function<EntityManager, T> trabajo) {
        EntityManager em = JpaUtil.em();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T resultado = trabajo.apply(em);
            tx.commit();
            return resultado;
        } catch (RuntimeException error) {
            if (tx.isActive()) {
                tx.rollback(); // nada queda a medias: todo o nada
            }
            throw error;
        } finally {
            em.close();
        }
    }

    private void enTransaccion(Consumer<EntityManager> trabajo) {
        enTransaccion(em -> {
            trabajo.accept(em);
            return null;
        });
    }

    private <T> T sinTransaccion(Function<EntityManager, T> consulta) {
        EntityManager em = JpaUtil.em();
        try {
            return consulta.apply(em);
        } finally {
            em.close();
        }
    }

    // ---------- Reglas y helpers ----------

    private Producto obtenerExistente(ProductoRepositorio repositorio, Long id) {
        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("No existe el producto con id " + id + "."));
    }

    private String validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new ValidacionException("El nombre es obligatorio.");
        }
        String normalizado = nombre.trim();
        if (normalizado.length() < 3) {
            throw new ValidacionException("El nombre debe tener al menos 3 caracteres.");
        }
        return normalizado;
    }

    private void validarPrecio(BigDecimal precio) {
        if (precio == null || precio.signum() <= 0) {
            throw new ValidacionException("El precio debe ser mayor que cero.");
        }
    }

    private void validarStock(Integer stock) {
        if (stock == null || stock < 0) {
            throw new ValidacionException("El stock no puede ser negativo.");
        }
    }
}

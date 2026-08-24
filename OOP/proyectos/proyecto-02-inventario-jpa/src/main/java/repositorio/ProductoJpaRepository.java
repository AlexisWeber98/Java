package repositorio;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import modelo.Producto;

/**
 * Implementación del puerto con JPA (módulo 21). No abre transacciones ni
 * crea EntityManagers: recibe el EntityManager que le presta el servicio,
 * dueño de los límites transaccionales.
 */
public class ProductoJpaRepository implements ProductoRepositorio {

    private final EntityManager em;

    public ProductoJpaRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Producto guardar(Producto producto) {
        if (producto.getId() == null) {
            em.persist(producto);
            return producto;
        }
        return em.merge(producto);
    }

    @Override
    public Optional<Producto> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(Producto.class, id));
    }

    @Override
    public Optional<Producto> buscarPorNombre(String nombre) {
        List<Producto> resultados = em.createQuery(
                        "SELECT p FROM Producto p WHERE LOWER(p.nombre) = LOWER(:nombre)", Producto.class)
                .setParameter("nombre", nombre)
                .getResultList();
        return resultados.stream().findFirst();
    }

    @Override
    public List<Producto> listarTodos() {
        return em.createQuery("SELECT p FROM Producto p ORDER BY p.nombre", Producto.class)
                .getResultList();
    }

    @Override
    public void eliminar(Producto producto) {
        em.remove(em.contains(producto) ? producto : em.merge(producto));
    }
}

package repositorio;

import java.util.List;
import java.util.Optional;

import modelo.Producto;

/**
 * Puerto de persistencia (interfaz, módulo 22): el servicio depende de esta
 * abstracción, no de JPA. En el módulo 23 (hexagonal) esto se convierte en un
 * puerto de salida con su adaptador.
 */
public interface ProductoRepositorio {

    Producto guardar(Producto producto);

    Optional<Producto> buscarPorId(Long id);

    Optional<Producto> buscarPorNombre(String nombre);

    List<Producto> listarTodos();

    void eliminar(Producto producto);
}

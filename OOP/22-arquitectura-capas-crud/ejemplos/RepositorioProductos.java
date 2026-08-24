// Módulo 22 · Arquitectura en capas — Archivo 2 de 4: DATOS (Repositorio).
//
// Cómo ejecutar la demo completa (las 4 clases viven en esta misma carpeta):
//   javac *.java
//   java ControllerConsolaYMain        <- SIN extensión .java
//
// IMPORTANTE 1: compilá TODO junto con "javac *.java", porque cada archivo
// usa clases definidas en otro; compilar uno solo falla.
//
// IMPORTANTE 2 (gotcha): "java ControllerConsolaYMain.java" NO anda acá.
// El lanzador de fuente única resuelve dependencias buscando archivos que
// se llamen igual que la clase (Producto -> Producto.java), pero nuestros
// archivos agrupan varias clases con nombres temáticos por capa. Por eso:
// primero javac, después ejecutá el bytecode sin extensión.

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// CAPA DATOS: la INTERFAZ es el contrato. El servicio depende de esto,
// nunca de una implementación concreta (misma idea que Repositorio<T>
// del módulo 17). Cambiar de HashMap a MySQL no rompe a nadie arriba.
interface RepositorioProductos {

    Producto guardar(Producto producto);   // asigna id si es nuevo

    Optional<Producto> buscarPorId(int id);

    boolean existeNombre(String nombre);   // para validar duplicados

    boolean eliminarPorId(int id);

    List<Producto> listarTodos();
}

// Implementación EN MEMORIA: un HashMap como "base de datos" provisoria.
// Fijate que acá NO hay reglas de negocio ni impresiones: solo persistir.
class RepositorioProductosEnMemoria implements RepositorioProductos {

    private final Map<Integer, Producto> productos = new HashMap<>();
    private int proximoId = 1;

    @Override
    public Producto guardar(Producto producto) {
        Producto guardado;
        if (producto.getId() == 0) {
            // Nuevo: le asignamos el próximo id disponible.
            guardado = new Producto(proximoId++, producto.getNombre(), producto.getPrecio());
        } else {
            // Existente: reemplaza lo que había bajo ese id.
            guardado = producto;
        }
        productos.put(guardado.getId(), guardado);
        return guardado;
    }

    @Override
    public Optional<Producto> buscarPorId(int id) {
        return Optional.ofNullable(productos.get(id));
    }

    @Override
    public boolean existeNombre(String nombre) {
        for (Producto p : productos.values()) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminarPorId(int id) {
        return productos.remove(id) != null;
    }

    @Override
    public List<Producto> listarTodos() {
        // Copia defensiva: nadie de afuera muta nuestro mapa.
        return List.copyOf(productos.values());
    }
}

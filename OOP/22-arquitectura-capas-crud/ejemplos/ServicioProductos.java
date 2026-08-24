// Módulo 22 · Arquitectura en capas — Archivo 3 de 4: LÓGICA (Servicio).
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

import java.util.List;
import java.util.Optional;

// CAPA LÓGICA: acá viven las REGLAS DE NEGOCIO. El servicio valida, decide
// y orquesta; delega la persistencia al repositorio. Fijate que NO conoce
// Scanner ni System.out: mañana la UI puede ser web y esto no se toca.
class ServicioProductos {

    private final RepositorioProductos repositorio;

    // Inyección por constructor: el servicio depende de la INTERFAZ,
    // no de la implementación concreta.
    ServicioProductos(RepositorioProductos repositorio) {
        this.repositorio = repositorio;
    }

    // Regla: crear un producto exige nombre válido, precio positivo y
    // que no exista otro con el mismo nombre. Si algo falla -> excepción
    // con mensaje claro (la traduce quien llama, sin exponer detalles).
    Producto crear(String nombre, double precio) {
        validarNombre(nombre);
        validarPrecio(precio);
        if (repositorio.existeNombre(nombre)) {
            throw new IllegalArgumentException("Ya existe un producto con el nombre '" + nombre + "'.");
        }
        return repositorio.guardar(new Producto(0, nombre.trim(), precio));
    }

    // Regla: solo se elimina lo que existe. El id 0 o inexistente es error.
    void eliminar(int id) {
        if (!repositorio.eliminarPorId(id)) {
            throw new IllegalArgumentException("No existe un producto con el id " + id + ".");
        }
    }

    Optional<Producto> buscarPorId(int id) {
        return repositorio.buscarPorId(id);
    }

    List<Producto> listarTodos() {
        return repositorio.listarTodos();
    }

    // ---- Reglas privadas: el negocio vive acá, en UN solo lugar ----

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
    }

    private void validarPrecio(double precio) {
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero.");
        }
    }
}

/*
 * RepositorioEnMemoria.java — Proyecto Integrador N°1: Biblioteca
 * Implementación genérica del repositorio con HashMap como backend, más un
 * contador autoincremental para altas rápidas.
 *
 * La gracia del contrato: mañana podríamos escribir RepositorioEnArchivo<T,K>
 * o RepositorioEnBaseDatos<T,K> y el GestorBiblioteca seguiría compilando
 * igual, porque depende de la INTERFAZ, no de esta clase.
 *
 * Compilación y ejecución: javac *.java && java Main
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositorioEnMemoria<T, K> implements RepositorioGenerico<T, K> {

    private final Map<K, T> datos = new HashMap<>();

    /** Contador interno que entrega claves numéricas secuenciales. */
    private int contadorIds = 0;

    @Override
    public int generarProximoId() {
        return ++contadorIds;
    }

    @Override
    public void guardar(K clave, T entidad) {
        datos.put(clave, entidad);
    }

    @Override
    public T buscarPorId(K clave) {
        return datos.get(clave);
    }

    @Override
    public List<T> listarTodos() {
        return new ArrayList<>(datos.values());
    }

    @Override
    public boolean eliminar(K clave) {
        return datos.remove(clave) != null;
    }

    @Override
    public boolean existe(K clave) {
        return datos.containsKey(clave);
    }

    @Override
    public int cantidad() {
        return datos.size();
    }
}

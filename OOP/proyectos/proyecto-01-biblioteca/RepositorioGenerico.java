/*
 * RepositorioGenerico.java — Proyecto Integrador N°1: Biblioteca
 * Contrato genérico de almacenamiento: T es el tipo guardado, K el tipo de
 * clave. Una sola abstracción sirve para ítems (clave String) y para socios
 * (clave Integer): ahí se ve el poder real de los genéricos.
 *
 * Compilación y ejecución: javac *.java && java Main
 */
import java.util.List;

public interface RepositorioGenerico<T, K> {

    /** Próximo número secuencial para altas automáticas (contador interno). */
    int generarProximoId();

    /** Inserta o reemplaza la entidad asociada a esa clave. */
    void guardar(K clave, T entidad);

    /** Recupera la entidad, o null si la clave no existe. */
    T buscarPorId(K clave);

    /** Copia con TODAS las entidades vivas (sin orden garantizado). */
    List<T> listarTodos();

    /** Elimina por clave; devuelve true si realmente borró algo. */
    boolean eliminar(K clave);

    /** ¿Hay algo guardado bajo esa clave? */
    boolean existe(K clave);

    /** Cuántas entidades contiene ahora mismo. */
    int cantidad();
}

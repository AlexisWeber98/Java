/*
 * ============================================================================
 *  Ejercicio 5 — DESAFÍO: Repositorio<T, ID> genérico en memoria
 * ============================================================================
 *
 *  ENUNCIADO
 *  --------
 *  Vas a construir una mini capa de persistencia con tipos genéricos, como
 *  las que usan los frameworks reales. Tres piezas:
 *
 *  1) La interfaz del contrato:
 *
 *         interface Repositorio<T, ID> {
 *             ID guardar(T elemento);      // devuelve el id asignado
 *             T buscarPorId(ID id);        // null si no existe
 *             java.util.List<T> listarTodos();
 *             boolean eliminar(ID id);     // true si había algo que borrar
 *         }
 *
 *  2) RepositorioEnMemoria<T, ID> implements Repositorio<T, ID>:
 *     backend con HashMap (mejor LinkedHashMap: conserva orden de inserción)
 *     y el TRUCO del contador: el constructor recibe un
 *     java.util.function.IntFunction<ID> generadorDeIds; cada guardar hace
 *     generadorDeIds.apply(++secuencia) para fabricar ids autoincrementales.
 *
 *  3) Sesión CRUD completa en el main, dos veces:
 *      - Producto con id Integer   → generador n -> n
 *      - Cliente  con id String    → generador n -> String.format("CLI-%03d", n)
 *
 *  REQUISITOS
 *  ----------
 *      - El main NUNCA toca el mapa directamente: todo pasa por la interfaz.
 *      - Mostrá: guardar (con ids asignados), listarTodos, buscarPorId de un
 *        id existente Y de uno inexistente, eliminar, y volver a listar.
 *      - Producto y Cliente van como clases estáticas acá adentro.
 *
 *  PISTAS
 *      ------
 *      - IntFunction<R> tiene apply(int) y devuelve R: es tu fábrica de ids.
 *      - remove(id) del mapa te devuelve el valor removido (o null): úsalo.
 *      - Una implementación puede ACHICAR los límites de los parámetros...
 *        pero hoy no hace falta: <T, ID> pelados alcanzan.
 *      - Guardá este patrón en la cabeza: lo vas a REENCONTRAR tal cual en
 *        el Módulo 22, cuando la implementación hable con base de datos.
 * ============================================================================
 */
public class Ejercicio5DesafioRepositorioGenerico {

    // TODO 1: declará la interfaz Repositorio<T, ID> con sus cuatro métodos.


    // TODO 2: implementá RepositorioEnMemoria<T, ID> con LinkedHashMap,
    //         el IntFunction<ID> inyectado por constructor y el contador.


    // TODO 3: creá las clases estáticas Producto(nombre, precio) y
    //         Cliente(nombre, email), ambas con toString() lindo.


    public static void main(String[] args) {
        // TODO 4: sesión CRUD completa contra repositorio de Productos.


        // TODO 5: repetí la experiencia con Clientes e id String.


        System.out.println("Desafío pendiente: construí tu repositorio y corré la sesión CRUD.");
    }
}

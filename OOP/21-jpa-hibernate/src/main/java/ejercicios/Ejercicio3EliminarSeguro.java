package ejercicios;

/**
 * Ejercicio 3 · Eliminar de forma segura.
 * Persistí un producto temporal, borralo por id y verificá que ya no está con una consulta count.
 */
public class Ejercicio3EliminarSeguro {

    public static void main(String[] args) {
        // TODO 1: persistir un producto temporal y confirmarlo para obtener su id
        // TODO 2: en otra transacción, buscarlo con find() por su id y pasarlo por em.remove()
        // TODO 3: hacer commit del borrado
        // TODO 4: con un EntityManager nuevo, correr:
        //         select count(p) from Producto p where p.nombre = :nombre
        //         e imprimir que el resultado es 0

        System.out.println("Ejercicio 3 sin resolver todavía.");
    }
}

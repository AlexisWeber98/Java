package ejercicios;

/**
 * Ejercicio 1 · Persistir y encontrar.
 * Guardá un producto, imprimí el id que le asignó la base
 * y después recuperalo con find() usando ese mismo id.
 */
public class Ejercicio1App {

    public static void main(String[] args) {
        // TODO 1: crear el EntityManagerFactory con la unidad "demoPU" (una sola vez)
        // TODO 2: abrir un EntityManager y arrancar una transacción (getTransaction().begin())
        // TODO 3: crear un Producto y pasarlo por em.persist(); imprimir producto.getId()
        // TODO 4: recuperarlo con em.find(Producto.class, id) e imprimirlo
        // TODO 5: commit; cerrar EntityManager y EntityManagerFactory en el finally

        System.out.println("Ejercicio 1 sin resolver todavía.");
    }
}

package ejercicios;

/**
 * Ejercicio 1 · Persistir y encontrar.
 * Guardá dos productos, imprimí los ids que les asignó la base
 * y después recuperalos con find() usando esos mismos ids.
 */
public class Ejercicio1PersistirYEncontrar {

    public static void main(String[] args) {
        // TODO 1: crear el EntityManagerFactory con la unidad "demoPU" (una sola vez)
        // TODO 2: abrir un EntityManager y arrancar una transacción (getTransaction().begin())
        // TODO 3: crear dos Productos y pasarlos por em.persist(); imprimir producto.getId()
        // TODO 4: recuperarlos con em.find(Producto.class, id) e imprimirlos
        // TODO 5: commit; cerrar EntityManager y EntityManagerFactory en el finally

        System.out.println("Ejercicio 1 sin resolver todavía.");
    }
}

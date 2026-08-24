package soluciones;

import java.math.BigDecimal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import modelo.Producto;

/**
 * Solución Ejercicio 1 · Persistir y encontrar.
 * Corre: java -cp "target/classes:$(cat /tmp/cp21.txt)" soluciones.Solucion1App
 */
public class Solucion1App {

    public static void main(String[] args) {
        // La Factory: UNA vez por aplicación.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demoPU");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // ALTA: con IDENTITY, el INSERT sale acá mismo y el id queda asignado ya.
            Producto auriculares = new Producto("Auriculares BT", new BigDecimal("15999.90"));
            em.persist(auriculares);
            System.out.println("[PERSIST] id generado -> " + auriculares.getId());

            // FIND: búsqueda por clave primaria. Sale del persistence context.
            Producto encontrado = em.find(Producto.class, auriculares.getId());
            System.out.println("[FIND] " + encontrado);

            tx.commit();
            System.out.println("[COMMIT] listo");
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }
}

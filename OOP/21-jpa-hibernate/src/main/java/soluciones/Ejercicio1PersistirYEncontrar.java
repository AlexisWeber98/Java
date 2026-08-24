package soluciones;

import java.math.BigDecimal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import modelo.Producto;

/**
 * Solución Ejercicio 1 · Persistir y encontrar.
 * Corre: java -cp "target/classes:$(cat /tmp/cp21b.txt)" soluciones.Ejercicio1PersistirYEncontrar
 */
public class Ejercicio1PersistirYEncontrar {

    public static void main(String[] args) {
        // La Factory se crea UNA sola vez por aplicación (es carísima de crear).
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demoPU");

        // Un EntityManager por unidad de trabajo: liviano, corto, desechable.
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // ALTA: con IDENTITY el INSERT sale en cada persist() y el id queda asignado.
            Producto teclado = new Producto("Teclado mecánico", new BigDecimal("25990.00"));
            Producto mouse   = new Producto("Mouse inalámbrico", new BigDecimal("8450.50"));
            em.persist(teclado);
            em.persist(mouse);
            System.out.println("[PERSIST] ids asignados -> teclado=" + teclado.getId()
                    + ", mouse=" + mouse.getId());

            // FIND por clave primaria: sale del persistence context, sin ir a la base de nuevo.
            Producto encontradoTeclado = em.find(Producto.class, teclado.getId());
            Producto encontradoMouse   = em.find(Producto.class, mouse.getId());
            System.out.println("[FIND] id=" + teclado.getId() + " -> " + encontradoTeclado);
            System.out.println("[FIND] id=" + mouse.getId()   + " -> " + encontradoMouse);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }
}

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import modelo.Producto;

/**
 * Demo completa del ciclo CRUD con JPA/Hibernate sobre H2.
 * Corre así (desde la carpeta del módulo):
 *   export JAVA_HOME=/usr/lib/jvm/java-25-openjdk
 *   mvn -q compile && mvn -q dependency:build-classpath -Dmdep.outputFile=/tmp/cp21.txt
 *   java -cp "target/classes:$(cat /tmp/cp21.txt)" Main
 */
public class Main {

    public static void main(String[] args) {
        // 0. La Factory se crea UNA sola vez por aplicación (es carísima de crear).
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demoPU");

        // 1. Un EntityManager por unidad de trabajo: liviano, corto, desechable.
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // 2. ALTA: persistir tres productos. Con IDENTITY el INSERT sale ya en el persist().
            Producto teclado = new Producto("Teclado mecánico", new BigDecimal("25990.00"));
            Producto mouse   = new Producto("Mouse inalámbrico", new BigDecimal("8450.50"));
            Producto monitor = new Producto("Monitor 24\"", new BigDecimal("189900.00"));
            em.persist(teclado);
            em.persist(mouse);
            em.persist(monitor);
            System.out.println("\n[PERSIST] Guardados -> " + teclado + " | " + mouse + " | " + monitor);

            // 3. FIND: buscar por clave primaria. Sale del persistence context: misma instancia.
            Producto encontrado = em.find(Producto.class, teclado.getId());
            System.out.println("\n[FIND] id=" + teclado.getId() + " -> " + encontrado);

            // 4. JPQL: consultamos OBJETOS (entidad Producto), no tablas.
            List<Producto> todos = em
                    .createQuery("select p from Producto p order by p.nombre", Producto.class)
                    .getResultList();
            System.out.println("\n[JPQL] Todos los productos:");
            todos.forEach(p -> System.out.println("   " + p));

            // 5. UPDATE sin llamar a ningún update(): cambio el campo, Hibernate detecta el cambio
            //    (dirty checking) y genera el UPDATE solo al hacer commit.
            encontrado.setPrecio(new BigDecimal("27990.00"));
            System.out.println("\n[DIRTY CHECKING] Precio modificado en memoria -> " + encontrado);

            // 6. BAJA: eliminar un producto gestionado.
            em.remove(monitor);
            System.out.println("\n[REMOVE] Eliminado -> " + monitor);

            tx.commit();
            System.out.println("\n[COMMIT] Transacción confirmada: UPDATE y DELETE salieron acá.");
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.err.println("\n[ROLLBACK] Algo falló, nada quedó a medias: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }

        // 7. Otro EntityManager ve el estado FINAL confirmado en la base.
        EntityManager em2 = emf.createEntityManager();
        try {
            List<Producto> finales = em2
                    .createQuery("select p from Producto p order by p.id", Producto.class)
                    .getResultList();
            System.out.println("\n[ESTADO FINAL] Lo que realmente quedó en la base:");
            finales.forEach(p -> System.out.println("   " + p));
        } finally {
            em2.close();
            emf.close();
        }
    }
}

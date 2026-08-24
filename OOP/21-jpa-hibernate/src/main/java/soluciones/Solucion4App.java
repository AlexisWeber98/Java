package soluciones;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import modelo.Producto;

/**
 * Solución Ejercicio 4 · Consultas JPQL con filtro.
 * Corre: java -cp "target/classes:$(cat /tmp/cp21.txt)" soluciones.Solucion4App
 */
public class Solucion4App {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demoPU");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            em.persist(new Producto("Cable HDMI", new BigDecimal("4200.00")));
            em.persist(new Producto("Silla gamer", new BigDecimal("245000.00")));
            em.persist(new Producto("Pad mouse XL", new BigDecimal("5300.00")));
            em.persist(new Producto("Micrófono USB", new BigDecimal("48900.00")));

            // JPQL piensa en OBJETOS y atributos, no en tablas ni columnas.
            List<Producto> caros = em.createQuery(
                            "select p from Producto p where p.precio > :precioMinimo order by p.precio desc",
                            Producto.class)
                    .setParameter("precioMinimo", new BigDecimal("10000"))
                    .getResultList();

            System.out.println("[JPQL] Productos con precio > 10000, del más caro al más barato:");
            caros.forEach(p -> System.out.println("   " + p));

            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }
}

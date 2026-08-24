package soluciones;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import modelo.Producto;

/**
 * Solución Ejercicio 4 · Consultas JPQL con filtro y parámetro con nombre.
 * Corre: java -cp "target/classes:$(cat /tmp/cp21b.txt)" soluciones.Ejercicio4ConsultasJPQLFiltro
 */
public class Ejercicio4ConsultasJPQLFiltro {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demoPU");

        // Semilla: cuatro productos con precios bien distintos.
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(new Producto("Yerba mate 1kg", new BigDecimal("4500.00")));
            em.persist(new Producto("Café especial",   new BigDecimal("12500.00")));
            em.persist(new Producto("Auriculares BT",  new BigDecimal("32000.00")));
            em.persist(new Producto("Monitor 24\"",    new BigDecimal("189900.00")));
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }

        // Consulta: JPQL habla de ENTIDADES (Producto), no de tablas.
        // El filtro usa un parámetro con nombre (:minimo), nunca concatenar valores a mano.
        BigDecimal minimo = new BigDecimal("10000");
        EntityManager emConsulta = emf.createEntityManager();
        try {
            List<Producto> caros = emConsulta
                    .createQuery(
                            "select p from Producto p where p.precio > :minimo order by p.precio desc",
                            Producto.class)
                    .setParameter("minimo", minimo)
                    .getResultList();

            System.out.println("\n[JPQL] Productos con precio > " + minimo
                    + ", ordenados por precio descendente:");
            caros.forEach(p -> System.out.println("   " + p));
        } finally {
            emConsulta.close();
            emf.close();
        }
    }
}

package soluciones;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import modelo.Producto;

/**
 * Solución Ejercicio 5 · Desafío integrador: alta-baja-modificación + reporte final.
 * Corre: java -cp "target/classes:$(cat /tmp/cp21.txt)" soluciones.Solucion5App
 */
public class Solucion5App {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demoPU");

        Long idABorrar = null;
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // ALTA
            Producto disco   = new Producto("Disco SSD 1TB", new BigDecimal("45000.00"));
            Producto memoria = new Producto("Memoria RAM 16GB", new BigDecimal("38000.00"));
            Producto fuente  = new Producto("Fuente 650W", new BigDecimal("52000.00"));
            em.persist(disco);
            em.persist(memoria);
            em.persist(fuente);
            System.out.println("[ALTA] tres productos persistidos");

            // BAJA
            idABorrar = fuente.getId();
            em.remove(em.find(Producto.class, idABorrar));
            System.out.println("[BAJA] eliminado id=" + idABorrar);

            // MODIFICACIÓN (dirty checking: solo cambio el campo)
            memoria.setPrecio(new BigDecimal("35500.00"));
            System.out.println("[MODIFICACIÓN] nuevo precio de '" + memoria.getNombre() + "'");

            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }

        // REPORTE FINAL desde un contexto fresco: lo que REALMENTE quedó en la base.
        EntityManager emReporte = emf.createEntityManager();
        try {
            Long total = emReporte
                    .createQuery("select count(p) from Producto p", Long.class)
                    .getSingleResult();
            System.out.println("\n[REPORTE] Total de productos: " + total);

            List<Producto> restantes = emReporte
                    .createQuery("select p from Producto p order by p.nombre", Producto.class)
                    .getResultList();
            System.out.println("[REPORTE] Inventario final:");
            restantes.forEach(p -> System.out.println("   " + p));
        } finally {
            emReporte.close();
            emf.close();
        }
    }
}

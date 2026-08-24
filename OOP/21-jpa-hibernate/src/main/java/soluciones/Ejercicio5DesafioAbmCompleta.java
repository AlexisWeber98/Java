package soluciones;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import modelo.Producto;

/**
 * Solución Ejercicio 5 · Desafío integrador: ABM completa + reporte final.
 * Corre: java -cp "target/classes:$(cat /tmp/cp21b.txt)" soluciones.Ejercicio5DesafioAbmCompleta
 */
public class Ejercicio5DesafioAbmCompleta {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demoPU");

        Long idABajar;
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // ALTA: dos productos.
            Producto notebook = new Producto("Notebook 14\"", new BigDecimal("899999.00"));
            Producto webcam   = new Producto("Webcam Full HD", new BigDecimal("28500.00"));
            em.persist(notebook);
            em.persist(webcam);
            System.out.println("[ALTA] ids -> notebook=" + notebook.getId() + ", webcam=" + webcam.getId());

            // MODIFICACIÓN: solo cambio el campo; el UPDATE lo genera Hibernate en el commit.
            notebook.setPrecio(new BigDecimal("849999.00"));
            System.out.println("[MODIFICACIÓN] nuevo precio de '" + notebook.getNombre() + "'");

            // BAJA: find + remove sobre el otro.
            idABajar = webcam.getId();
            em.remove(em.find(Producto.class, idABajar));
            System.out.println("[BAJA] eliminado id=" + idABajar);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }

        // REPORTE FINAL desde un contexto fresco: lo que REALMENTE quedó confirmado.
        EntityManager emReporte = emf.createEntityManager();
        try {
            List<Producto> restantes = emReporte
                    .createQuery("select p from Producto p order by p.nombre", Producto.class)
                    .getResultList();

            BigDecimal valorInventario = restantes.stream()
                    .map(Producto::getPrecio)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            System.out.println("\n[REPORTE] Inventario final:");
            restantes.forEach(p -> System.out.println("   " + p));
            System.out.println("[REPORTE] Valor total del inventario: $" + valorInventario);
        } finally {
            emReporte.close();
            emf.close();
        }
    }
}

package soluciones;

import java.math.BigDecimal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import modelo.Producto;

/**
 * Solución Ejercicio 3 · Eliminar seguro.
 * Corre: java -cp "target/classes:$(cat /tmp/cp21.txt)" soluciones.Solucion3App
 */
public class Solucion3App {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demoPU");

        Long idCreado;
        EntityManager emAlta = emf.createEntityManager();
        EntityTransaction txAlta = emAlta.getTransaction();
        try {
            txAlta.begin();
            Producto parlante = new Producto("Parlante Bluetooth", new BigDecimal("12750.00"));
            emAlta.persist(parlante);
            txAlta.commit();
            idCreado = parlante.getId();
        } finally {
            emAlta.close();
        }

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Caso feliz: existe -> se elimina.
            Producto victima = em.find(Producto.class, idCreado);
            if (victima != null) {
                em.remove(victima);
                System.out.println("[REMOVE] eliminado -> " + victima);
            } else {
                System.out.println("[INFO] el producto no existe, nada para borrar");
            }

            // Caso borde: id inexistente. find() devuelve null, NO lanza excepción:
            // por eso hay que chequear antes de remove().
            Producto fantasma = em.find(Producto.class, 999L);
            if (fantasma == null) {
                System.out.println("[BORDE] id=999 no existe: find() devolvió null, sin explosión");
            } else {
                em.remove(fantasma);
            }

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

package soluciones;

import java.math.BigDecimal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import modelo.Producto;

/**
 * Solución Ejercicio 2 · Actualizar con dirty checking.
 * Corre: java -cp "target/classes:$(cat /tmp/cp21.txt)" soluciones.Solucion2App
 */
public class Solucion2App {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demoPU");

        // Paso 0: dato de partida.
        Long idCreado;
        EntityManager emAlta = emf.createEntityManager();
        EntityTransaction txAlta = emAlta.getTransaction();
        try {
            txAlta.begin();
            Producto base = new Producto("Webcam HD", new BigDecimal("32000.00"));
            emAlta.persist(base);
            txAlta.commit();
            idCreado = base.getId();
        } finally {
            emAlta.close();
        }

        // Paso 1: modificar SIN llamar a ningún update. Solo cambio el campo.
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Producto webcam = em.find(Producto.class, idCreado);
            webcam.setPrecio(new BigDecimal("29999.99"));
            System.out.println("[EN MEMORIA] " + webcam + " (todavía no hubo UPDATE...)");
            tx.commit(); // <-- acá Hibernate compara el estado actual vs. original y genera el UPDATE solo
            System.out.println("[COMMIT] dirty checking hizo el UPDATE sin que lo pidamos");
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }

        // Paso 2: prueba de fuego: OTRO persistence context lee directo de la base.
        EntityManager emVerif = emf.createEntityManager();
        try {
            Producto releido = emVerif.find(Producto.class, idCreado);
            System.out.println("[VERIFICACIÓN] desde otra sesión -> " + releido);
        } finally {
            emVerif.close();
            emf.close();
        }
    }
}

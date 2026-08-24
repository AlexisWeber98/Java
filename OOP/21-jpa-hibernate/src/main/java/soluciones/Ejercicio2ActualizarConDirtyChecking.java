package soluciones;

import java.math.BigDecimal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import modelo.Producto;

/**
 * Solución Ejercicio 2 · Actualizar mediante dirty checking.
 * Corre: java -cp "target/classes:$(cat /tmp/cp21b.txt)" soluciones.Ejercicio2ActualizarConDirtyChecking
 */
public class Ejercicio2ActualizarConDirtyChecking {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demoPU");

        // PASO 1: semilla inicial confirmada, para tener un estado conocido en la base.
        Long idAuriculares;
        EntityManager emAlta = emf.createEntityManager();
        EntityTransaction txAlta = emAlta.getTransaction();
        try {
            txAlta.begin();
            Producto auriculares = new Producto("Auriculares Bluetooth", new BigDecimal("19999.99"));
            emAlta.persist(auriculares);
            txAlta.commit();
            idAuriculares = auriculares.getId();
            System.out.println("[SEED] guardado id=" + idAuriculares + " precio=19999.99");
        } catch (RuntimeException e) {
            if (txAlta.isActive()) txAlta.rollback();
            throw e;
        } finally {
            emAlta.close();
        }

        // PASO 2: buscar, mutar SOLO el campo y commit. No hay update(): el dirty checking
        // compara el estado al cerrar el contexto y genera el UPDATE solo.
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Producto auriculares = em.find(Producto.class, idAuriculares);
            System.out.println("[ANTES]  " + auriculares);
            auriculares.setPrecio(new BigDecimal("17499.99"));
            System.out.println("[MEMORIA] cambié el precio sin guardar nada todavía: " + auriculares);
            tx.commit();
            System.out.println("[COMMIT] acá salió el UPDATE real a la base.");
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }

        // PASO 3: otro EntityManager ve lo realmente confirmado.
        EntityManager emVerificacion = emf.createEntityManager();
        try {
            Producto releido = emVerificacion.find(Producto.class, idAuriculares);
            System.out.println("[RE-FIND] id=" + idAuriculares + " -> " + releido);
        } finally {
            emVerificacion.close();
            emf.close();
        }
    }
}

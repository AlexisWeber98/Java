package soluciones;

import java.math.BigDecimal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import modelo.Producto;

/**
 * Solución Ejercicio 3 · Eliminar de forma segura.
 * Corre: java -cp "target/classes:$(cat /tmp/cp21b.txt)" soluciones.Ejercicio3EliminarSeguro
 */
public class Ejercicio3EliminarSeguro {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demoPU");

        final String nombreTemporal = "Producto temporal";

        // PASO 1: alta del temporal, confirmada, para obtener su id.
        Long idTemporal;
        EntityManager emAlta = emf.createEntityManager();
        EntityTransaction txAlta = emAlta.getTransaction();
        try {
            txAlta.begin();
            Producto temporal = new Producto(nombreTemporal, new java.math.BigDecimal("1.00"));
            emAlta.persist(temporal);
            txAlta.commit();
            idTemporal = temporal.getId();
            System.out.println("[SEED] temporal guardado con id=" + idTemporal);
        } catch (RuntimeException e) {
            if (txAlta.isActive()) txAlta.rollback();
            throw e;
        } finally {
            emAlta.close();
        }

        // PASO 2: baja por id: find para traerlo gestionado y remove sobre esa instancia.
        EntityManager emBaja = emf.createEntityManager();
        EntityTransaction txBaja = emBaja.getTransaction();
        try {
            txBaja.begin();
            Producto temporal = emBaja.find(Producto.class, idTemporal);
            emBaja.remove(temporal);
            txBaja.commit();
            System.out.println("[REMOVE] eliminado id=" + idTemporal);
        } catch (RuntimeException e) {
            if (txBaja.isActive()) txBaja.rollback();
            throw e;
        } finally {
            emBaja.close();
        }

        // PASO 3: verificación desde un contexto fresco con count().
        EntityManager emVerificacion = emf.createEntityManager();
        try {
            Long restantes = emVerificacion
                    .createQuery("select count(p) from Producto p where p.nombre = :nombre", Long.class)
                    .setParameter("nombre", nombreTemporal)
                    .getSingleResult();
            System.out.println("[VERIFICACIÓN] quedan " + restantes
                    + " producto(s) llamado(s) '" + nombreTemporal + "' (esperado: 0)");
        } finally {
            emVerificacion.close();
            emf.close();
        }
    }
}

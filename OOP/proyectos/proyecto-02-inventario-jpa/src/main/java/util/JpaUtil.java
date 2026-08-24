package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Singleton del EntityManagerFactory (módulo 21): crearlo es caro, así que
 * hay UNO por aplicación. Los EntityManager, en cambio, son baratos y de
 * vida corta: uno por operación.
 */
public final class JpaUtil {

    private static final String UNIDAD_PERSISTENCIA = "inventarioPU";
    private static volatile EntityManagerFactory fabrica;

    private JpaUtil() {
    }

    public static EntityManagerFactory emf() {
        if (fabrica == null) {
            synchronized (JpaUtil.class) {
                if (fabrica == null) { // double-checked locking
                    fabrica = Persistence.createEntityManagerFactory(UNIDAD_PERSISTENCIA);
                }
            }
        }
        return fabrica;
    }

    public static EntityManager em() {
        return emf().createEntityManager();
    }

    /** Cierre ordenado al terminar la aplicación. */
    public static void cerrar() {
        if (fabrica != null) {
            fabrica.close();
            fabrica = null;
        }
    }
}

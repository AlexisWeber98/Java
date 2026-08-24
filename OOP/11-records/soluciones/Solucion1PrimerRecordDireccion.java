/*
 * ============================================================================
 * Módulo 11 - Records | Solución 1: Tu primer record: Dirección
 * ============================================================================
 * Idea clave: equals compara valores componente a componente; == compara
 * referencias. Dos news con el mismo contenido son objetos distintos pero
 * "iguales" según el contrato del record. El toString se genera solo.
 */
public class Solucion1PrimerRecordDireccion {

    public static void main(String[] args) {
        Direccion casa = new Direccion("Av. Siempre Viva 742", "Springfield", "B1234ABC");
        Direccion mismaCasa = new Direccion("Av. Siempre Viva 742", "Springfield", "B1234ABC");

        System.out.println("casa      -> " + casa);
        System.out.println("mismaCasa -> " + mismaCasa);

        // equals compara calle, ciudad y codigoPostal: mismo contenido => true.
        System.out.println("\n¿casa.equals(mismaCasa)? " + casa.equals(mismaCasa));

        // == compara si apuntan al MISMO objeto en memoria: dos new => false.
        System.out.println("¿casa == mismaCasa?      " + (casa == mismaCasa));

        // Bonus coherente: si equals da true, hashCode debe coincidir.
        System.out.println("¿hashCode() iguales?     " + (casa.hashCode() == mismaCasa.hashCode()));
    }

    // Tipo anidado para que cada archivo sea autocontenido; también puede ir
    // en su propio archivo Direccion.java sin modificador de acceso.
    record Direccion(String calle, String ciudad, String codigoPostal) {
    }
}

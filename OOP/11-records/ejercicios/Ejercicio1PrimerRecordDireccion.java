/*
 * ============================================================================
 * Módulo 11 - Records | Ejercicio 1: Tu primer record: Dirección
 * ============================================================================
 *
 * ENUNCIADO:
 * Declará un record Direccion con los componentes calle, ciudad y codigoPostal.
 * Creá dos instancias con exactamente el mismo contenido y comprobá que:
 *   - direccionUno.equals(direccionDos) devuelve true,
 *   - pero direccionUno == direccionDos es false.
 * Imprimí también ambas direcciones para ver el toString generado automáticamente.
 *
 * REQUISITOS:
 * - Usar el record Direccion ya declarado al final de este archivo.
 * - Crear DOS objetos separados en memoria con el mismo contenido.
 * - Mostrar por pantalla el resultado de equals, el resultado de == y el toString de ambos.
 *
 * PISTAS:
 * - El record genera equals, hashCode y toString basados en los VALORES de los
 *   componentes, no en la identidad del objeto en memoria.
 * - El operador == compara referencias: true sólo si es el MISMO objeto.
 * - equals compara estado: true si todos los componentes son iguales.
 */
public class Ejercicio1PrimerRecordDireccion {

    public static void main(String[] args) {
        // TODO: creá dos direcciones con el mismo contenido, como objetos distintos

        // TODO: mostrá el resultado de equals entre ambas

        // TODO: mostrá el resultado de compararlas con ==

        // TODO: imprimí ambas direcciones y observá el toString generado
    }

    record Direccion(String calle, String ciudad, String codigoPostal) {
    }
}

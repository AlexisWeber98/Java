/*
 * ============================================================================
 * Módulo 11 - Records | Ejercicio 4: Records en colección: lista de productos
 * ============================================================================
 *
 * ENUNCIADO:
 * Armá una pequeña ArrayList<ProductoRecord> con cuatro productos donde dos
 * sean un DUPLICADO LÓGICO: objetos creados por separado pero con el mismo
 * nombre y precio. Gracias a la igualdad de valor de los records:
 *   - descubrí el duplicado con contains(),
 *   - ubicá su primera posición con indexOf(),
 *   - eliminá una ocurrencia con remove(Object).
 * Mostrá la lista antes y después de cada operación.
 *
 * REQUISITOS:
 * - Usar el record ProductoRecord(nombre, precio) ya declarado al final.
 * - Lista con 4 elementos donde dos sean contenido-idénticos pero objetos distintos.
 * - Usar contains, indexOf y remove sobre el duplicado e imprimir los resultados.
 *
 * PISTAS:
 * - equals y hashCode se generan comparando TODOS los componentes.
 * - remove(Object) quita sólo la PRIMERA ocurrencia que sea equals al argumento.
 * - indexOf devuelve -1 cuando no encuentra ninguna coincidencia.
 */
public class Ejercicio4RecordsEnColeccion {

    public static void main(String[] args) {
        // TODO: creá la lista y cargá 4 productos; dos de ellos contenido-idénticos

        // TODO: mostrá la lista completa

        // TODO: con contains, preguntá si existe un producto (objeto aparte) con datos repetidos

        // TODO: con indexOf, obtené la posición del duplicado y mostrala

        // TODO: eliminá UNA ocurrencia con remove y mostrá cómo quedó la lista
    }

    record ProductoRecord(String nombre, double precio) {
    }
}

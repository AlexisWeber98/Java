/*
 * ============================================================================
 *  Ejercicio 2 — Agenda con clase propia como clave del mapa
 * ============================================================================
 *
 *  ENUNCIADO
 *  --------
 *  La clave de un HashMap puede ser cualquier objeto. Acá usamos nuestra
 *  clase Contacto como clave y el apodo como valor. El problema: tal como
 *  está, dos contactos con EXACTAMENTE los mismos datos no son "el mismo".
 *
 *  REQUISITOS
 *  ----------
 *  1. Ejecutá este archivo y mirá la salida: ¿por qué el mapa termina con
 *     2 entradas si ambas son "Ana"? ¿Por qué la búsqueda devuelve false?
 *  2. Implementá equals(Object) y hashCode() en Contacto.
 *  3. Volvé a ejecutar: ahora sí, cargar dos veces a Ana reemplaza el valor
 *     y la búsqueda con un objeto nuevo (mismos datos) encuentra.
 *
 *  PISTAS
 *  ------
 *  - Podés generarlos con tu IDE o escribirlos a mano usando Objects.equals
 *    y Objects.hash (java.util.Objects).
 *  - Contrato a respetar: si a.equals(b) es true, entonces
 *    a.hashCode() == b.hashCode() SIEMPRE.
 *  - Un HashMap ubica cada clave en un "balde" elegido por hashCode(); si
 *    dos objetos iguales caen en baldes distintos, jamás se van a encontrar.
 */

import java.util.HashMap;
import java.util.Map;

public class Ejercicio2AgendaConClasePropia {

    static class Contacto {
        private final String nombre;
        private final String telefono;

        Contacto(String nombre, String telefono) {
            this.nombre = nombre;
            this.telefono = telefono;
        }

        String getNombre()   { return nombre; }
        String getTelefono() { return telefono; }

        // TODO 1: implementá equals(Object otro)

        // TODO 2: implementá hashCode()
    }

    public static void main(String[] args) {
        Map<Contacto, String> agenda = new HashMap<>();

        agenda.put(new Contacto("Ana", "1111-2222"), "Compañera de trabajo");

        // "Actualización" del teléfono: mismo nombre, datos nuevos.
        agenda.put(new Contacto("Ana", "9999-8888"), "Compañera de trabajo");

        System.out.println("Entradas en la agenda: " + agenda.size()); // esperamos 1...

        Contacto consulta = new Contacto("Ana", "9999-8888");
        System.out.println("¿Existe Ana con su teléfono nuevo? "
                + agenda.containsKey(consulta));                        // esperamos true...

        // TODO 3: después de implementar equals/hashCode, volvé a correr y
        //  explicá acá abajo por qué cambió el resultado.
        //
        //  Tu explicación:
    }
}

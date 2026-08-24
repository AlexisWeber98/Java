/*
 * ============================================================================
 *  Solución 2 — Agenda con clase propia como clave del mapa
 * ============================================================================
 *
 *  ¿Qué estaba fallando?
 *  ---------------------
 *  Sin sobrescribir equals/hashCode heredamos los de Object:
 *    - equals compara IDENTIDAD (¿es el mismo objeto en memoria?).
 *    - hashCode deriva de una marca interna de cada instancia.
 *  Dos Contacto("Ana", ...) eran claves DISTINTAS: el mapa las guardaba en
 *  "baldes" separados, duplicaba la entrada y containsKey daba false.
 *
 *  El contrato equals/hashCode (para explicar en clase)
 *  ----------------------------------------------------
 *  1. Si a.equals(b) es true  =>  a.hashCode() == b.hashCode(). OBLIGATORIO.
 *  2. Hashes iguales NO obligan a que equals dé true (las colisiones son
 *     normales; el mapa las resuelve encadenando dentro del balde).
 *  3. Usá los MISMOS campos en ambos métodos. Si cambiás uno, cambiá el otro.
 *  Con el contrato respetado, claves iguales caen en el mismo balde y el
 *  put(...) REEMPLAZA el valor en lugar de duplicar la entrada.
 *  Ojo: equals/hashCode deben usar el campo que define identidad (acá, el
 *  nombre). Si incluyéramos el teléfono, "Ana con teléfono nuevo" sería otra
 *  clave distinta y el mapa volvería a duplicar la entrada.
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Solucion2AgendaConClasePropia {

    static class Contacto {
        private final String nombre;
        private final String telefono;

        Contacto(String nombre, String telefono) {
            this.nombre = nombre;
            this.telefono = telefono;
        }

        String getNombre()   { return nombre; }
        String getTelefono() { return telefono; }

        // Decisión de diseño: la identidad de un contacto en ESTA agenda es
        // el nombre (queremos que "cargar Ana" actualice su teléfono). Por
        // eso equals y hashCode usan SOLO el campo clave, no el teléfono.
        @Override
        public boolean equals(Object otro) {
            if (this == otro) return true;                  // misma instancia
            if (!(otro instanceof Contacto)) return false;  // otro tipo: no son iguales
            return Objects.equals(nombre, ((Contacto) otro).nombre);
        }

        @Override
        public int hashCode() {
            // Mismo campo que equals: si agregás campos acá, agregalos arriba.
            return Objects.hash(nombre);
        }
    }

    public static void main(String[] args) {
        Map<Contacto, String> agenda = new HashMap<>();

        agenda.put(new Contacto("Ana", "1111-2222"), "Compañera de trabajo");

        // "Actualización" del teléfono: mismo nombre, datos nuevos.
        agenda.put(new Contacto("Ana", "9999-8888"), "Compañera de trabajo");

        System.out.println("Entradas en la agenda: " + agenda.size()); // ahora sí: 1

        Contacto consulta = new Contacto("Ana", "9999-8888");
        System.out.println("¿Existe Ana con su teléfono nuevo? "
                + agenda.containsKey(consulta));                        // true

        System.out.println("Apodo guardado: " + agenda.get(consulta));
    }
}

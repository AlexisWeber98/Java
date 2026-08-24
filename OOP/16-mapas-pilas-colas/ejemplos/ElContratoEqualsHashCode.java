import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * El contrato equals + hashCode en claves de un mapa.
 *
 * Parte 1: Personita SIN sobrescribir nada -> dos personas con el mismo
 * nombre son dos claves DISTINTAS para el HashMap. Bug silencioso.
 * Parte 2: Personita CON el contrato cumplido -> una sola clave lógica.
 */
public class ElContratoEqualsHashCode {

    static class PersonitaSinContrato {
        String nombre;
        int edad;

        PersonitaSinContrato(String nombre, int edad) {
            this.nombre = nombre;
            this.edad = edad;
        }
    }

    static class PersonitaConContrato {
        String nombre;
        int edad;

        PersonitaConContrato(String nombre, int edad) {
            this.nombre = nombre;
            this.edad = edad;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof PersonitaConContrato otra)) {
                return false;
            }
            return edad == otra.edad && Objects.equals(nombre, otra.nombre);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nombre, edad);
        }
    }

    public static void main(String[] args) {
        // PARTE 1: sin contrato. Mismo nombre y edad, objetos distintos.
        Map<PersonitaSinContrato, String> notasMalas = new HashMap<>();
        notasMalas.put(new PersonitaSinContrato("Ana", 30), "jefa de equipo");
        notasMalas.put(new PersonitaSinContrato("Ana", 30), "backup");

        System.out.println("Sin contrato: esperábamos 1 entrada...");
        System.out.println("Tamaño del mapa: " + notasMalas.size() + "  <- ¡BUG! Son 2.");

        // PARTE 2: con equals + hashCode juntos.
        Map<PersonitaConContrato, String> notasBuenas = new HashMap<>();
        notasBuenas.put(new PersonitaConContrato("Ana", 30), "jefa de equipo");
        notasBuenas.put(new PersonitaConContrato("Ana", 30), "backup");

        System.out.println("\nCon contrato: mismo caso, ahora bien.");
        System.out.println("Tamaño del mapa: " + notasBuenas.size());
        for (Map.Entry<PersonitaConContrato, String> par : notasBuenas.entrySet()) {
            System.out.println(par.getKey().nombre + " → " + par.getValue());
        }

        System.out.println("\nMORAL: si tu clase es clave de un mapa,"
                + "\nsobrescribí equals Y hashCode SIEMPRE JUNTOS.");
    }
}

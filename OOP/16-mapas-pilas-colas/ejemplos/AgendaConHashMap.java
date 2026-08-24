import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Agenda de contactos con HashMap: agregar, buscar, manejar claves
 * ausentes con getOrDefault e iterar entradas ordenadas por clave.
 */
public class AgendaConHashMap {

    public static void main(String[] args) {
        Map<String, String> agenda = new HashMap<>();

        // Agregar contactos: clave = nombre, valor = teléfono.
        agenda.put("Ana", "11-5555-1010");
        agenda.put("Bruno", "11-5555-2020");
        agenda.put("Carla", "11-5555-3030");

        System.out.println("Contactos en la agenda: " + agenda.size());

        // Buscar una clave existente.
        System.out.println("Teléfono de Ana: " + agenda.get("Ana"));

        // Clave ausente: get devuelve null, getOrDefault da un valor seguro.
        System.out.println("Teléfono de Luis (get): " + agenda.get("Luis"));
        System.out.println("Teléfono de Luis (getOrDefault): "
                + agenda.getOrDefault("Luis", "no registrado"));

        // containsKey antes de sobrescribir algo importante.
        if (!agenda.containsKey("Bruno")) {
            agenda.put("Bruno", "11-5555-2999");
        } else {
            System.out.println("Bruno ya está agendado, no lo pisa otro número.");
        }

        // Actualizar un valor: put sobre clave existente reemplaza.
        agenda.put("Carla", "11-5555-3939");
        System.out.println("Nuevo teléfono de Carla: " + agenda.get("Carla"));

        // Eliminar un contacto.
        agenda.remove("Bruno");
        System.out.println("¿Sigue Bruno? " + agenda.containsKey("Bruno"));

        // Iterar entradas ordenadas alfabéticamente: TreeMap sobre el mismo mapa.
        System.out.println("\nAgenda ordenada por nombre:");
        Map<String, String> ordenada = new TreeMap<>(agenda);
        for (Map.Entry<String, String> par : ordenada.entrySet()) {
            System.out.println(par.getKey() + " → " + par.getValue());
        }
    }
}

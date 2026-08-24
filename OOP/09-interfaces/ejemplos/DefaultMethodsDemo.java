// ============================================================
// Módulo 09 · Ejemplo 3: métodos default — evolución segura de APIs
// Ejecutar con: java DefaultMethodsDemo.java
// ============================================================
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// La historia: publicaste Buscador v1 con buscar(). Miles de clases ya lo
// implementan. En v2 querés agregar búsqueda ordenada... ¿romper todo?
// Con `default` (Java 8+): agregás el método CON cuerpo y nadie se rompe.
interface Buscador {
    List<String> buscar(String consulta);

    default List<String> buscarOrdenada(String consulta) {
        List<String> resultados = new ArrayList<>(buscar(consulta));
        Collections.sort(resultados); // A-Z, tal cual viene del contrato
        return resultados;
    }
}

// Clase "vieja", escrita cuando el contrato solo tenía buscar().
// Usa buscarOrdenada() TAL CUAL viene: heredó el default sin escribir nada.
class BuscadorSimple implements Buscador {
    @Override
    public List<String> buscar(String consulta) {
        return List.of("Zorro", "Águila", "Burro");
    }
}

// Clase "nueva": decide sobrescribir el default para personalizarlo,
// reutilizando la versión del contrato con Interfaz.super.metodo(...).
class BuscadorPremium implements Buscador {
    @Override
    public List<String> buscar(String consulta) {
        return List.of("zorro gris", "aguila real", "burro andino", "Águila marina");
    }

    @Override
    public List<String> buscarOrdenada(String consulta) {
        List<String> resultados = Buscador.super.buscarOrdenada(consulta); // base del contrato
        resultados.addFirst(resultados.getFirst()); // extra premium: duplica el primero
        return resultados;
    }
}

public class DefaultMethodsDemo {
    public static void main(String[] args) {
        Buscador simple = new BuscadorSimple();
        Buscador premium = new BuscadorPremium();

        System.out.println("Simple (usa el default tal cual):   "
                + simple.buscarOrdenada("animales"));
        System.out.println("Premium (sobrescribe y reutiliza): "
                + premium.buscarOrdenada("animales"));

        // Moraleja: el contrato EVOLUCIONÓ (v1 -> v2) sin romper a
        // BuscadorSimple. Y cada implementador elige: usarlo o mejorarlo.
    }
}

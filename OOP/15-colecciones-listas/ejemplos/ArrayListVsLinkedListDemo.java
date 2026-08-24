// Módulo 15 — ArrayList vs LinkedList: misma operación, dos estructuras.
// Ejecutar: java ejemplos/ArrayListVsLinkedListDemo.java
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ArrayListVsLinkedListDemo {

    private static final int CANTIDAD = 50_000;

    public static void main(String[] args) {
        System.out.println("Comparando con " + CANTIDAD + " elementos por operación...\n");

        // Escenario 1: agregar al final. La teoría dice "ambos baratos".
        long tArrayFinal = medirAgregarAlFinal(new ArrayList<>());
        long tLinkedFinal = medirAgregarAlFinal(new LinkedList<>());
        System.out.println("Agregar al final   -> ArrayList: " + ms(tArrayFinal) + " | LinkedList: " + ms(tLinkedFinal));

        // Escenario 2: insertar en el medio. La teoría favorece a LinkedList...
        long tArrayMedio = medirInsertarAlMedio(new ArrayList<>());
        long tLinkedMedio = medirInsertarAlMedio(new LinkedList<>());
        System.out.println("Insertar al medio  -> ArrayList: " + ms(tArrayMedio) + " | LinkedList: " + ms(tLinkedMedio));

        // Escenario 3: acceso aleatorio por índice. Acá no hay debate: O(1) vs O(n).
        List<Integer> arrayLleno = new ArrayList<>();
        for (int i = 0; i < CANTIDAD; i++) {
            arrayLleno.add(i);
        }
        long tArrayGet = medirLecturas(arrayLleno);
        long tLinkedGet = medirLecturas(new LinkedList<>(arrayLleno));
        System.out.println("10.000 get(i)      -> ArrayList: " + ms(tArrayGet) + " | LinkedList: " + ms(tLinkedGet));

        // CONCLUSIÓN HONESTA:
        // La teoría promete que LinkedList gana insertando en el medio (reenganchar
        // nodos es O(1))... pero para insertar al medio PRIMERO hay que llegar hasta
        // ahí caminando nodo por nodo, y cada salto de referencia cacha mal en la CPU.
        // Resultado: ArrayList suele ganar incluso donde la teoría lo da por perdedor,
        // y aplasta en lecturas. Elegí ArrayList por defecto; considerá ArrayDeque
        // para colas/pilas y reservá LinkedList para casos medidos, no supuestos.
    }

    private static long medirAgregarAlFinal(List<Integer> lista) {
        long inicio = System.nanoTime();
        for (int i = 0; i < CANTIDAD; i++) {
            lista.add(i);
        }
        return System.nanoTime() - inicio;
    }

    private static long medirInsertarAlMedio(List<Integer> lista) {
        // Insertamos siempre en la mitad actual de la lista.
        long inicio = System.nanoTime();
        for (int i = 0; i < CANTIDAD; i++) {
            lista.add(lista.size() / 2, i);
        }
        return System.nanoTime() - inicio;
    }

    private static long medirLecturas(List<Integer> lista) {
        long inicio = System.nanoTime();
        int suma = 0;
        for (int i = 0; i < 10_000; i++) {
            suma += lista.get(i * (lista.size() / 10_000)); // posiciones dispersas
        }
        long duracion = System.nanoTime() - inicio;
        if (suma == Integer.MIN_VALUE) {
            System.out.println("imposible"); // evita que el JIT elimine el bucle
        }
        return duracion;
    }

    private static String ms(long nanos) {
        return String.format("%.1f ms", nanos / 1_000_000.0);
    }
}

/*
 * ============================================================================
 * Solución 1 — ¿Qué días hay que trabajar?
 * ============================================================================
 *
 * ENUNCIADO
 * Declarar un enum DiaSemana con los siete días y darle un método
 * esLaborable() que responda true de lunes a viernes. Después recorrer la
 * semana con values(), imprimir el veredicto de cada día y contar el total.
 *
 * REQUISITOS
 *   1. Enum DiaSemana con las siete constantes, una por línea.
 *   2. Método esLaborable(): true de LUNES a VIERNES, false el fin de semana.
 *   3. Recorrer con values() e imprimir "DIA -> es/no es laborable".
 *   4. Imprimir el total de días laborables.
 *
 * PISTAS (para revisar tu intento)
 *   - Comparar enums con == / != es seguro: cada constante existe UNA sola
 *     vez en la JVM, son singletons naturales.
 *   - printf con %-10s alinea los nombres a la izquierda: salida prolija.
 */
public class Solucion1DiasLaborables {

    enum DiaSemana {
        LUNES,
        MARTES,
        MIERCOLES,
        JUEVES,
        VIERNES,
        SABADO,
        DOMINGO;

        boolean esLaborable() {
            // Los enums se comparan con == / != porque cada constante es un
            // singleton: no hay dos instancias que comparar mal.
            return this != SABADO && this != DOMINGO;
        }
    }

    public static void main(String[] args) {
        System.out.println("La semana de una persona con proyectos:");
        int diasLaborables = 0;

        for (DiaSemana dia : DiaSemana.values()) {
            String veredicto = dia.esLaborable() ? "es laborable" : "no es laborable";
            System.out.printf("%-10s -> %s%n", dia, veredicto);

            if (dia.esLaborable()) {
                diasLaborables++;
            }
        }

        System.out.println();
        System.out.println("Total de días laborables: " + diasLaborables);
    }
}

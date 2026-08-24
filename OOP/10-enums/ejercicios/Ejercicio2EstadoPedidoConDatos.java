/*
 * ============================================================================
 * Ejercicio 2 — Pedidos con datos: enums con campos y constructor
 * ============================================================================
 *
 * ENUNCIADO
 * Cuando cada constante necesita SU PROPIA información, el enum crece:
 * aparecen campos, constructor y métodos. Modelá el ciclo de vida de un
 * pedido con el enum EstadoPedido (PENDIENTE, PAGADO, ENVIADO, ENTREGADO,
 * CANCELADO): cada estado guarda una descripcion y sus diasEstimados de
 * entrega, y sabe describirse solo con resumen().
 *
 * REQUISITOS
 *   1. Pasarle los datos a cada constante entre paréntesis:
 *      PENDIENTE("...", 2), PAGADO("...", 1), etc.
 *   2. Campos privados y finales: descripcion (String), diasEstimados (int).
 *   3. Constructor que reciba ambos valores y los asigne.
 *   4. Método public String resumen() que devuelva algo como:
 *      "ENVIADO: Tu pedido está en camino (3 día(s) hábiles)"
 *      Si diasEstimados es 0, la parte entre paréntesis no aparece.
 *   5. En main, recorrer los estados con values() e imprimir cada resumen().
 *
 * PISTAS
 *   - El constructor de un enum SIEMPRE es privado (aunque no lo escribas):
 *     nadie puede hacer new EstadoPedido(...) por fuera de la declaración.
 *   - La lista de constantes va PRIMERO y termina con punto y coma antes de
 *     campos y métodos: CANCELADO("El pedido quedó cancelado", 0);
 *   - Campos final = cada estado es inmutable: nace con sus datos y no cambia.
 */
public class Ejercicio2EstadoPedidoConDatos {

    enum EstadoPedido {
        // TODO paso 1: pasale los datos a cada constante entre paréntesis.
        //       Sugerencia: PENDIENTE(2), PAGADO(1), ENVIADO(3),
        //       ENTREGADO(0) y CANCELADO(0).
        PENDIENTE,
        PAGADO,
        ENVIADO,
        ENTREGADO,
        CANCELADO;

        // TODO paso 2: declará los campos privados finales.

        // TODO paso 3: creá el constructor que reciba descripcion y
        //       diasEstimados, y asignelos a los campos.

        // TODO paso 4: implementá resumen() según el requisito 4.
    }

    public static void main(String[] args) {
        System.out.println("Ciclo de vida de tu pedido:");
        // TODO: recorré EstadoPedido.values() e imprimí estado.resumen().
    }
}

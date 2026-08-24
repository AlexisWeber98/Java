/*
 * Módulo 13 — Conversiones de tipos
 * Ejercicio 2: Pérdida silenciosa (SOLUCIÓN)
 *
 * La solución muestra las DOS facturas: la rota (como la tenías) y la
 * corregida, para compararlas una al lado de la otra.
 *
 * SALIDA REAL DEL PROGRAMA:
 *   === Versión ROTA ===
 *   Precio unitario declarado: 999.99
 *   Cantidad: 3
 *   TOTAL FACTURA: 2997          <- el cliente esperaba 2999.97: perdimos $2.97
 *   === Versión CORREGIDA ===
 *   Precio unitario declarado: 999.99
 *   Cantidad: 3
 *   TOTAL FACTURA: 2999.9700000000003
 *
 * CLAVES DE LA SOLUCIÓN:
 * - La pérdida ocurre en el CAST: "(int) precioUnitario" trunca 999.99 -> 999.
 *   Ahí mueren los centavos; multiplicar por 3 después solo replica el error.
 * - Es "silenciosa" porque el cast explícito ES un aviso que el programador
 *   firma: el compilador exige el paréntesis justamente para obligarte a decir
 *   "yo sé lo que hago". No hay warning en tiempo de ejecución.
 * - La corrección es simple: double para el total, sin cast. El int cantidad
 *   se promueve automáticamente a double en la multiplicación.
 * - Nota final honesta: mirá el total corregido... ¡2999.9700000000003!
 *   double tampoco representa exacto los decimales (es binario). Para dinero
 *   real se usa BigDecimal o se trabaja en centavos enteros. Eso queda para
 *   otro módulo; acá lo importante era ver DÓNDE y POR QUÉ se perdía info.
 */
public class Solucion2PerdidaSilenciosa {

    public static void main(String[] args) {
        mostrarFacturaRota();
        System.out.println();
        mostrarFacturaCorregida();
    }

    // Versión original con el bug: la dejamos intacta para comparar.
    static void mostrarFacturaRota() {
        double precioUnitario = 999.99;
        int cantidad = 3;

        // AQUÍ ocurre la pérdida: (int) trunca hacia cero, 999.99 pasa a ser 999.
        // Se pierden $0.99 por unidad ANTES de cualquier cálculo. Silencioso total.
        int precioEntero = (int) precioUnitario;

        int totalFactura = precioEntero * cantidad;
        System.out.println("=== Versión ROTA ===");
        System.out.println("Precio unitario declarado: " + precioUnitario);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("TOTAL FACTURA: " + totalFactura);
    }

    // Versión corregida: el tipo acompaña al dato. Un precio vive mejor en double.
    static void mostrarFacturaCorregida() {
        double precioUnitario = 999.99;
        int cantidad = 3;

        // Sin cast: cantidad (int) se promueve implícitamente a double.
        double totalFactura = precioUnitario * cantidad;
        System.out.println("=== Versión CORREGIDA ===");
        System.out.println("Precio unitario declarado: " + precioUnitario);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("TOTAL FACTURA: " + totalFactura);
    }
}

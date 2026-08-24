/*
 * EJEMPLO 1: El mismo problema, resuelto de dos maneras.
 *
 * Problema: controlar el stock de bolsas de arroz en un almacén.
 *
 * VERSIÓN A (estructurada): datos sueltos + funciones que los procesan.
 * VERSIÓN B (orientada a objetos): una clase que junta datos + comportamiento,
 *            y objetos creados a partir de ella.
 *
 * Corrélo y compará: ¿cuál te parece más fácil de extender si mañana
 * queremos manejar también fideos y yerba?
 */
public class Ejemplo1ProceduralVsObjetos {

    public static void main(String[] args) {

        System.out.println("=== VERSION A: ESTILO PROCEDURAL ===");

        // El estado vive SUERTO, fuera de cualquier dueño.
        int stockArroz = 30;
        int puntoDeReposicionArroz = 5;

        // El comportamiento son funciones que reciben y devuelven datos.
        stockArroz = venderProcedural(stockArroz, 12);
        avisarSiHayQueReponer(stockArroz, puntoDeReposicionArroz);
        stockArroz = reponerProcedural(stockArroz, 10);

        System.out.println("Stock final del arroz: " + stockArroz + " bolsas");
        System.out.println();

        System.out.println("=== VERSION B: ESTILO ORIENTADO A OBJETOS ===");

        // El estado y el comportamiento viven JUNTOS, dentro del objeto.
        // Crear un segundo producto ahora cuesta UNA línea:
        ProductoSimple arroz = new ProductoSimple("Arroz 1kg", 30, 5);
        ProductoSimple fideos = new ProductoSimple("Fideos 500g", 8, 3);

        arroz.vender(12);
        arroz.mostrarEstado();
        arroz.reponer(10);

        fideos.vender(6);
        fideos.mostrarEstado();

        /*
         * MORALEJA:
         * - En la versión A, cada producto nuevo = más variables sueltas
         *   y riesgo de pasarle a la función la variable equivocada.
         * - En la versión B, cada producto nuevo = un objeto más del mismo
         *   molde, con sus reglas escritas UNA sola vez adentro.
         */
    }

    // ---------- Funciones de la versión procedural ----------
    // (la palabra static es un requisito técnico para llamarlas desde main;
    //  la estudiamos en serio en el módulo 03)

    private static int venderProcedural(int stockActual, int unidadesVendidas) {
        if (unidadesVendidas > stockActual) {
            System.out.println("No hay suficiente stock para esa venta.");
            return stockActual;
        }
        return stockActual - unidadesVendidas;
    }

    private static int reponerProcedural(int stockActual, int unidadesIngresadas) {
        return stockActual + unidadesIngresadas;
    }

    private static void avisarSiHayQueReponer(int stockActual, int puntoDeReposicion) {
        if (stockActual <= puntoDeReposicion) {
            System.out.println("[PROCEDURAL] Atencion: quedan " + stockActual + " bolsas, hay que reponer.");
        }
    }
}

/*
 * EL MOLDE de la versión orientada a objetos.
 * No declara "package" a propósito: corre standalone con `java Ejemplo1ProceduralVsObjetos.java`.
 */
class ProductoSimple {

    // ---- ESTADO: lo que cada objeto sabe ----
    String nombre;
    int stock;
    int puntoDeReposicion;

    // ---- COMPORTAMIENTO: lo que cada objeto sabe hacer ----

    // Nota didáctica: esto técnicamente es un constructor; se estudia en el módulo 04.
    ProductoSimple(String nombreInicial, int stockInicial, int puntoDeReposicionInicial) {
        this.nombre = nombreInicial;
        this.stock = stockInicial;
        this.puntoDeReposicion = puntoDeReposicionInicial;
    }

    void vender(int unidadesVendidas) {
        if (unidadesVendidas <= 0 || unidadesVendidas > stock) {
            System.out.println(nombre + ": venta rechazada (stock insuficiente o cantidad invalida).");
            return;
        }
        stock = stock - unidadesVendidas;
        if (stock <= puntoDeReposicion) {
            System.out.println(nombre + ": quedan pocas bolsas, conviene reponer.");
        }
    }

    void reponer(int unidadesIngresadas) {
        if (unidadesIngresadas <= 0) {
            return;
        }
        stock = stock + unidadesIngresadas;
    }

    void mostrarEstado() {
        System.out.println(nombre + " -> stock actual: " + stock + " bolsas");
    }
}

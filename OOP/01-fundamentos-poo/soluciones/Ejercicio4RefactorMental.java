/*
 * ============================================
 *  Solución 4: Refactor mental
 * ============================================
 * Versión orientada a objetos: los datos viajan juntos y la lógica que antes
 * estaba duplicada ahora existe UNA sola vez, dentro de la clase.
 */
class Solucion4RefactorMental {

    public static void main(String[] args) {
        Articulo yerba = new Articulo();
        yerba.nombre = "Yerba mate";
        yerba.precio = 2500.0;
        yerba.stock = 10;

        Articulo azucar = new Articulo();
        azucar.nombre = "Azúcar";
        azucar.precio = 1200.0;
        azucar.stock = 30;

        System.out.println("--- ANTES DEL DESCUENTO ---");
        yerba.describir();
        azucar.describir();

        yerba.aplicarDescuento(10);
        azucar.aplicarDescuento(10);

        System.out.println("--- DESPUÉS DEL DESCUENTO ---");
        yerba.describir();
        azucar.describir();

        double total = yerba.valorEnInventario() + azucar.valorEnInventario();
        System.out.println("Valor del inventario: $" + total);

        /*
         * Qué mejoró respecto del código procedural:
         * 1) Los tres datos de cada producto viven juntos dentro de un objeto:
         *    es imposible mezclar el precio de la yerba con el stock del azúcar.
         * 2) La lógica de describir/descontar/valorizar existe UNA vez en la
         *    clase: se corrige una vez y la usan todos los artículos.
         * 3) Agregar un tercer producto son unas pocas líneas de datos, sin
         *    copiar ni una sola línea de lógica.
         * Problemas típicos que se esperaban en TODO 1:
         *   - Variables sueltas sin relación: nada impide confundir precio1
         *     con stock2 (el compilador no avisa).
         *   - Lógica duplicada: el descuento está escrito dos veces; si hay
         *     que corregirlo, hay que acordarse de cambiarlo en todos lados.
         *   - Escala pésima: cada producto nuevo repite todo el bloque.
         */
    }
}

/*
 * La clase del refactor: estado (campos) + comportamiento (métodos).
 */
class Articulo {
    String nombre;
    double precio;
    int stock;

    void aplicarDescuento(double porcentaje) {
        precio = precio - precio * porcentaje / 100;
    }

    double valorEnInventario() {
        return precio * stock;
    }

    void describir() {
        System.out.println(nombre + " | $" + precio + " | stock: " + stock);
    }
}

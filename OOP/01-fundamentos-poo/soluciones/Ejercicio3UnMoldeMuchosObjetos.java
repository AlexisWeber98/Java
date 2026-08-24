/*
 * ============================================
 *  Solución 3: Un molde, muchos objetos
 * ============================================
 * Tres instancias de un mismo molde, cada una con estado independiente.
 */
class Solucion3UnMoldeMuchosObjetos {

    public static void main(String[] args) {
        Producto fideos = new Producto();
        fideos.nombre = "Fideos";
        fideos.precio = 950.0;
        fideos.stock = 20;

        Producto arroz = new Producto();
        arroz.nombre = "Arroz";
        arroz.precio = 1300.0;
        arroz.stock = 15;

        Producto aceite = new Producto();
        aceite.nombre = "Aceite";
        aceite.precio = 2100.0;
        aceite.stock = 8;

        System.out.println("--- ANTES ---");
        fideos.describir();
        arroz.describir();
        aceite.describir();

        fideos.vender(5);
        arroz.reponer(10);

        System.out.println("--- DESPUÉS ---");
        fideos.describir();
        arroz.describir();
        aceite.describir(); // sigue exactamente igual: nadie lo tocó

        /*
         * Clave del ejercicio: los tres objetos nacieron del MISMO molde pero
         * cada uno tiene su propia copia de nombre, precio y stock. Cambiar el
         * stock de fideos no toca ni a arroz ni a aceite. Esa es la diferencia
         * entre la clase (el plano) y las instancias (las casas construidas).
         */
    }

    /*
     * Igual que en la Solución 2: Producto vive adentro para poder compilarse
     * junto con el ejercicio sin choque de nombres. En tu versión va afuera.
     */
    static class Producto {
        String nombre;
        double precio;
        int stock;

        void describir() {
            System.out.println(nombre + " | $" + precio + " | stock: " + stock);
        }

        void vender(int cantidad) {
            if (cantidad <= stock) {
                stock = stock - cantidad;
                System.out.println("Venta ok: -" + cantidad + " de " + nombre);
            } else {
                System.out.println("No hay stock suficiente de " + nombre + ": quedan " + stock);
            }
        }

        void reponer(int cantidad) {
            stock = stock + cantidad;
            System.out.println("Reposición ok: +" + cantidad + " de " + nombre);
        }
    }
}

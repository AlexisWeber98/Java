/*
 * ============================================
 *  Solución 1: Detective de objetos
 * ============================================
 * Análisis completo de la escena del kiosco + una clase de ejemplo.
 */
class Solucion1DetectiveDeObjetos {

    public static void main(String[] args) {
        Golosina chocolate = new Golosina();
        chocolate.nombre = "Chocolate Block";
        chocolate.precio = 1500.0;
        chocolate.stock = 12;

        chocolate.mostrarInformacion();
        chocolate.vender(2);
        System.out.println("Stock después de la venta: " + chocolate.stock);
        chocolate.vender(100); // más de lo que hay: el objeto se defiende solo
    }
}

/*
 * ANÁLISIS DE LA ESCENA (lo que se esperaba en los comentarios del ejercicio):
 *
 * Clases candidatas:
 *   - Dueño: atributos nombre, dineroEnCaja; métodos cobrar(), reponerStock().
 *   - Cliente: atributos nombre, dineroDisponible; métodos pedir(), pagar().
 *   - Golosina: atributos nombre, precio, stock; métodos vender(), reponer().
 *   - CajaRegistradora: atributos totalAcumulado; métodos registrarVenta(),
 *     calcularVuelto().
 *   - Kiosco: podría ser clase (nombre, direccion) o quedar como mero
 *     escenario de la escena. Ambas respuestas son válidas si se justifican.
 *
 * Sustantivos trampa (NO son clases):
 *   - "La compra": no es un objeto con estado que vive en el tiempo; es una
 *     INTERACCIÓN entre cliente, golosina y caja. Se convierte en un método
 *     (vender()) o, si el negocio lo exige más adelante, en una clase Venta.
 *   - "El precio": es un ATRIBUTO de Golosina, no una clase Precio.
 *   - "El vuelto": es el resultado de un cálculo que devuelve un método,
 *     no un objeto.
 *
 * Idea clave: una clase merece existir cuando tiene ESTADO propio y un
 * COMPORTAMIENTO coherente con ese estado. Un sustantivo suelto sin estado
 * ni responsabilidad suele ser trampa.
 */

/*
 * Ejemplo de clase mínima para la escena.
 */
class Golosina {
    String nombre;
    double precio;
    int stock;

    void mostrarInformacion() {
        System.out.println(nombre + " | $" + precio + " | stock: " + stock);
    }

    void vender(int cantidad) {
        if (cantidad <= stock) {
            stock = stock - cantidad;
            System.out.println("Vendidas " + cantidad + " unidades de " + nombre);
        } else {
            System.out.println("No alcanza el stock de " + nombre + ": quedan " + stock);
        }
    }
}

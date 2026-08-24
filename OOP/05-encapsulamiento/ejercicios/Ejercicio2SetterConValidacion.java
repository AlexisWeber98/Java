/*
 * ============================================================================
 *  Ejercicio 2 — Setter con validación
 * ============================================================================
 *
 *  ENUNCIADO
 *  En el ejercicio 1 pusiste el estado bajo llave. Ahora viene la parte
 *  linda: usar esa puerta única para PROTEGER el estado.
 *
 *  Regla de negocio: un precio nunca puede ser negativo.
 *  Hacé que ProductoValidado.setPrecio rechace los precios negativos y
 *  DOCUMENTÁ la estrategia que elijas:
 *
 *    Opción A: ignorar el cambio, conservar el valor anterior y avisar.
 *    Opción B: "clamp": guardar 0, el valor válido más cercano.
 *
 *  REQUISITOS
 *   1. Después de cualquier llamada a setPrecio, el precio del objeto
 *      nunca es negativo.
 *   2. Un comentario junto al setter explica qué opción elegiste y por
 *      qué. Las dos son defendibles; lo que no es opcional es justificar.
 *   3. El main demuestra un INTENTO rechazado (el precio anterior queda
 *      intacto) y después un cambio válido (ese sí se aplica).
 *
 *  PISTAS
 *   - Toda la validación vive en el setter: es el ÚNICO punto de entrada
 *     del estado. Eso es justo lo que compramos al encapsular.
 *   - Pensá quién llama al setter: si te pasa un -50, ¿querés corregirlo
 *     en silencio y esconderle el problema, o querés que se entere?
 *   - La clase ya tiene getters, así que desde el main podés verificar
 *     qué quedó guardado después de cada intento.
 * ============================================================================
 */
public class Ejercicio2SetterConValidacion {

    /** Producto con la puerta única instalada... falta el portero. */
    static class ProductoValidado {
        private final String nombre;
        private double precio;

        public ProductoValidado(String nombre, double precio) {
            this.nombre = nombre;
            this.precio = precio;
        }

        public String getNombre() {
            return nombre;
        }

        public double getPrecio() {
            return precio;
        }

        public void setPrecio(double nuevoPrecio) {
            // TODO 1: acá va la validación. Hoy deja pasar cualquier cosa,
            //         incluso precios absurdos como el -50 del main.
            this.precio = nuevoPrecio;
        }
    }

    public static void main(String[] args) {
        ProductoValidado mate = new ProductoValidado("Mate imperial", 18000.0);

        System.out.println("Precio inicial: " + mate.getPrecio());

        // Intento de carga inválida: hoy el objeto lo acepta sin chistar.
        mate.setPrecio(-50);
        System.out.println("Tras intentar -50   : " + mate.getPrecio()
                + "   <- ¡quedó en negativo!");

        // TODO 2: implementá la validación según la opción A o B que
        //         elegiste (documentala junto al setter) y corré de nuevo:
        //         el -50 no tiene que colarse; este cambio, sí.
        mate.setPrecio(19500.0);
        System.out.println("Tras cargar 19500.0 : " + mate.getPrecio());
    }
}

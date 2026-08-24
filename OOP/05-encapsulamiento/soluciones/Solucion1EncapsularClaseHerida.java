/*
 * ============================================================================
 *  Solución 1 — Encapsular una clase herida
 * ============================================================================
 *
 *  IDEA CLAVE
 *  El estado pasa a ser PRIVATE: nadie fuera de la clase puede leerlo ni
 *  escribirlo directo. Todo contacto ocurre por los métodos que NOSOTROS
 *  decidimos exponer.
 *
 *  Decisiones de diseño:
 *   - nombre es PRIVATE y FINAL: es la identidad del producto, nace en el
 *     constructor y no cambia jamás. Por eso no tiene setter: si nada
 *     justifica mutarlo, no expongas la forma de mutarlo.
 *   - precioBase y stock SÍ tienen setters porque representan valores que
 *     legítimamente cambian con el tiempo (rehacer precios, recibir
 *     mercadería, vender). Hoy asignan sin más; ya tenemos EL punto único
 *     donde colgar validación en el próximo ejercicio.
 *   - Getters para todo: leer el estado es inocuo; el peligro está en
 *     escribir sin control.
 * ============================================================================
 */

/**
 * Versión CURADA: estado private, acceso solo por la puerta de los métodos.
 */
class ProductoEncapsulado {

    private final String nombre;
    private double precioBase;
    private int stock;

    public ProductoEncapsulado(String nombre, double precioBase, int stock) {
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.stock = stock;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public int getStock() {
        return stock;
    }

    /**
     * Único punto por donde entra un precio nuevo. Mañana acá va la regla
     * de negocio; hoy, el simple reemplazo.
     */
    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    /** Único punto por donde entra un stock nuevo. Ídem: puerta única. */
    public void setStock(int stock) {
        this.stock = stock;
    }
}

public class Solucion1EncapsularClaseHerida {

    public static void main(String[] args) {
        // Mismo escenario del enunciado, pero construyendo por constructor.
        ProductoEncapsulado teclado = new ProductoEncapsulado("Teclado mecánico", 25000.0, 10);

        // Las mismas dos líneas abusivas, ahora por los setters.
        // (Todavía pasan: la VALIDACIÓN es el ejercicio 2. La diferencia es
        // que ahora pasan por NUESTRA puerta, y ahí decidimos qué hacer.)
        teclado.setStock(-7);
        teclado.setPrecioBase(-999);

        System.out.println("Producto: " + teclado.getNombre());
        System.out.println("Precio : " + teclado.getPrecioBase());
        System.out.println("Stock  : " + teclado.getStock());

        // Fijate: si descomentás la línea siguiente, NO compila.
        // teclado.stock = 5;
        // Ese error de compilación es exactamente la protección que compramos
        // al encapsular: el detalle interno dejó de ser tocable desde afuera.
    }
}

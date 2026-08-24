package dominio;

/**
 * Reglas puras de descuento para una tienda.
 * No toca base de datos ni red: solo lógica de negocio.
 *
 * Reglas:
 * - Cliente VIP: 15% de descuento.
 * - Compra mayor a $100.000: 5% extra (acumulable con VIP).
 * - Combos: el descuento total nunca supera el 20%.
 */
public class CalculadoraDescuentos {

    static final double DESCUENTO_VIP = 0.15;
    static final double DESCUENTO_COMPRA_GRANDE = 0.05;
    static final double LIMITE_COMPRA_GRANDE = 100_000;
    static final double TOPE_COMBO = 0.20;

    /**
     * Calcula el porcentaje total de descuento (entre 0 y 1) según cliente y monto.
     * @throws IllegalArgumentException si el precio es negativo
     */
    public double calcularDescuento(double precio, boolean esVip) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }

        double descuento = 0;

        if (esVip) {
            descuento += DESCUENTO_VIP;
        }
        if (precio > LIMITE_COMPRA_GRANDE) {
            descuento += DESCUENTO_COMPRA_GRANDE;
        }
        if (descuento > TOPE_COMBO) {
            descuento = TOPE_COMBO;
        }

        return descuento;
    }

    /** Precio final a pagar luego de aplicar el descuento que corresponda. */
    public double calcularPrecioFinal(double precio, boolean esVip) {
        double descuento = calcularDescuento(precio, esVip);
        return precio - (precio * descuento);
    }
}

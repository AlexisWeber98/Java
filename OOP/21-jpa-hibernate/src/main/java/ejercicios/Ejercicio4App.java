package ejercicios;

/**
 * Ejercicio 4 · Consultas JPQL con filtro.
 * Persistí varios productos y consultá solo los que superen un precio mínimo,
 * ordenados por precio descendente, usando parámetros con nombre (:precioMinimo).
 */
public class Ejercicio4App {

    public static void main(String[] args) {
        // TODO 1: persistir al menos 3 productos con precios distintos
        // TODO 2: JPQL: "select p from Producto p where p.precio > :precioMinimo order by p.precio desc"
        // TODO 3: bindear el parámetro con query.setParameter("precioMinimo", new BigDecimal("10000"))
        // TODO 4: recorrer getResultList() e imprimir el resultado filtrado

        System.out.println("Ejercicio 4 sin resolver todavía.");
    }
}

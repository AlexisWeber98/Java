package ejercicios;

/**
 * Ejercicio 4 · Consultas JPQL con filtro.
 * JPQL consulta OBJETOS (from Producto), no tablas; los parámetros van con :nombre.
 */
public class Ejercicio4ConsultasJPQLFiltro {

    public static void main(String[] args) {
        // TODO 1: persistir cuatro productos con precios bien distintos y hacer commit
        // TODO 2: armar la consulta:
        //         select p from Producto p where p.precio > :minimo order by p.precio desc
        // TODO 3: bindear el parámetro con setParameter("minimo", new BigDecimal(...))
        // TODO 4: recorrer getResultList() e imprimir solo los que superan el mínimo

        System.out.println("Ejercicio 4 sin resolver todavía.");
    }
}

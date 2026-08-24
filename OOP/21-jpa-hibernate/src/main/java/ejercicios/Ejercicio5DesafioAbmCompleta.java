package ejercicios;

/**
 * Ejercicio 5 · Desafío integrador: ABM completa + reporte final.
 * Alta de dos productos, modificación de uno, baja del otro,
 * y un reporte con lo que quedó más el valor total del inventario (suma con BigDecimal).
 * Pista: hacé el reporte desde un EntityManager NUEVO para ver lo realmente confirmado.
 */
public class Ejercicio5DesafioAbmCompleta {

    public static void main(String[] args) {
        // TODO 1: ALTA: persistir dos productos dentro de una transacción
        // TODO 2: MODIFICACIÓN: cambiarle el precio a uno (dirty checking, sin update())
        // TODO 3: BAJA: eliminar el otro por id (find + remove) y confirmar todo con commit
        // TODO 4: REPORTE: con otro EntityManager, listar los restantes ordenados por nombre
        //         y calcular el valor total del inventario sumando precios con BigDecimal

        System.out.println("Ejercicio 5 sin resolver todavía.");
    }
}

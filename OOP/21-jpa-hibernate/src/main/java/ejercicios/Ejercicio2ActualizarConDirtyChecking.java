package ejercicios;

/**
 * Ejercicio 2 · Actualizar con dirty checking.
 * No existe ProductoDao.update(): cambiás el campo y Hibernate genera el UPDATE solo.
 */
public class Ejercicio2ActualizarConDirtyChecking {

    public static void main(String[] args) {
        // TODO 1: persistir un producto inicial y confirmarlo (commit) para que quede en la base
        // TODO 2: con OTRO EntityManager, buscarlo con find(), arrancar transacción
        //         y cambiarle el precio SIN llamar a ningún método de guardado
        // TODO 3: hacer commit (acá sale el UPDATE por dirty checking)
        // TODO 4: con un tercer EntityManager, volver a buscarlo e imprimir el precio actualizado

        System.out.println("Ejercicio 2 sin resolver todavía.");
    }
}

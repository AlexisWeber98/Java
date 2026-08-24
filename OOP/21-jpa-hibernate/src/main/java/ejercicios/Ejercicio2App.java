package ejercicios;

/**
 * Ejercicio 2 · Actualizar con dirty checking.
 * Buscá un producto con find(), cambiale el precio SIN llamar a ningún update(),
 * hacé commit y verificá con un segundo EntityManager que el cambio quedó en la base.
 */
public class Ejercicio2App {

    public static void main(String[] args) {
        // TODO 1: persistir un producto de partida (o usar uno existente)
        // TODO 2: con OTRO EntityManager, hacer find() del producto y cambiarle setPrecio()
        //         (no invoques ninguna consulta de actualización: eso es dirty checking)
        // TODO 3: commit y cerrar ese EntityManager
        // TODO 4: abrir un TERCER EntityManager, volver a hacer find() e imprimir:
        //         si muestra el precio nuevo, Hibernate generó el UPDATE solo

        System.out.println("Ejercicio 2 sin resolver todavía.");
    }
}

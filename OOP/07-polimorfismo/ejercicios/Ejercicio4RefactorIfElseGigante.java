/*
 * ============================================================================
 * Módulo 07 – Polimorfismo | Ejercicio 4: Refactor del if-else gigante
 * ============================================================================
 *
 * ENUNCIADO:
 *   Este archivo compila y "funciona", pero es una trampa: hacerSonido(String)
 *   elige el sonido con una cadena de if-else por tipo. Tu misión es
 *   reemplazarla por una jerarquía polimórfica Animal -> Perro/Gato/Vaca/Pato.
 *
 * REQUISITOS:
 *   1. Creá la clase abstracta Animal con el método abstracto hacerSonido().
 *   2. Creá Perro, Gato, Vaca y Pato, cada uno con SU sonido (@Override).
 *   3. En main, reemplazá el arreglo de Strings por un Animal[] y recorrélo
 *      imprimiendo tipo y sonido SIN tocar ninguna cadena de if.
 *   4. Borrá el método hacerSonido(String): ya no lo necesitás.
 *   5. Bonus de valientes: agregá Dragon como clase nueva y verificá que el
 *      bucle NO necesitó ni un cambio.
 *
 * PISTAS:
 *   - El if-else viejo era un "switch manual por tipo": eso es señal clara de
 *     que falta polimorfismo.
 *   - Para imprimir el tipo usá animal.getClass().getSimpleName().
 *   - Regla de oro del refactor: primero hacé andar la versión nueva entera,
 *     después recién ahí borrá el código legado.
 *
 * Ejecución:  java Ejercicio4RefactorIfElseGigante.java
 */
public class Ejercicio4RefactorIfElseGigante {

    // ⚠️ CÓDIGO LEGADO: cada especie nueva obliga a abrir y editar este método.
    static String hacerSonido(String tipoAnimal) {
        if (tipoAnimal.equals("perro")) {
            return "Guau";
        } else if (tipoAnimal.equals("gato")) {
            return "Miau";
        } else if (tipoAnimal.equals("vaca")) {
            return "Muu";
        } else if (tipoAnimal.equals("pato")) {
            return "Cuac";
        } else {
            return "...";
        }
    }

    public static void main(String[] args) {
        String[] tipos = { "perro", "gato", "vaca", "pato", "dragón" };

        for (String tipo : tipos) {
            System.out.println(tipo + " hace: " + hacerSonido(tipo));
        }

        // TODO: creá la jerarquía Animal y reemplazá TODO este enfoque.
        // Cuando termines, este archivo no debe tener NI UN if de tipo.
    }
}

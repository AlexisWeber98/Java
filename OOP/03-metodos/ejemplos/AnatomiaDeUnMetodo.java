/**
 * AnatomiaDeUnMetodo.java — Disección de un método, pieza por pieza.
 * Ejecutar: java ejemplos/AnatomiaDeUnMetodo.java
 */
public class AnatomiaDeUnMetodo {

    // ── PIEZA 1: modificadores + tipo de retorno + nombre + parámetros ──────

    // "public": cualquier clase puede llamarlo.
    // "static": pertenece a la clase; así el main puede usarlo sin crear objetos.
    // "int": tipo de retorno — este método SIEMPRE entrega un int al terminar.
    // "doble": nombre en verbo/acción, en camelCase.
    // "(int numero)": parámetro — variable local que nace con el valor pasado.
    public static int doble(int numero) {
        return numero * 2;   // "return" entrega el valor Y termina el método.
    }

    // Un método void NO devuelve nada: hace su trabajo y corta solo.
    // Igual puede usar "return;" sin valor para salir antes de tiempo.
    public static void saludar(String nombre, boolean formal) {
        if (nombre == null) {
            System.out.println("No hay nadie para saludar");
            return;   // salida anticipada: lo que sigue no se ejecuta.
        }
        if (formal) {
            System.out.println("Buenos días, " + nombre);
        } else {
            System.out.println("¡Hola, " + nombre + "!");
        }
    }

    // Varios parámetros se separan por coma; cada uno con su tipo.
    // El orden importa: presentar("Ada", true) != presentar(true, "Ada").
    public static String presentar(String nombre, int edad) {
        return nombre + " tiene " + edad + " años";
    }

    // La FIRMA es nombre + tipos de parámetros EN ORDEN.
    // Estas dos firmas son distintas entre sí... pero idénticas si solo
    // cambian los NOMBRES de los parámetros: eso NO permite sobrecargar.
    public static double areaRectangulo(double base, double altura) {
        return base * altura;
    }

    // ── LLAMADAS: cada forma de invocar y qué pasa con el resultado ─────────

    public static void main(String[] args) {
        // Llamada con captura del retorno: el valor queda disponible.
        int resultado = doble(21);          // 42
        System.out.println("El doble de 21 es " + resultado);

        // También podés usar el retorno directo, sin guardarlo.
        System.out.println("El doble de 7 es " + doble(7));

        // Método void: se llama y listo; NO produce valor para asignar.
        saludar("Ana", true);
        saludar("Bruno", false);
        saludar(null, false);

        // Argumentos en orden: "Ada" va a nombre, 36 va a edad.
        String ficha = presentar("Ada", 36);
        System.out.println(ficha);

        // El nombre del argumento acá es independiente del parámetro adentro.
        double ancho = 3.0;
        double largo = 4.5;
        System.out.println("Área: " + areaRectangulo(ancho, largo));
    }
}

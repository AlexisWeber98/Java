import java.util.Scanner;

/*
 * ParsingSeguroDeString.java
 * Módulo 13 · Conversiones de tipos
 *
 * Todo input de usuario llega como String. Convertirlo puede explotar:
 * parseInt/parseDouble lanzan NumberFormatException con basura.
 * Acá vemos la versión ingenua (comentada) y la receta segura reutilizable.
 */
public class ParsingSeguroDeString {

    public static void main(String[] args) {
        demostrarCrashVersion();
        System.out.println();
        demostrarRecetaSegura();
        System.out.println();
        conversacionConUsuario();
    }

    /*
     * VERSIÓN INGENUINA: sin red de seguridad.
     * Descomentá la línea marcada y corré: la app muere con NumberFormatException.
     */
    static void demostrarCrashVersion() {
        System.out.println("=== LA VERSION QUE EXPLOTA ===");

        int edadValida = Integer.parseInt("42");
        System.out.println("parseInt(\"42\") = " + edadValida);

        // int edadBasura = Integer.parseInt("cuarenta y dos");
        // -> Exception in thread "main" java.lang.NumberFormatException:
        //    For input string: "cuarenta y dos"

        System.out.println("-> Un solo texto inválido y el programa muere.");
    }

    /*
     * RECETA SEGURA: try/catch en un helper que se reutiliza en todo el programa.
     */
    static void demostrarRecetaSegura() {
        System.out.println("=== LA RECETA SEGURA ===");

        String[] textos = { "1.75", "  3.5 ", "hola", "" };

        for (String texto : textos) {
            Double altura = parsearDoubleSeguro(texto);
            if (altura == null) {
                System.out.println("\"" + texto + "\" no es un número válido.");
            } else {
                System.out.println("\"" + texto + "\" parseado a double: " + altura);
            }
        }
        System.out.println("-> El programa sobrevive a cualquier basura del usuario.");
    }

    static Double parsearDoubleSeguro(String texto) {
        if (texto == null) {
            return null;
        }
        try {
            return Double.parseDouble(texto.trim());
        } catch (NumberFormatException error) {
            return null;
        }
    }

    /*
     * Camino inverso: de número a texto, y formateo con printf.
     */
    static void conversacionConUsuario() {
        System.out.println("=== DE NÚMERO A STRING + FORMATO ===");

        Scanner lector = new Scanner(System.in);
        System.out.print("Ingresá tu edad: ");
        String entrada = lector.nextLine();

        Integer edad = parsearEnteroSeguro(entrada);
        if (edad == null) {
            System.out.println("Eso no es una edad válida, pero sigo vivo. ;)");
        } else {
            String mensaje = String.valueOf(edad); // número -> texto explícito
            double promedioTeorico = edad;         // widening gratis
            System.out.printf("Edad %s (%.1f como double): bien ahí.%n",
                    mensaje, promedioTeorico);
        }
        lector.close();
    }

    static Integer parsearEnteroSeguro(String texto) {
        if (texto == null) {
            return null;
        }
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException error) {
            return null;
        }
    }
}

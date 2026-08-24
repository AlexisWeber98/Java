// Primer contacto con try/catch: dos fallas clásicas, manejadas con elegancia.
// Ejecutar: java PrimerTryCatch.java

public class PrimerTryCatch {

    public static void main(String[] args) {
        System.out.println("--- División por cero ---");

        // La JVM lanza ArithmeticException al dividir un entero por cero.
        try {
            int resultado = dividir(10, 0);
            System.out.println("Resultado: " + resultado);
        } catch (ArithmeticException e) {
            System.out.println("Operación cancelada: " + e.getMessage());
        }

        // El flujo continúa: el programa NO murió.
        System.out.println("\n--- Input no numérico ---");

        String[] entradas = { "42", "hola", "17" };

        for (String entrada : entradas) {
            try {
                int numero = Integer.parseInt(entrada); // puede lanzar NumberFormatException
                System.out.println("'" + entrada + "' parseado a " + numero);
            } catch (NumberFormatException e) {
                // Entrada inválida: avisamos y seguimos con la siguiente.
                System.out.println("'" + entrada + "' no es un número válido");
            }
        }

        System.out.println("\nEl programa llegó hasta el final sin morir.");
    }

    // Método que confía en su llamada y deja que la excepción viaje.
    static int dividir(int dividendo, int divisor) {
        return dividendo / divisor; // aquí se lanza si divisor es 0
    }
}

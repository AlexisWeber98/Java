// Sobrescritura vs sobrecarga, lado a lado:
// - Sobrescritura (@Override): misma firma, se resuelve en RUNTIME por el tipo REAL del objeto.
// - Sobrecarga: mismo nombre, firma distinta, se resuelve en COMPILACIÓN por los tipos de los argumentos.
//
// Ejecutar: java SobrescrituraVsSobrecarga.java

class Notificacion {
    // Versión base que las subclases van a sobrescribir.
    public void enviar(String mensaje) {
        System.out.println("Enviando genérico: " + mensaje);
    }
}

class Email extends Notificacion {
    @Override
    public void enviar(String mensaje) { // MISMA firma → SOBRESCRITURA
        System.out.println("[EMAIL] " + mensaje);
    }
}

class Sms extends Notificacion {
    @Override
    public void enviar(String mensaje) {
        System.out.println("[SMS] " + mensaje);
    }
}

public class SobrescrituraVsSobrecarga {

    // SOBRECARGA: tres métodos con el MISMO nombre y firmas distintas.
    static int sumar(int a, int b) {
        return a + b;
    }

    static double sumar(double a, double b) {
        return a + b;
    }

    static int sumar(int a, int b, int c) {
        return a + b + c;
    }

    public static void main(String[] args) {
        System.out.println("=== SOBRESCRITURA: se decide en RUNTIME ===");

        Notificacion canal = new Email();
        canal.enviar("Hola desde una variable de tipo Notificacion"); // ejecuta Email.enviar

        canal = new Sms();
        canal.enviar("El objeto cambió"); // ahora ejecuta Sms.enviar

        // El compilador solo verificó que Notificacion declare enviar(String).
        // Qué versión corre lo define el TIPO REAL del objeto en runtime.

        System.out.println("\n=== SOBRECARGA: se decide en COMPILACIÓN ===");
        System.out.println("sumar(2, 3)       -> " + sumar(2, 3));        // (int, int)
        System.out.println("sumar(2.5, 3.1)   -> " + sumar(2.5, 3.1));    // (double, double)
        System.out.println("sumar(1, 2, 3)    -> " + sumar(1, 2, 3));     // (int, int, int)

        // OJO: la sobrecarga NO mira el tipo real del objeto, mira los tipos declarados.
        Notificacion emailComoNotificacion = new Email();
        System.out.println("\nLlamamos enviar(...) con variable tipo Notificacion:");
        emailComoNotificacion.enviar("mensaje"); // ¿a cuál sobrecarga va? A la única existente...
        // ...pero si hubiera varias sobrecargas, la elección ya quedó hecha al compilar,
        // aunque en runtime el objeto sea un Email o un Sms.

        System.out.println("\n=== RESUMEN IMPRIMIDO ===");
        System.out.println("@Override  -> misma firma | resuelve en RUNTIME (tipo del OBJETO)");
        System.out.println("Sobrecarga -> otra firma   | resuelve en COMPILACIÓN (tipos de los ARGUMENTOS)");
    }
}

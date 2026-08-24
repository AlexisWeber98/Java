/*
 * =============================================================================
 *  Ejercicio 4 — Programar contra la interfaz
 *  Módulo 09 · Interfaces
 * =============================================================================
 *
 *  ENUNCIADO (refactorización guiada)
 *  ----------------------------------
 *  El starter FUNCIONA... pero ProcesadorDeAlertas está soldado a la clase
 *  concreta NotificadorEmail. Mañana te piden avisos por SMS y el diseño se
 *  rompe. Tu misión:
 *    1. Extraé la interfaz Notificable con el método
 *       void enviar(String destino, String mensaje).
 *    2. Hacé que NotificadorEmail implemente Notificable.
 *    3. Creá NotificadorSMS con su propia forma de enviar.
 *    4. Cambiá la firma a procesarAlertas(Notificable canal, String alerta).
 *    5. Demostrá desde main que EL MISMO método acepta email y SMS sin haber
 *       tocado una línea de ProcesadorDeAlertas.
 *
 *  REQUISITOS
 *  ----------
 *    - Interfaz Notificable + dos implementaciones (email, SMS).
 *    - procesarAlertas depende SOLO del tipo interfaz.
 *    - Ningún cambio en la lógica del procesador al sumar un canal nuevo.
 *
 *  PISTAS
 *  ------
 *    - Regla de oro: "dependé de abstracciones, no de concreciones".
 *    - Un canal NUEVO (¿WhatsApp?) debería ser solo una clase nueva; si
 *      necesitás tocar ProcesadorDeAlertas para sumarlo, algo quedó acoplado.
 * =============================================================================
 */
public class Ejercicio4ProgramarContraLaInterfaz {

    // Canal concreto: sabe ENVIAR EMAILS y nada más.
    static class NotificadorEmail {
        void enviar(String destino, String mensaje) {
            System.out.printf("[EMAIL -> %s] %s%n", destino, mensaje);
        }
    }

    static class ProcesadorDeAlertas {
        // ACOPLAMIENTO: la firma exige un NotificadorEmail concreto.
        void procesarAlertas(NotificadorEmail canal, String alerta) {
            canal.enviar("guardia@empresa.com", alerta);
        }

        // TODO 4: cambiá el tipo del parámetro por la interfaz Notificable.
    }

    public static void main(String[] args) {
        ProcesadorDeAlertas procesador = new ProcesadorDeAlertas();
        procesador.procesarAlertas(new NotificadorEmail(), "CPU al 95%");

        System.out.println("(Stub) Ahora extraé Notificable y sumá NotificadorSMS.");
        // TODO 5: pasale un NotificadorSMS al MISMO procesador, sin tocarlo.
        // TODO 1: declará interface Notificable { void enviar(String destino,
        //         String mensaje); }
        // TODO 2: NotificadorEmail implements Notificable.
        // TODO 3: creá NotificadorSMS implements Notificable (println propio).
    }
}

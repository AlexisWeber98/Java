/*
 * =============================================================================
 *  Ejercicio 4 — Programar contra la interfaz (SOLUCIÓN)
 *  Módulo 09 · Interfaces
 * =============================================================================
 *
 *  Idea clave: ANTES, ProcesadorDeAlertas estaba soldado a NotificadorEmail;
 *  sumar un canal exigía tocarlo (y re-testearlo). DESPUÉS, depende del
 *  contrato Notificable: cada canal nuevo es SOLO una clase nueva. El
 *  procesador ni se entera: eso es estar desacoplado (abierto a extensión,
 *  cerrado a modificación).
 * =============================================================================
 */
// Sin modificador y con sufijo Solucion: evita colisionar con el starter
// al compilar ambos directorios juntos; java Ejercicio4ProgramarContraLaInterfaz.java
// sigue funcionando porque ejecuta la primera clase del archivo.
class Ejercicio4ProgramarContraLaInterfazSolucion {

    // La abstracción: el único tipo que ProcesadorDeAlertas conoce.
    interface Notificable {
        void enviar(String destino, String mensaje);
    }

    static class NotificadorEmail implements Notificable {
        @Override
        public void enviar(String destino, String mensaje) {
            System.out.printf("[EMAIL -> %s] %s%n", destino, mensaje);
        }
    }

    // Canal nuevo: clase nueva, cero cambios en el resto del sistema.
    static class NotificadorSMS implements Notificable {
        @Override
        public void enviar(String destino, String mensaje) {
            System.out.printf("[SMS -> %s] %s%n", destino, mensaje);
        }
    }

    static class ProcesadorDeAlertas {
        // DESACOPLADO: acepta CUALQUIER cosa que sepa notificar.
        // Sumar WhatsApp mañana = una clase más; este método no se toca.
        void procesarAlertas(Notificable canal, String alerta) {
            canal.enviar("guardia@empresa.com", alerta);
        }
    }

    public static void main(String[] args) {
        ProcesadorDeAlertas procesador = new ProcesadorDeAlertas();

        // El MISMO método, dos canales distintos: solo cambia la implementación.
        procesador.procesarAlertas(new NotificadorEmail(), "CPU al 95%");
        procesador.procesarAlertas(new NotificadorSMS(), "Backup falló");
    }
}

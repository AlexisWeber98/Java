/*
 * =============================================================================
 *  Ejercicio 3 — Default: usado tal cual vs. sobrescrito (SOLUCIÓN)
 *  Módulo 09 · Interfaces
 * =============================================================================
 *
 *  Idea clave: el método default es comportamiento heredado de la interfaz.
 *  NotaRapida lo usa tal cual; MensajeFormal lo sobrescribe y REUTILIZA la
 *  versión base con Notificable.super.prepararMensaje(...) en lugar de copiarla.
 * =============================================================================
 */
// Sin modificador y con sufijo Solucion: evita colisionar con el starter
// al compilar ambos directorios juntos; java Ejercicio3DefaultUsadoYSobrescrito.java
// sigue funcionando porque ejecuta la primera clase del archivo.
class Ejercicio3DefaultUsadoYSobrescritoSolucion {

    interface Notificable {
        default String prepararMensaje(String texto) {
            return "(aviso) " + texto;
        }
    }

    static class NotaRapida implements Notificable {
        // Hereda el default sin escribir nada: uso directo del contrato.
    }

    static class MensajeFormal implements Notificable {
        // Sobrescribe: nuestra versión GANA sobre la heredada.
        @Override
        public String prepararMensaje(String texto) {
            // Sintaxis clave para defaults: InterfaceName.super.metodo(...).
            String cuerpo = Notificable.super.prepararMensaje(texto);
            return "Estimado/a cliente:\n  " + cuerpo
                    + "\nAtentamente, el equipo.";
        }
    }

    public static void main(String[] args) {
        NotaRapida rapida = new NotaRapida();
        MensajeFormal formal = new MensajeFormal();

        System.out.println(rapida.prepararMensaje("Reunión de equipo a las 15"));
        System.out.println(formal.prepararMensaje("Su factura Nº 1001 venció."));
    }
}

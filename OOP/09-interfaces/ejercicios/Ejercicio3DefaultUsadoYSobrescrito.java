/*
 * =============================================================================
 *  Ejercicio 3 — Default: usado tal cual vs. sobrescrito
 *  Módulo 09 · Interfaces
 * =============================================================================
 *
 *  ENUNCIADO
 *  ---------
 *  Desde Java 8 una interfaz puede ofrecer métodos default: implementación
 *  lista para usar que las clases firmantes HEREDAN sin escribir una línea.
 *    1. Mirá la interfaz Notificable: ya trae prepararMensaje() como default.
 *    2. NotaRapida lo usa TAL CUAL (clase vacía, cero esfuerzo).
 *    3. En MensajeFormal SOBRESCRIVÍ el método para darle formato formal,
 *       reutilizando la versión por defecto con Notificable.super.prepararMensaje(...).
 *    4. Ejecutá el starter ANTES de resolverlo: los dos mensajes salen
 *       genéricos. Después del override, MensajeFormal tiene vida propia.
 *
 *  REQUISITOS
 *  ----------
 *    - No modificar la interfaz ni NotaRapida.
 *    - MensajeFormal sobrescribe prepararMensaje() con @Override y llama
 *      al menos una vez a la versión default vía InterfaceName.super.
 *
 *  PISTAS
 *  ------
 *    - El default es un comportamiento heredado DE LA INTERFAZ, no de una
 *      clase madre: por eso se invoca con Notificable.super.metodo(...).
 *    - Si no lo sobrescribís, heredás el default tal cual; si lo sobrescribís,
 *      TU versión gana. Elegancia: reutilizar y sumar, no copiar y pegar.
 * =============================================================================
 */
public class Ejercicio3DefaultUsadoYSobrescrito {

    interface Notificable {
        // Implementación por defecto: quien firme el contrato la hereda gratis.
        default String prepararMensaje(String texto) {
            return "(aviso) " + texto;
        }
    }

    static class NotaRapida implements Notificable {
        // Usa el default TAL CUAL: clase vacía, cero código duplicado.
    }

    static class MensajeFormal implements Notificable {
        // TODO 1: sobrescribí prepararMensaje() con @Override.
        //         Arma un mensaje formal ("Estimado/a cliente:", cuerpo,
        //         despedida "Atentamente, el equipo.") reutilizando la
        //         versión por defecto con Notificable.super.prepararMensaje(texto).
    }

    public static void main(String[] args) {
        NotaRapida rapida = new NotaRapida();
        MensajeFormal formal = new MensajeFormal();

        System.out.println(rapida.prepararMensaje("Reunión de equipo a las 15"));
        System.out.println(formal.prepararMensaje("Su factura Nº 1001 venció."));
    }
}

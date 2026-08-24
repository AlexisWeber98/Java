/*
 * ============================================================================
 *  Ejercicio 2 — Estados independientes · SOLUCIÓN
 *  Módulo 02 · Clases y objetos
 * ============================================================================
 *
 *  IDEA CLAVE
 *  La clase es el plano; cada objeto es una casa distinta construida con ese
 *  plano. alimentar() sobre rocco no toca a Lola porque cada uno tiene SU
 *  propia copia de energia en memoria. El método siempre trabaja sobre el
 *  objeto que recibe el mensaje.
 */
// Sin public y con nombre Solucion*: así ejercicios y soluciones compilan juntos.
class Solucion2EstadosIndependientes {

    public static void main(String[] args) {
        Mascota rocco = new Mascota();
        Mascota lola = new Mascota();   // segundo objeto, misma clase
        rocco.nombre = "Rocco";
        lola.nombre = "Lola";

        System.out.println("--- Estado inicial ---");
        rocco.mostrarEstado();
        lola.mostrarEstado();

        rocco.alimentar();   // SOLO Rocco recibe este mensaje

        System.out.println("--- Después de alimentar a Rocco ---");
        rocco.mostrarEstado();   // energía subió a 15
        lola.mostrarEstado();    // Lola sigue en 0: estados independientes
    }

    static class Mascota {
        String nombre;
        int energia;   // valor por defecto: 0

        void alimentar() {
            energia = energia + 15;   // muta la copia del objeto receptor
        }

        void mostrarEstado() {
            System.out.println(nombre + " tiene " + energia + " de energía.");
        }
    }
}

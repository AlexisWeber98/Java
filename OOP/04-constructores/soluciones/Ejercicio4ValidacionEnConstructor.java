/*
 * ============================================================================
 *  Ejercicio 4 — Validación en el constructor · SOLUCIÓN COMENTADA
 *  Módulo 04 · Constructores
 * ============================================================================
 *  Idea clave: fail fast. Si el dato es imposible, el objeto no nace y el
 *  error se grita en el momento exacto, con contexto.
 *
 *  Alternativa "normalizadora" (discutida, no elegida):
 *      this.edad = Math.max(edad, 0);
 *  Con eso el programa sigue silenciosamente... pero un -5 casi seguro es
 *  un bug aguas arriba (una cuenta mal restada, un formulario sin validar).
 *  Normalizar lo esconde; la excepción lo hace visible HOY, con el valor
 *  culpable en el mensaje. Fallar temprano y ruidoso gana.
 *
 *  Bonus de arquitectura: como el canónico del Ejercicio 3 concentra la
 *  inicialización, acá la validación también vive en UN solo lugar. DRY y
 *  validación se llevan muy bien.
 * ============================================================================
 */

public class Ejercicio4ValidacionEnConstructor {

    public static void main(String[] args) {
        Alumno alumna = new Alumno("Camila", 21);
        alumna.mostrarEstado();

        try {
            Alumno fantasma = new Alumno("Nadie", -5);
            fantasma.mostrarEstado(); // nunca llega: el objeto no llegó a nacer
        } catch (IllegalArgumentException e) {
            System.out.println("Objeto rechazado: " + e.getMessage());
        }

        System.out.println("El programa sigue vivo: rechazar no es tirar todo abajo.");
    }
}

class Alumno {

    private final String nombre;
    private final int edad;

    Alumno(String nombre, int edad) {
        // Guardá ANTES de asignar: si falla, no dejamos atributos a medio escribir.
        if (edad < 0) {
            throw new IllegalArgumentException(
                    "La edad no puede ser negativa, recibimos: " + edad);
        }
        this.nombre = nombre;
        this.edad = edad;
    }

    void mostrarEstado() {
        System.out.printf("Alumno -> nombre=%s, edad=%d%n", nombre, edad);
    }
}

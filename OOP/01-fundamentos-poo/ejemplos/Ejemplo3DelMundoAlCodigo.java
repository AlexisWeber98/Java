/*
 * EJEMPLO 3: Del mundo real al codigo.
 *
 * La escena (cerrá los ojos, imaginala): en una veterinaria llega
 * "Mecha, una gata de 3 años, con turno para vacunarse el lunes".
 *
 * Como PASAMOS esa escena a clases:
 *
 *   PASO 1 - Sustantivos importantes -> candidatos a CLASE.
 *            Mascota y Turno. ("Veterinaria" existe pero no hace falta
 *            modelarla para este ejemplo; no todo sustantivo se convierte
 *            en clase.)
 *   PASO 2 - Datos de cada sustantivo -> CAMPOS de la clase.
 *            Mascota: nombre, especie. Turno: mascota, motivo, dia.
 *   PASO 3 - Verbos / responsabilidades -> METODOS.
 *            Mascota: describirse. Turno: confirmar() (usa su mascota).
 *
 * Fijate como `confirmar()` NO recibe la mascota por parámetro: el turno
 * GUARDA a su mascota adentro. Eso es colaboración entre objetos.
 *
 * Corrélo con: java Ejemplo3DelMundoAlCodigo.java
 */
public class Ejemplo3DelMundoAlCodigo {

    public static void main(String[] args) {

        // Primero creamos las piezas del mundo...
        Mascota mecha = new Mascota("Mecha", "gata");
        // ...y despues las conectamos entre ellas.
        Turno vacunacion = new Turno(mecha, "vacunacion anual", "lunes");

        System.out.println("--- Escena original ---");
        System.out.println(vacunacion.resumen());
        mecha.describir();

        System.out.println("--- Confirmamos el turno ---");
        vacunacion.confirmar();
    }
}

/*
 * Clase 1 del diseño: Mascota (sustantivo con datos y un verbo propio).
 * Corre standalone junto al archivo principal, sin declarar "package".
 */
class Mascota {

    // PASO 2: sus datos.
    String nombre;
    String especie;

    Mascota(String nombreInicial, String especieInicial) {
        this.nombre = nombreInicial;
        this.especie = especieInicial;
    }

    // PASO 3: su verbo.
    void describir() {
        System.out.println(especie + " llamada/o " + nombre);
    }
}

/*
 * Clase 2 del diseño: Turno.
 * COLABORACIÓN: tiene un campo del tipo Mascota. Los objetos se
 * referencian entre sí, igual que en el mundo real el turno "pertenece"
 * a una mascota concreta.
 */
class Turno {

    // PASO 2: sus datos, incluyendo UNA REFERENCIA a otro objeto.
    Mascota mascota;
    String motivo;
    String dia;

    Turno(Mascota mascotaDelTurno, String motivoInicial, String diaInicial) {
        this.mascota = mascotaDelTurno;
        this.motivo = motivoInicial;
        this.dia = diaInicial;
    }

    // PASO 3: su verbo usa AL OBJETO COLABORADOR interno.
    void confirmar() {
        System.out.println("Turno confirmado para " + mascota.nombre
                + " el " + dia + ": " + motivo + ".");
    }

    String resumen() {
        return dia + " -> " + motivo + " de " + mascota.nombre;
    }
}

/*
 * ============================================
 *  Solución 5: Diseña tu propio mundo
 * ============================================
 * Dominio de ejemplo: Veterinaria, con dos clases que colaboran:
 * Mascota (el paciente) y Turno (la consulta, que CONOCE a su mascota).
 */
class Solucion5DisenaTuPropioMundo {

    public static void main(String[] args) {
        // Escena: llega Lola a su control anual.
        Mascota mascota = new Mascota();
        mascota.nombre = "Lola";
        mascota.especie = "gata";
        mascota.edad = 3;

        Turno turno = new Turno();
        turno.fechaHora = "lunes 10:00";
        turno.motivo = "control anual";
        turno.mascota = mascota;   // <- la colaboración: el turno guarda al paciente

        turno.confirmar();
    }
}

/*
 * El paciente de la veterinaria: sabe presentarse.
 */
class Mascota {
    String nombre;
    String especie;
    int edad;

    void presentar() {
        System.out.println(nombre + " (" + especie + ", " + edad + " años)");
    }
}

/*
 * La consulta agendada. Fijate el campo del tipo Mascota: eso es colaboración
 * entre objetos, un objeto dentro de otro.
 */
class Turno {
    String fechaHora;
    String motivo;
    Mascota mascota;

    void confirmar() {
        System.out.println("Turno confirmado | " + fechaHora + " | motivo: " + motivo);
        System.out.print("Paciente: ");
        mascota.presentar();   // el turno delega en la mascota: cada clase hace lo suyo
    }
}

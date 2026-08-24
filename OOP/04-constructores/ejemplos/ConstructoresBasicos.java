// ConstructoresBasicos.java — constructor por defecto automático vs parametrizado
// y LA TRAMPA: escribir un constructor hace desaparecer el default.

public class ConstructoresBasicos {

    static class Gato {
        String nombre;

        void presentarse() {
            System.out.println("Soy un gato llamado: " + nombre);
        }
    }

    static class Perro {
        String nombre;

        // Constructor parametrizado. Al escribir ESTE, el constructor por
        // defecto de Perro (el que generaba el compilador) deja de existir.
        Perro(String nombre) {
            this.nombre = nombre;   // this.nombre = campo; nombre = parámetro
        }

        void presentarse() {
            System.out.println("Soy un perro llamado: " + nombre);
        }
    }

    public static void main(String[] args) {
        // 1) Gato no declara constructores -> el compilador genera uno vacío.
        Gato gato = new Gato();
        gato.presentarse();          // "null": nació con estado por defecto

        // 2) Perro SÍ declara constructor parametrizado.
        Perro perro = new Perro("Bobby");
        perro.presentarse();         // nace con nombre asignado

        // 3) LA TRAMPA, en vivo:
        // Antes de que Perro tuviera constructor parametrizado,
        // esta línea compilaba sin problemas (usaba el default generado):
        //
        //     Perro otro = new Perro();   // <-- DESCOMENTALA: error de compilación
        //
        // Hoy falla porque el default solo existe mientras NO escribís ninguno.
        System.out.println("¿Viste la trampa? Descomentá la línea indicada y rompela.");
    }
}

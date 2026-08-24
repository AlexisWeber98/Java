// Despacho dinámico: un mismo mensaje, respuestas distintas.
// El método que se ejecuta depende del tipo REAL del objeto en runtime,
// no del tipo de la variable que lo referencia.
//
// Ejecutar: java DespachoDinamico.java

class Animal {
    public void hacerSonido() {
        System.out.println("Algún sonido genérico de animal...");
    }
}

class Perro extends Animal {
    @Override
    public void hacerSonido() {
        System.out.println("Guau guau");
    }
}

class Gato extends Animal {
    @Override
    public void hacerSonido() {
        System.out.println("Miau");
    }
}

class Vaca extends Animal {
    @Override
    public void hacerSonido() {
        System.out.println("Muuu");
    }
}

public class DespachoDinamico {

    public static void main(String[] args) {
        // Una sola variable de tipo general, tres objetos reales distintos.
        Animal[] zoo = { new Perro(), new Gato(), new Vaca() };

        System.out.println("=== El zoológico suena ===");
        // Un único bucle escrito UNA vez contra el tipo general:
        for (Animal animal : zoo) {
            animal.hacerSonido(); // cada uno responde a su manera
        }

        // Extensión sin modificación: si mañana agregamos Pato,
        // este bucle NO cambia ni una línea. Solo entra al arreglo.
        System.out.println("\n=== Llega un pato nuevo al zoo ===");
        Animal[] zooAmpliado = { new Perro(), new Gato(), new Vaca(), new Pato() };
        for (Animal animal : zooAmpliado) {
            animal.hacerSonido();
        }

        System.out.println("\n=== La variable no decide, el objeto sí ===");
        Animal disfraz = new Perro(); // la variable dice "Animal"...
        System.out.println("Tipo de la variable: " + "Animal");
        System.out.print("Pero quien responde es: ");
        disfraz.hacerSonido(); // ...el objeto real (Perro) elige el método
    }
}

class Pato extends Animal {
    @Override
    public void hacerSonido() {
        System.out.println("Cuac cuac");
    }
}

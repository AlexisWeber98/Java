// ============================================================
// Módulo 09 · Ejemplo 2: una clase, varios contratos a la vez
// Ejecutar con: java MultiplesInterfaces.java
// ============================================================

// Tres contratos de capacidad. Cada uno chico y enfocado (ISP).
interface Volador {
    void volar();
}

interface Nadador {
    void nadar();
}

interface Caminante {
    void caminar();
}

// El pato ES-UN Ave (herencia), pero PUEDE volar, nadar y caminar:
// tres capacidades independientes que firma al mismo tiempo.
class Pato implements Volador, Nadador, Caminante {
    @Override
    public void volar() {
        System.out.println("El pato vuela en formación");
    }

    @Override
    public void nadar() {
        System.out.println("El pato rema con sus patitas");
    }

    @Override
    public void caminar() {
        System.out.println("El pato camina tambaleándose");
    }
}

// Un pez también nada... pero NO vuela ni camina. Contratos separados
// permiten compartir UNA capacidad sin heredar las demás.
class Pez implements Nadador {
    @Override
    public void nadar() {
        System.out.println("El pez nada como pez en el agua");
    }
}

public class MultiplesInterfaces {
    public static void main(String[] args) {
        Pato donald = new Pato();

        // La MISMA instancia vista a través de cada tipo de contrato:
        // cada referencia solo "ve" la promesa de SU interfaz.
        Volador   comoVolador   = donald;
        Nadador   comoNadador   = donald;
        Caminante comoCaminante = donald;

        comoVolador.volar();
        comoNadador.nadar();
        comoCaminante.caminar();

        // Y el polimorfismo por contrato cruza especies:
        Nadador nadador1 = donald;
        Nadador nadador2 = new Pez();
        nadador1.nadar(); // pato
        nadador2.nadar(); // pez

        // Legal porque las interfaces no traen estado: muchas capacidades,
        // una sola cadena de construcción. Con clases sería imposible.
    }
}

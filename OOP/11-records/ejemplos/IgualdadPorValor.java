// Módulo 11 — Igualdad por valor: la diferencia clave entre record y clase.
//
// Dos records con los mismos valores: == false, equals true.
// Dos clases SIN equals sobrescrito: ambos false (comparan referencias).
//
// Ejecutá: java IgualdadPorValor.java

public class IgualdadPorValor {

    record Coordenada(int x, int y) {}

    static final class PuntoClase {
        private final int x;
        private final int y;

        PuntoClase(int x, int y) {
            this.x = x;
            this.y = y;
        }
        // Sin equals ni hashCode sobrescritos:
        // hereda Object.equals → compara REFERENCIAS.
    }

    public static void main(String[] args) {
        System.out.println("── Records ──────────────────────────────");
        var a = new Coordenada(3, 4);
        var b = new Coordenada(3, 4);

        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("a == b      : " + (a == b));
        System.out.println("a.equals(b) : " + a.equals(b));

        // Consecuencia directa de equals/hashCode automáticos:
        var mapa = new java.util.HashMap<Coordenada, String>();
        mapa.put(a, "esquina");
        System.out.println("mapa.get(new Coordenada(3,4)) → "
                + mapa.get(new Coordenada(3, 4)));   // lo encuentra por valor

        System.out.println("\n── Clase sin equals ─────────────────────");
        var p1 = new PuntoClase(3, 4);
        var p2 = new PuntoClase(3, 4);

        System.out.println("p1 = " + p1);              // hash "raro": identidad, no datos
        System.out.println("p2 = " + p2);
        System.out.println("p1 == p2     : " + (p1 == p2));
        System.out.println("p1.equals(p2): " + p1.equals(p2));

        var mapaClase = new java.util.HashMap<PuntoClase, String>();
        mapaClase.put(p1, "esquina");
        System.out.println("mapa.get(p2) → "
                + mapaClase.get(p2));                // null: no encuentra "lo mismo"

        System.out.println("""

                MORAL:
                  - La clase compara IDENTIDAD: dos objetos con idénticos datos
                    son "distintos" salvo que escribas equals a mano.
                  - El record compara VALORES: si los componentes coinciden,
                    son intercambiables. Eso es lo que querés para datos.
                """);
    }
}

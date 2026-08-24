// Módulo 11 — El mismo modelo de datos, escrito de las dos maneras.
// Arriba: clase tradicional (todo a mano). Abajo: record (una línea).
//
// Conteo aproximado:
//   Clase DineroTradicional  → ~35 líneas de código repetitivo.
//   Record Dinero            → 1 línea + lo que agreguemos vos.
//
// Ejecutá: java RecordVsClaseTradicional.java

public class RecordVsClaseTradicional {

    // ── Versión tradicional: cada pieza escrita a mano ─────────────────
    static final class DineroTradicional {
        private final int centavos;
        private final String moneda;

        DineroTradicional(int centavos, String moneda) {
            this.centavos = centavos;
            this.moneda = moneda;
        }

        int getCentavos() { return centavos; }
        String getMoneda() { return moneda; }

        @Override public boolean equals(Object objeto) {
            if (this == objeto) return true;
            if (!(objeto instanceof DineroTradicional otro)) return false;
            return centavos == otro.centavos && moneda.equals(otro.moneda);
        }

        @Override public int hashCode() {
            return 31 * centavos + moneda.hashCode();
        }

        @Override public String toString() {
            return "Dinero[centavos=" + centavos + ", moneda=" + moneda + "]";
        }
    }

    // ── Versión record: el compilador genera TODO lo de arriba ────────
    // Constructor canónico, accesores, equals, hashCode y toString: gratis.
    record Dinero(int centavos, String moneda) {
        // Método propio: igual que en una clase común.
        Dinero sumar(Dinero otro) {
            if (!moneda.equals(otro.moneda)) {
                throw new IllegalArgumentException("monedas distintas");
            }
            return new Dinero(centavos + otro.centavos, moneda);
        }
    }

    public static void main(String[] args) {
        var manual = new DineroTradicional(1250, "ARS");
        var compacto = new Dinero(1250, "ARS");

        // Mismo comportamiento visible desde afuera:
        System.out.println("Clase : " + manual);
        System.out.println("Record: " + compacto);

        System.out.println("\nAccesores: " + compacto.centavos() + " " + compacto.moneda());
        System.out.println("Suma     : " + compacto.sumar(new Dinero(500, "ARS")));

        // Y la igualdad también sale igual... pero sin escribirla:
        System.out.println("\nequals clase : "
                + manual.equals(new DineroTradicional(1250, "ARS")));
        System.out.println("equals record: "
                + compacto.equals(new Dinero(1250, "ARS")));

        System.out.println("""
                
                Moral: la clase te costó ~35 líneas y tres chances de equivocarte.
                El record: una línea. Menos código = menos bugs.""");
    }
}

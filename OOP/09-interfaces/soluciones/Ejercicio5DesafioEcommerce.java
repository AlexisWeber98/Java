/*
 * =============================================================================
 *  Ejercicio 5 — Desafío: ecommerce con capacidades a la carta (SOLUCIÓN)
 *  Módulo 09 · Interfaces
 * =============================================================================
 *
 *  Idea clave: las interfaces modelan CAPACIDADES transversales. El bucle
 *  procesa Producto[] preguntando "¿sabés cobrarte? ¿te podés enviar?" con
 *  instanceof patrón. Nunca pregunta "¿sos ProductoFisico?": trabaja con
 *  capacidades, no con clases concretas.
 * =============================================================================
 */
// Sin modificador y con sufijo Solucion: evita colisionar con el starter
// al compilar ambos directorios juntos; java Ejercicio5DesafioEcommerce.java
// sigue funcionando porque ejecuta la primera clase del archivo.
class Ejercicio5DesafioEcommerceSolucion {

    // Capacidades independientes: se combinan según el producto.
    interface Pagable {
        void cobrar();
    }

    interface Enviable {
        double calcularEnvio();
    }

    interface Descontable {
        double aplicarDescuento(double porcentaje);
    }

    static class Producto {
        protected final String nombre;
        protected final double precioBase;

        Producto(String nombre, double precioBase) {
            this.nombre = nombre;
            this.precioBase = precioBase;
        }

        String getNombre() {
            return nombre;
        }

        double getPrecioBase() {
            return precioBase;
        }
    }

    static class ProductoFisico extends Producto implements Pagable, Enviable, Descontable {
        private static final double TARIFA_POR_KG = 900.0;
        private final double pesoKg;

        ProductoFisico(String nombre, double precioBase, double pesoKg) {
            super(nombre, precioBase);
            this.pesoKg = pesoKg;
        }

        @Override
        public void cobrar() {
            System.out.printf("  Cobro de $%.2f con tarjeta.%n", getPrecioBase());
        }

        @Override
        public double calcularEnvio() {
            return pesoKg * TARIFA_POR_KG; // regla propia del físico
        }

        @Override
        public double aplicarDescuento(double porcentaje) {
            validar(porcentaje);
            return getPrecioBase() * (1 - porcentaje / 100);
        }

        private static void validar(double porcentaje) {
            if (porcentaje <= 0 || porcentaje > 50) {
                throw new IllegalArgumentException(
                        "Descuento inválido: " + porcentaje + "% (permitido: 0-50]");
            }
        }
    }

    // Solo dos contratos: lo digital no viaja en camión.
    static class ProductoDigital extends Producto implements Pagable, Descontable {
        private final double tamanoMb;

        ProductoDigital(String nombre, double precioBase, double tamanoMb) {
            super(nombre, precioBase);
            this.tamanoMb = tamanoMb;
        }

        @Override
        public void cobrar() {
            System.out.printf("  Cobro de $%.2f con billetera virtual.%n", getPrecioBase());
        }

        @Override
        public double aplicarDescuento(double porcentaje) {
            if (porcentaje <= 0 || porcentaje > 50) {
                throw new IllegalArgumentException(
                        "Descuento inválido: " + porcentaje + "% (permitido: 0-50]");
            }
            return getPrecioBase() * (1 - porcentaje / 100);
        }
    }

    public static void main(String[] args) {
        Producto[] carrito = {
                new ProductoFisico("Teclado mecánico", 45_000.0, 1.2),
                new ProductoDigital("Curso de interfaces", 12_500.0, 850.0),
                new ProductoFisico("Monitor 27\"", 320_000.0, 6.5)
        };

        for (Producto producto : carrito) {
            System.out.println("--- " + producto.getNombre());

            // Ambos firmaron Pagable: cobramos sin mirar la clase.
            if (producto instanceof Pagable pagable) {
                pagable.cobrar();
            }

            // Patrón instanceof: chequea Y asigna en una sola línea.
            if (producto instanceof Enviable enviable) {
                System.out.printf("  Envío: $%.2f%n", enviable.calcularEnvio());
            } else {
                System.out.println("  Sin envío: entrega digital inmediata.");
            }

            if (producto instanceof Descontable descontable) {
                System.out.printf("  Precio con 10%% off: $%.2f%n",
                        descontable.aplicarDescuento(10));
            }
        }
    }
}

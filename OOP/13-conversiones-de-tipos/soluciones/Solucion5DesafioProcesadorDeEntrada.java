/*
 * Módulo 13 — Conversiones de tipos
 * Ejercicio 5: Desafío — Procesador de entrada (SOLUCIÓN)
 *
 * SALIDA REAL DEL PROGRAMA:
 *   ==== INFORME DE PROCESAMIENTO ====
 *   Entradas recibidas: 5
 *   Enteros válidos    : 2 (suma = 5)
 *   Decimales válidos  : 1 (suma = 3.5)
 *   Rechazados         : 2
 *     - [""]   -> motivo: texto vacío o nulo
 *     - ["abc"] -> motivo: no representa un número válido
 *
 * CLAVES DE LA SOLUCIÓN:
 * - Los helpers devuelven el WRAPPER (Integer/Double) y usan null como
 *   "no se pudo": conecta con los ejercicios 3 y 4. Cuidado: null exige
 *   chequear antes de desempaquetar, si no... NullPointerException.
 * - El orden de las pruebas es la clave del diseño: vacío -> entero ->
 *   decimal. Como "12" también es parseable como double, probar entero
 *   primero evita contar enteros en la columna equivocada.
 * - continue mantiene el bucle plano y legible: un motivo de salida por rama.
 */
import java.util.ArrayList;
import java.util.List;

public class Solucion5DesafioProcesadorDeEntrada {

    public static void main(String[] args) {
        String[] entrada = {"12", "3.5", "abc", "", "-7"};

        int sumaEnteros = 0;
        int contadorEnteros = 0;
        double sumaDecimales = 0.0;
        int contadorDecimales = 0;
        List<String> rechazados = new ArrayList<>();

        for (String texto : entrada) {
            // Regla 1: sin contenido no hay nada que convertir.
            if (texto == null || texto.isBlank()) {
                rechazados.add("[\"" + texto + "\"] -> motivo: texto vacío o nulo");
                continue;
            }

            String limpio = texto.trim();

            // Regla 2: probamos entero PRIMERO (si no, "12" contaría como decimal).
            Integer entero = intentarConvertirAEntero(limpio);
            if (entero != null) {
                sumaEnteros += entero;      // desempaquetado automático al sumar
                contadorEnteros++;
                continue;
            }

            // Regla 3: ahora sí, decimal.
            Double decimal = intentarConvertirADecimal(limpio);
            if (decimal != null) {
                sumaDecimales += decimal;
                contadorDecimales++;
                continue;
            }

            // Regla 4: sobrevivió a todo -> basura identificada.
            rechazados.add("[\"" + texto + "\"] -> motivo: no representa un número válido");
        }

        System.out.println("==== INFORME DE PROCESAMIENTO ====");
        System.out.println("Entradas recibidas: " + entrada.length);
        System.out.println("Enteros válidos    : " + contadorEnteros + " (suma = " + sumaEnteros + ")");
        System.out.println("Decimales válidos  : " + contadorDecimales + " (suma = " + sumaDecimales + ")");
        System.out.println("Rechazados         : " + rechazados.size());
        for (String rechazado : rechazados) {
            System.out.println("  - " + rechazado);
        }
    }

    // Devuelve el entero convertido, o null si no se pudo. Autoboxing trabajando
    // para nosotros... con su trampa incluida: NUNCA sumar sin chequear null.
    static Integer intentarConvertirAEntero(String texto) {
        try {
            return Integer.valueOf(texto);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    static Double intentarConvertirADecimal(String texto) {
        try {
            return Double.valueOf(texto);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

package util;

import java.util.Scanner;

import controlador.ConsolaController;
import humo.PruebaDeHumo;
import servicio.InventarioService;

/**
 * Punto de entrada que cablea las capas: repositorio -> servicio -> controlador.
 * Sin argumentos corre la demo guiada no interactiva (flujo completo CRUD);
 * con el argumento "interactivo" abre el menú de consola.
 */
public class AppMain {

    public static void main(String[] args) {
        try {
            if (args.length > 0 && "interactivo".equalsIgnoreCase(args[0])) {
                InventarioService servicio = new InventarioService();
                ConsolaController controlador = new ConsolaController(servicio, new Scanner(System.in));
                controlador.ejecutar();
            } else {
                System.out.println("(sin argumento 'interactivo': se ejecuta la demo guiada)");
                boolean ok = PruebaDeHumo.ejecutarFlujoCompleto();
                System.out.println(ok
                        ? "Demo guiada completada correctamente."
                        : "La demo guiada detectó fallos.");
                if (!ok) {
                    System.exit(1);
                }
            }
        } finally {
            JpaUtil.cerrar();
        }
    }
}

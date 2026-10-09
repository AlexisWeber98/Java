import java.util.Scanner;

/*
 * ------------------------ EJERCICIO ESTACIONAMIENTO ----------------------------
 *
 *  Un administrador de un estacionamiento necesita un programa que permita
 *  calcular el total a pagar de varios vehículos que utilizan el servicio.
 *
 *  El programa debe solicitar por teclado dos datos:
 *  - patente del vehículo
 *  - tipo de estacionamiento
 *
 *  Los tipos de estacionamiento disponibles son 3:
 *  - Por Hora (valor de $3 por hora)
 *  - Media Jornada (valor total de $15 con 5% de descuento)
 *  - Jornada Completa (valor total de $30 con 10% de descuento)
 *
 *  El programa debe calcular el monto a pagar para cada cliente en función del
 *  tipo de estacionamiento seleccionado.
 *  La carga de datos debe continuar hasta que el usuario ingrese la palabra
 *  "FIN" en lugar de la patente.
 *
 *  Al finalizar, el programa debe mostrar por pantalla:
 *  - la cantidad de estacionamientos por hora
 *  - la cantidad de estacionamientos de media jornada
 *  - la cantidad de estacionamientos de jornada completa
 *  - la suma total de ingresos en $ que hubo durante el día
 */
public class Integrator {

  public static void main(String[] args) {

    Scanner console = new Scanner(System.in);

    String patent = "";
    int type; // 1: por hora, 2: media jornada, 3: jornada completa

    // Cantidad de estacionamientos por cada tipo
    int hourCount = 0;      // por hora
    int halfDayCount = 0;   // media jornada
    int fullDayCount = 0;   // jornada completa

    // Ingresos (en $) generados por cada tipo
    double hourTotal = 0;
    double halfDayTotal = 0;
    double fullDayTotal = 0;

    while (true) {

      System.out.println("Ingrese la patente del vehículo (o FIN para terminar):");
      patent = console.nextLine();

      // Si la patente es "FIN", salimos del ciclo sin pedir el servicio.
      if (patent.equalsIgnoreCase("fin")) {
        break;
      }

      System.out.println(
          "Ingrese el tipo de servicio (1: por hora, 2: media jornada {5 hs}, "
              + "3: jornada completa {10 hs}):");
      type = console.nextInt();
      console.nextLine(); // consumir el salto de línea que deja nextInt()

      // Si el tipo no es válido, se vuelve a pedir la patente y el servicio.
      if (type < 1 || type > 3) {
        System.out.println("Debe elegir una opción válida (1, 2 o 3)");
        continue;
      }

      switch (type) {

        case 1: // Por hora: $3 la hora
          System.out.println("Ingrese la cantidad de horas a estacionar:");
          int hours = console.nextInt();
          console.nextLine(); // consumir el salto de línea

          double byHourTotal = hours * 3;
          hourCount++;
          hourTotal += byHourTotal;
          System.out.println("El total de su estacionamiento es de: $" + byHourTotal);
          break;

        case 2: // Media jornada: $15 con 5% de descuento
          double halfDay = 15 - (15 * 0.05);
          halfDayCount++;
          halfDayTotal += halfDay;
          System.out.println("El total de su estacionamiento es de: $" + halfDay);
          break;

        case 3: // Jornada completa: $30 con 10% de descuento
          double fullDay = 30 - (30 * 0.10);
          fullDayCount++;
          fullDayTotal += fullDay;
          System.out.println("El total de su estacionamiento es de: $" + fullDay);
          break;
      }
    }

    // Resumen final del día
    double grandTotal = hourTotal + halfDayTotal + fullDayTotal;

    System.out.println("----------------------------------------");
    System.out.println("Resumen del día:");
    System.out.println("Cantidad de estacionamientos por hora: " + hourCount);
    System.out.println("Cantidad de estacionamientos de media jornada: " + halfDayCount);
    System.out.println("Cantidad de estacionamientos de jornada completa: " + fullDayCount);
    System.out.println("Ingresos totales del día: $" + grandTotal);
  }
}

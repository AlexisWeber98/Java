import java.util.Scanner;

/* 
 * ------------------------ EJERCICIO ESTACIONAMIENTO ----------------------------
 *
 *  Un administradot de un estacionamiento necesita un programa que permita calcular el total a pagar de varios vehiculos que utilizan el servicio
 *  
 *  El progmrama debe solicitar por teclado dos datos:
 *  - patente del vehiculo
 *  - tipo de estacienamiento
 *
 *  los tipos de esntacionamiento disponibles son 3:
 *  - Por Hora (cuyo valor es  de $3 por hora)
 *  - media jornada (cuyo valor total es de $15 y posee 5% de descuento)
 *  - jornara completa (cuyo valor total es de $30 y posee 10% de descuento)
 *
 *  el programa debe calcular el monto a pagar para cada cliente en funcion  del ripo de estacionamiento seleccienado
 *  La  carga de datos debe continuar hasta que el usuario ingrese la palabra "FIN" en  lugar de la patente 
 *
 *  al finalizar el programa debe mostrar por pantalla :
 *
 *  - la cantidad de estacionamientos por hora 
 *  - la cantidad de estacionamientos de media jornada
 *  - la cantidad de estacionamientos de jornada completa 
 *  - la suma tetal de ingresos en $ que hubo durtante el dia
 *
 *
 */
public class Integrator {

public static void main(String[] args) {

  Scanner console = new Scanner(System.in);

  String patente; 
  int turno;  // 1 = media jornada (5 hs ), 2 jornada cgompleta (10 hs), 3 = por hora



    do {
    System.out.println("Ingrese la patente del vehiculo (o FIN para terminar):");
    patente = console.nextLine();

      if(patente.isEmpty() || patente.isBlank()) {
        System.out.println("La patente no puede estar vacia o en blanco. Por favor, ingrese una patente valida.");
      } 

    } while (patente.isEmpty() || patente.isBlank());

    if(patente.equals("FIN")) {
      System.out.println("Programa finalizado");
      console.close();
      return; 
    }

    System.out.println("SU PATENTE: " + patente);



  }
}


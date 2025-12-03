public class Conditionals {
  public static void main (String [] args){

    int age = 27;

    if (age >= 18) {
      System.out.println("Eres mayor de edad");

    }else {
      System.out.println("Eres menor de edad");
    }


    switch (age) {
      case 27:
        System.out.println("El SWITCH dice: Eres mayor de edad");
        break;
      case 17:
        System.out.println("El SWITCH dice: Eres menor de edad");
        break;
      case 18:
        System.out.println("El SWITCH dice: Tienes 18 años");
        break;
     default:
      break;
    }


  }
}

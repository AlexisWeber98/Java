package switchOperator;

import java.util.Scanner;

public class SwitchOperator {
  public static void main(String[] args) {
    int dayWeek;
    String dayName;

    Scanner console = new Scanner(System.in);
    System.out.println("please type a number fom 1 to 7");

    dayWeek = console.nextInt();

    switch (dayWeek) {
      case 1:
        dayName = "Sunday";
        break;
      case 2:
        dayName = "Monday";
        break;
      case 3:
        dayName = "Tuesday";
        break;
      case 4:
        dayName = "Wednesday";
        break;
      case 5:
        dayName = "Thursday";
        break;
      case 6:
        dayName = "Friday";
        break;
      case 7:
        dayName = "Saturday";
        break;
      default:
        dayName = "error Day";
    }

    console.close();

    System.out.println("Yor day is: " + dayName);
  }
}

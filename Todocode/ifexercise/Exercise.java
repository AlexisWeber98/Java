package ifexercise;

import java.util.Scanner;

public class Exercise {

  enum StudentType {
    KINDER, FIRST_YEAR, SECOND_YEAR, THIRD_YEAR
  }

  public static void main(String[] args) {

    Scanner console = new Scanner(System.in);
    System.out.println("******** Welcome to Jurassic Park School ********");
    System.out.print("Enter student age: ");

    if (!console.hasNextInt()) {
      System.out.println("Invalid input. Please enter a number.");
      console.close();
      return;
    }

    int studentAge = console.nextInt();
    console.close();

    StudentType type = classifyStudent(studentAge);

    if (type == null) {
      System.out.println("Age " + studentAge + " is not eligible for enrollment.");
      return;
    }

    printSchedule(type);
  }

  private static StudentType classifyStudent(int age) {
    if (age >= 4 && age <= 6) return StudentType.KINDER;
    if (age >= 7 && age <= 8) return StudentType.FIRST_YEAR;
    if (age >= 9 && age <= 10) return StudentType.SECOND_YEAR;
    if (age >= 11 && age <= 13) return StudentType.THIRD_YEAR;
    return null;
  }

  private static void printSchedule(StudentType type) {
    System.out.println("Course schedule:");
    switch (type) {
      case KINDER:
        System.out.println("  Monday and Wednesday 16:00 - 17:00 hs");
        break;
      case FIRST_YEAR:
        System.out.println("  Tuesday and Thursday 16:30 - 17:30 hs");
        break;
      case SECOND_YEAR:
        System.out.println("  Tuesday and Thursday 17:00 - 19:00 hs");
        break;
      case THIRD_YEAR:
        System.out.println("  Monday and Wednesday 17:00 - 18:30 hs");
        break;
    }
  }
}

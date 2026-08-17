import java.util.Arrays;
import java.util.ArrayList;

public class Functions {
  public static void main(String[] args) {

    var users =
        new ArrayList<>(
            Arrays.asList("ale@arraymail.com", "pepe@arraymail.com", "pepa@arraymail.com"));

    for (int i = 0; i < 3; i++) {
      sendEmail("ale@mail.com");
    }

    sendEmail("ale@mail.com", "Ale");

    sendEmail(users);

    var state = sendEmailWithStatus("");
    System.out.println("Email sent: " + state);
  }

  // sobre carga de funciones

  public static void sendEmail(String email, String name) {
    System.out.println("Sending email to " + name + " (" + email + ")");
  }

  public static void sendEmail(String email) {
    System.out.println("Sending email to " + email);
  }

  public static void sendEmail(ArrayList<String> emails) {

    for (String email : emails) {
      sendEmail(email);
    }
  }

  // funciones con retorno

  public static boolean sendEmailWithStatus(String email) {
    if (email.isEmpty()) {
      return false;
    }
    System.out.println("Sending email with status" + email);
    return true;
  }
}

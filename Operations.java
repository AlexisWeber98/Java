

public class Operations {
  public static void main (String[] args) {
 
    var a = 5;
    var b = 2;


    System.out.println("sum: " + a + b);


    System.out.println("mult: " + a * b);

    System.out.println("div: " + a / b);

    int newAsign = a += 5;
    System.out.println("new asign: " +  newAsign);

    System.out.println(a==b);
    System.out.println(a==10);
    System.out.println(a!=b);

    System.out.println(a!=5);
  }
}

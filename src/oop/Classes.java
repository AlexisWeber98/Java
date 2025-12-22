package oop;

public class Classes {
  public static void main(String[] args) {

    var person = new Person("Ale", 27);

    /*
       person.name = "Ale";
      person.age = 30;
    */
    person.sayHello();

    var person2 = new Person("Pepe", 35);
    person2.sayHello();
  }
}

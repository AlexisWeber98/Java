package oop;

public class Person {
  // Atributos
  protected String name;
  protected int age;

  // constructor

  public Person(String name, int age) {
    this.name = name;
    this.age = age;
  }

  // métodos

  public void sayHello() {
    System.out.println("Hello, my name is " + name + " and I am " + age + " years old.");
  }
}

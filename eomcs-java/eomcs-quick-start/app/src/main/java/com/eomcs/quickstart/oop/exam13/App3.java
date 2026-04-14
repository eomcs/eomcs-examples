package com.eomcs.quickstart.oop.exam13;

class GenericBox<T> {

  T value;

  void set(T value) {
    this.value = value;
  }

  T get() {
    return value;
  }
}

public class App3 {
  public static void main(String[] args) {
    GenericBox<String> strBox = new GenericBox<>();
    strBox.set("Hello");
    String strValue = strBox.get();
    System.out.println(strValue);

    GenericBox<Integer> intBox = new GenericBox<>();
    intBox.set(100);
    int intValue = intBox.get();
    System.out.println(intValue);

    GenericBox<Float> floatBox = new GenericBox<>();
    floatBox.set(3.14f);
    float floatValue = floatBox.get();
    System.out.println(floatValue);
  }
}

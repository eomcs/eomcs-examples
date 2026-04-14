package com.eomcs.quickstart.oop.exam13;

class ObjectBox {

  Object value;

  void set(Object value) {
    this.value = value;
  }

  Object get() {
    return value;
  }
}

public class App2 {
  public static void main(String[] args) {
    ObjectBox strBox = new ObjectBox();
    strBox.set("Hello");
    String strValue = (String) strBox.get();
    System.out.println(strValue);

    ObjectBox intBox = new ObjectBox();
    intBox.set(100);
    int intValue = (int) intBox.get();
    System.out.println(intValue);

    ObjectBox floatBox = new ObjectBox();
    floatBox.set(3.14f);
    float floatValue = (float) floatBox.get();
    System.out.println(floatValue);
  }
}

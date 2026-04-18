package com.eomcs.advanced.oop.exam13;

class StringBox {

  String value;

  void set(String value) {
    this.value = value;
  }

  String get() {
    return value;
  }
}

class IntBox {

  int value;

  void set(int value) {
    this.value = value;
  }

  int get() {
    return value;
  }
}

class FloatBox {

  float value;

  void set(float value) {
    this.value = value;
  }

  float get() {
    return value;
  }
}

public class App {
  public static void main(String[] args) {
    StringBox strBox = new StringBox();
    strBox.set("Hello");
    String strValue = strBox.get();
    System.out.println(strValue);

    IntBox intBox = new IntBox();
    intBox.set(100);
    int intValue = intBox.get();
    System.out.println(intValue);

    FloatBox floatBox = new FloatBox();
    floatBox.set(3.14f);
    float floatValue = floatBox.get();
    System.out.println(floatValue);
  }
}

package com.eomcs.quickstart.oop.exam14;

import java.util.Random;
import java.util.function.Supplier;

public class App {

  static <T> void print(Supplier<T> supplier) {
    System.out.println(supplier.get());
  }

  public static void main(String[] args) {
    print(() -> "Hello, World!");

    Random random = new Random();
    print(() -> random.nextInt(100) + 1);
    print(() -> random.nextInt(100) + 1);
  }
}

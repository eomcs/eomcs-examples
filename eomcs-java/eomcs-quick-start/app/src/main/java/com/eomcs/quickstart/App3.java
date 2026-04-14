package com.eomcs.quickstart;

public class App3 {
  public static void main(String[] args) {
    System.out.println("Hello, %s(%d)!".formatted(args[0], Integer.parseInt(args[1])));
  }
}

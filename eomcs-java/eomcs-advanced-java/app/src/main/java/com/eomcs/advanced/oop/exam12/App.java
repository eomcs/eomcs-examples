package com.eomcs.advanced.oop.exam12;

import java.util.Random;

public class App {

  int[] numbers = new int[20];

  App() {
    Random random = new Random();
    for (int i = 0; i < numbers.length; i++) {
      numbers[i] = random.nextInt(100) + 1;
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    for (int i = 0; i < numbers.length; i++) {
      if (i > 0) {
        sb.append(",");
      }
      sb.append(numbers[i]);
    }
    sb.append("]");
    return sb.toString();
  }

  void play() {
    BubbleSorter sorter = new BubbleSorter(numbers);

    System.out.println("정렬 전: " + this);

    sorter.sort();

    System.out.println("정렬 후: " + this);
  }

  public static void main(String[] args) {
    new App().play();
  }
}

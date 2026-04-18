package com.eomcs.advanced.oop.exam10;

import java.util.Random;

public class App {

  static void play(AbstractSorter sorter) {
    System.out.println("정렬 전: " + sorter);
    sorter.sort();
    System.out.println("정렬 후: " + sorter);
  }

  public static void main(String[] args) {
    int[] numbers = new int[20];
    Random random = new Random();

    for (int i = 0; i < numbers.length; i++) {
      numbers[i] = random.nextInt(100) + 1;
    }

    System.out.println("[버블 정렬]");
    play(new BubbleSorter(numbers.clone()));

    System.out.println("[퀵 정렬]");
    play(new QuickSorter(numbers.clone()));
  }
}

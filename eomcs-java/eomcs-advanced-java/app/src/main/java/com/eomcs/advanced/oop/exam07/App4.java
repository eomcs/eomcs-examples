package com.eomcs.advanced.oop.exam07;

import java.util.Random;

public class App4 {

  static void play(int[] arr, QuickSorter sorter) {
    System.out.print("정렬 전: ");
    for (int n : arr) {
      System.out.printf("%d ", n);
    }
    System.out.println();

    sorter.sort(arr);

    System.out.print("정렬 후: ");
    for (int n : arr) {
      System.out.printf("%d ", n);
    }
    System.out.println();
  }

  public static void main(String[] args) {
    int[] numbers = new int[20];
    Random random = new Random();

    for (int i = 0; i < numbers.length; i++) {
      numbers[i] = random.nextInt(100) + 1;
    }

    play(numbers, new QuickSorter());
  }
}

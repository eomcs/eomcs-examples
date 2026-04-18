package com.eomcs.advanced.oop.exam09;

import java.util.Random;

public class App {

  static void play(int[] arr, Sorter sorter) {
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

    System.out.println("[버블 정렬]");
    play(numbers.clone(), new BubbleSorter());

    System.out.println("[퀵 정렬]");
    play(numbers.clone(), new QuickSorter());
  }
}

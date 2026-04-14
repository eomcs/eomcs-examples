package com.eomcs.quickstart.oop.exam12;

import java.util.Random;

public class App2 {

  static class BubbleSorter implements Sorter {

    int[] arr;

    BubbleSorter(int[] arr) {
      this.arr = arr;
    }

    @Override
    public void sort() {
      for (int i = 0; i < arr.length - 1; i++) {
        for (int j = 0; j < arr.length - 1 - i; j++) {
          if (arr[j] > arr[j + 1]) {
            int temp = arr[j];
            arr[j] = arr[j + 1];
            arr[j + 1] = temp;
          }
        }
      }
    }
  }

  int[] numbers = new int[20];

  App2() {
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
    new App2().play();
  }
}

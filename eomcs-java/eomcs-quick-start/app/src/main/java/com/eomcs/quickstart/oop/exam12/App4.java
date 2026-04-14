package com.eomcs.quickstart.oop.exam12;

import java.util.Random;

public class App4 {

  int[] numbers = new int[20];

  App4() {
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

    class BubbleSorter implements Sorter {
      @Override
      public void sort() {
        for (int i = 0; i < App4.this.numbers.length - 1; i++) {
          for (int j = 0; j < App4.this.numbers.length - 1 - i; j++) {
            if (App4.this.numbers[j] > App4.this.numbers[j + 1]) {
              int temp = App4.this.numbers[j];
              App4.this.numbers[j] = App4.this.numbers[j + 1];
              App4.this.numbers[j + 1] = temp;
            }
          }
        }
      }
    }

    BubbleSorter sorter = new BubbleSorter();

    System.out.println("정렬 전: " + this);

    sorter.sort();

    System.out.println("정렬 후: " + this);
  }

  public static void main(String[] args) {
    new App4().play();
  }
}

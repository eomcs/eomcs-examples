package com.eomcs.advanced.oop.exam12;

import java.util.Random;

public class App5 {

  int[] numbers = new int[20];

  App5() {
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
    System.out.println("정렬 전: " + this);

    new Sorter() {
      @Override
      public void sort() {
        for (int i = 0; i < App5.this.numbers.length - 1; i++) {
          for (int j = 0; j < App5.this.numbers.length - 1 - i; j++) {
            if (App5.this.numbers[j] > App5.this.numbers[j + 1]) {
              int temp = App5.this.numbers[j];
              App5.this.numbers[j] = App5.this.numbers[j + 1];
              App5.this.numbers[j + 1] = temp;
            }
          }
        }
      }
    }.sort();

    System.out.println("정렬 후: " + this);
  }

  public static void main(String[] args) {
    new App5().play();
  }
}

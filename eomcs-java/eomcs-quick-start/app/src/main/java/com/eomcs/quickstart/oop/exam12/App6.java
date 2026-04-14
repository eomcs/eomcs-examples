package com.eomcs.quickstart.oop.exam12;

import java.util.Random;

public class App6 {

  int[] numbers = new int[20];

  App6() {
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
    Sorter sorter =
        () -> {
          for (int i = 0; i < App6.this.numbers.length - 1; i++) {
            for (int j = 0; j < App6.this.numbers.length - 1 - i; j++) {
              if (App6.this.numbers[j] > App6.this.numbers[j + 1]) {
                int temp = App6.this.numbers[j];
                App6.this.numbers[j] = App6.this.numbers[j + 1];
                App6.this.numbers[j + 1] = temp;
              }
            }
          }
        };

    sorter.sort();

    System.out.println("정렬 후: " + this);
  }

  public static void main(String[] args) {
    new App6().play();
  }
}

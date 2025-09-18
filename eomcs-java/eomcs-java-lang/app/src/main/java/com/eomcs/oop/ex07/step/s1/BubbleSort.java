package com.eomcs.oop.ex07.step.s1;

public class BubbleSort {

  public void run(int[] values) {
    int size = values.length;
    for (int i = 0; i < size - 1; i++) {
      for (int j = 0; j < size - i - 1; j++) {
        if (values[j] > values[j + 1]) {
          //System.out.printf("%d <==> %d\n", values[j], values[j + 1]);
          int temp = values[j];
          values[j] = values[j + 1];
          values[j + 1] = temp;
        }
      }
    }
  }
}

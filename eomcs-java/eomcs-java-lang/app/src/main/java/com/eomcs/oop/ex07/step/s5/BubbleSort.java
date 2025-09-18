package com.eomcs.oop.ex07.step.s5;

public class BubbleSort implements Sorter {

  // 인터페이스의 추상 메서드를 구현하는 것도 
  // "오버라이딩" 이라 부른다.
  @Override
  public void sort(int[] values) {
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

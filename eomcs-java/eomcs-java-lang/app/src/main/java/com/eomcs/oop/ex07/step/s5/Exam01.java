// 추상 클래스를 인터페이스로 전환하기
package com.eomcs.oop.ex07.step.s5;

public class Exam01 {

  public static void main(String[] args) {
    int[] values = {23, 7, 12, 15, 9, 2, 22, 8, 11, 25, 13, 5};
    int[] values2 = {23, 7, 12, 15, 9, 2, 22, 8, 11, 25, 13, 5};
    int[] values3 = {23, 7, 12, 15, 9, 2, 22, 8, 11, 25, 13, 5};

    // Sorter 추상 클래스처럼 필드나 구현 메서드 없이 추상 메서드만 있을 경우
    // 인터페이스로 정의하는 것이 더 낫다.
    //
    display(new BubbleSort(), values);
    display(new QuickSort(), values2);
    display(new MergeSort(), values3);

  }

  static void display(Sorter sorter, int[] values) {
    sorter.sort(values);
    for (int  value : values) {
      System.out.print(value + ",");
    }
    System.out.println();
  }
}







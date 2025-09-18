// 일반 메서드의 한계 - 서브 클래스에게 구현을 강요할 수 없다.
package com.eomcs.oop.ex07.step.s3;

public class Exam02 {

  public static void main(String[] args) {
    int[] values = {23, 7, 12, 15, 9, 2, 22, 8, 11, 25, 13, 5};
    int[] values2 = {23, 7, 12, 15, 9, 2, 22, 8, 11, 25, 13, 5};
    int[] values3 = {23, 7, 12, 15, 9, 2, 22, 8, 11, 25, 13, 5};

    display(new BubbleSort(), values);
    display(new QuickSort(), values2);

    // 새로 추가한 MergeSort 클래스를 사용하여 데이터를 정렬해 보자!
    // - 위의 BubbleSort나 QuickSort처럼 정렬을 잘 하리라 기대할 것이다.
    // - 그러나 생각대로 정렬되지 않는다.
    display(new MergeSort(), values3);

    // 왜?
    // - MergeSort는 Sorter를 상속 받아 만들었다.
    // - 따라서 이 클래스를 사용하는 개발자 쪽에서는 Sorter를 상속 받았으니,
    //   당연히 정렬을 수행하기 위해 sort(int[]) 메서드를 호출할 것이다.
    // - 그러나 MergeSort 클래스는 sort(int[]) 메서드를 오버라이딩 하지 않았다.
    // - 개발자가 sort(int[])를 오버라이딩 하는 것을 놓쳤거나 일부러 하지 않은 것이다.
    //
    // 이런 실수나 의도를 방지하고자 등장한 문법이 "추상 메서드"이다.
    // 서브 클래스에게 구현을 강제할 필요가 있을 때 추상 메서드로 선언하라!
    //
  }

  static void display(Sorter sorter, int[] values) {

    // 당연히 파라미터로 넘어 오는 클래스는 Sorter의 서브 클래스일 것이다.
    // (Sorter는 추상 클래스이기 때문에 인스턴스를 생성할 수 없다.
    //  따라서 Sorter의 인스턴스가 넘어올 수는 없다.)
    // 따라서 정렬을 수행하려면 Sorter에 정의된 sort()를 호출할 것이다.
    // 여기서 문제가 발생한다.
    // BubbleSort나 QuickSort 클래스는 sort()를 오버라이딩 하였다.
    // 그러나 MergeSort는 sort() 오버라이딩 하지 않았다.
    // 따라서 파라미터로 넘어 오는 객체가 MergeSort 라면
    // 다음 메서드를 호출하는 것은 아무런 의미가 없다.
    sorter.sort(values);

    // 해결책?
    // => Sorter의 서브 클래스들이 반드시 sort() 메서드를 구현하도록 강제하라!

    for (int  value : values) {
      System.out.print(value + ",");
    }
    System.out.println();
  }
}







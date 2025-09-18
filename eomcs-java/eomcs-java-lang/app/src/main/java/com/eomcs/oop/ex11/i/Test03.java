// 중첩 클래스 사용 예: 람다(lambda) 사용
package com.eomcs.oop.ex11.i;

public class Test03 {

  public static void main(String[] args) {
    int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

    List list = new List(a);

    int[] r = list.toArray(value -> (value % 2) == 1);

    for (int v : r) {
      System.out.println(v);
    }
  }

}

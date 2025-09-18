// 제네릭(Generic) : 메서드에 타입 파라미터 선언하기
package com.eomcs.generic.ex01;

public class Exam0410 {

  // 제네릭 적용 전
  static Object[] reverse(Object[] arr) {
    for (int i = 0; i < arr.length / 2; i++) {
      Object temp = arr[i];
      int targetIndex = arr.length - 1 - i;
      arr[i] = arr[targetIndex];
      arr[targetIndex] = temp;
    }
    return arr;
  }

  // 제네릭 적용 후
  static <T> T[] reverse2(T[] arr) {
    for (int i = 0; i < arr.length / 2; i++) {
      T temp = arr[i];
      int targetIndex = arr.length - 1 - i;
      arr[i] = arr[targetIndex];
      arr[targetIndex] = temp;
    }
    return arr;
  }

  public static void main(String[] args) {
    // 제네릭이 적용 안된 메서드의 리턴 타입은 Object[] 이다.
    // => 파라미터에 넘겨 준 타입으로 형변환 해야 한다.
    String[] arr = (String[])reverse(new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"});
    for (String value : arr) {
      System.out.print(value + ",");
    }
    System.out.println();

    // 제네릭이 적용된 메서드의 리턴 타입은 파라미터 타입과 같은 String[] 이다.
    // => 따로 형변환 할 필요가 없어 편리하다.
    String[] arr2 = reverse2(new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"});
    for (String value : arr2) {
      System.out.print(value + ",");
    }
    System.out.println();
  }

}









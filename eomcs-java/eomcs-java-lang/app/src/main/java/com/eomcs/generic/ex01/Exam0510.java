// 제네릭(Generic) : 배열 만들기
package com.eomcs.generic.ex01;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Exam0510 {

  // 제네릭 배열 생성하기

  // 예1) 제네릭의 타입 파라미터로 레퍼런스 배열을 생성할 수 없다.
  static <T> T[] create1() {
    T[] arr;
    //    arr = new T[10]; // 컴파일 오류! new 명령어를 사용할 때 제네릭의 타입 파라미터를 사용할 수 없다.
    return null;
  }

  // 예2) 견본 배열을 받아서 복제하는 방법을 사용한다.
  static <T> T[] create2(T[] arr) {
    // copyOf(original, newLength)
    // => 원래 배열(original)과 같은 타입의 배열을 배열크기(newLength)에 맞춰 새로 생성한다.
    return Arrays.copyOf(arr, 10);
  }

  // 예3) 배열의 타입 정보를 받아 생성하기
  @SuppressWarnings("unchecked")
  static <T> T[] create3(Class<?> type) {
    return (T[]) Array.newInstance(type, 10);
  }

  // 예4) 견본 배열에서 타입 정보를 추출하여 배열을 생성하기
  @SuppressWarnings("unchecked")
  static <T> T[] create4(T[] arr) {
    Class<?> arrayTypeInfo = arr.getClass(); // 예) String[]
    System.out.println(arrayTypeInfo);

    Class<?> arrayItemTypeInfo = arrayTypeInfo.getComponentType(); // 예) String
    System.out.println(arrayItemTypeInfo);

    return (T[]) Array.newInstance(arrayItemTypeInfo, 10);
  }

  public static void main(String[] args) {
    // 제네릭을 사용하는 메서드를 이용하여 배열 만들기

    // 파라미터로 빈 배열을 넘기면,
    String[] arr1 = create2(new String[0]);
    System.out.println(arr1.length);

    // 내부에서 생성할 배열 크기 보다 더 큰 배열을 파라미터로 넘긴다면?
    // copyOf() 그래도 새 크기에 맞춰 새 배열을 생성한다.
    String[] temp = new String[100];
    String[] arr2 = create2(temp);
    System.out.println(arr2.length);
    System.out.println(temp == arr2);

    // 생성할 배열의 타입 정보를 넘긴다.
    String[] arr3 = create3(String.class);
    System.out.println(arr3.length);

    // 배열을 넘기면 배열의 항목 타입을 알아내어 새 배열을 만든다.
    String[] arr4 = create4(new String[0]);
    System.out.println(arr4.length);
  }


}









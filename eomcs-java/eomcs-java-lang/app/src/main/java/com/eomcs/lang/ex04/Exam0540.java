package com.eomcs.lang.ex04;

//# 배열 - 배열 레퍼런스와 배열 인스턴스 II
//
public class Exam0540 {
  public static void main(String[] args) {

    // 배열 레퍼런스만 따로 선언할 수 있다.
    int[] arr1;

    // 주의!
    // - 아직 레퍼런스는 배열의 메모리를 알지 못하기 때문에 사용할 수 없다.
    //    arr1[0] = 100; // 컴파일 오류!

    // 레퍼런스에 그 레퍼런스 타입의 배열 주소를 넘겨야 한다.
    //    arr1 = new float[5]; // 컴파일 오류!

    // 배열 인스턴스를 생성하고 그 주소를 레퍼런스에 저장한다.
    arr1 = new int[5];


    // 이제 레퍼런스는 배열 메모리를 알기 때문에 레퍼런스를 통해 배열 인스턴스에 값을 담을 수 있다.
    arr1[0] = 100;

    // 배열 인스턴스에 저장된 값을 출력해보자!
    System.out.println(arr1[0]);
  }
}
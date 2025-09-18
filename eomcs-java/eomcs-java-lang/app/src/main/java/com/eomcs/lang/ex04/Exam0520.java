package com.eomcs.lang.ex04;

//# 배열 - 배열 메모리에 방법
// 배열 메모리에 접근
// - 문법
//          배열변수[인덱스] = 값;
//          ex) arr1[0] = 100;
//
// - 배열의 인덱스는 0부터 시작한다.
// - 인덱스의 범위는 0 ~ (배열개수 - 1) 이다.
//
public class Exam0520 {
  public static void main(String[] args) {
    int[] arr1 = new int[5];

    // 배열 메모리에 접근
    arr1[0] = 100;
    arr1[1] = 200;
    arr1[2] = 300;
    arr1[3] = 400;
    arr1[4] = 500;


    // 유효하지 않은 인덱스를 지정한다면?
    // - 실행 중에 오류가 발생한다.
    // - 컴파일 할 때는 인덱스가 유효한지 알 수 없다.
    //
    //    arr1[5] = 600; // 실행 오류(Runtime Error)
    //    arr1[-1] = 700; // 실행 오류(Runtime Error)

    System.out.println("종료!");
  }
}
package com.eomcs.lang.ex07;

//# 메서드 : Heap 메모리 영역
//
public class Exam0420 {

  static int[] getArray() {
    int[] arr = new int[] {100, 200, 300};
    // => int 배열 주소를 담을 arr 변수를 JVM Stack 영역에 준비하라!
    // => 100, 200, 300 값을 담은 배열을 Heap 영역에 준비하라.
    // => Heap 영역에 준비한 배열 메모리의 주소를 JVM Stack 메모리에 있는 arr 변수에 넣어라.

    return arr;
  }

  public static void main(String[] args) {
    int[] arr;
    arr = getArray();
    System.out.println(arr[1]); // 200
  }
}

// 1) main() 호출
//    => JVM Stack: args, arr 변수 생성
// 2) getArray() 호출
//    => JVM Stack: arr 변수 생성
//    => Heap: new int[] 배열 생성
// 3) getArray() 호출 끝
//    => JVM Stack: getArray() 관련 메모리(arr 변수) 제거
//    => new int[] 배열 주소 리턴
// 4) main() 호출 끝
//    => JVM Stack: main() 관련 메모리 제거 
// 5) JVM 종료
//    => JVM이 사용한 모든 메모리(Method Area, JVM Stack, Heap 등)를 OS 반납.
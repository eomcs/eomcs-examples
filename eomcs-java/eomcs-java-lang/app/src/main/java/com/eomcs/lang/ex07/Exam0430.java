package com.eomcs.lang.ex07;

//# 메서드 : 인스턴스와 Heap 메모리 영역
//
public class Exam0430 {

  // Heap 메모리에 어떤 변수를 만들어야 하는지 적어 놓은 설계도
  // => 나중에 new 명령을 사용하여 메모리를 만들라고 하면,
  //    MyObject에 적어 놓은 변수를 Heap 영역에 생성하라는 뜻이다.
  static class MyObject {
    int a;
    int b;
  }

  static MyObject getMyObject() {
    MyObject ref = new MyObject(); // MyObject에 선언된대로 변수를 Heap에 만들라!
    ref.a = 100;
    ref.b = 200;

    return ref;
  }

  public static void main(String[] args) {
    MyObject ref;
    ref = getMyObject();
    System.out.println(ref.a);
    System.out.println(ref.b);
  }
}

// 1) main() 호출
//    => JVM Stack: args, ref 변수 생성
// 2) getMyObject() 호출
//    => JVM Stack: ref 변수 생성
//    => Method Area: MyObject 클래스를 로딩
//    => Heap: MyObject 설계도에 따라 인스턴스 생성
// 3) getMyObject() 호출 끝
//    => JVM Stack: getMyObject() 관련 메모리(ref 변수) 제거
//    => MyObject의 인스턴스의 주소 리턴
// 4) main() 호출 끝
//    => JVM Stack: main() 관련 메모리 제거
// 5) JVM 종료
//    => JVM이 사용한 모든 메모리(Method Area, JVM Stack, Heap 등)를 OS 반납.
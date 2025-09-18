package com.eomcs.oop.ex01;

//# 레퍼런스와 인스턴스 - 가비지
//
public class Exam0320 {
  public static void main(String[] args) {
    class Score {
      String name;
      int kor;
      int eng;
      int math;
      int sum;
      float aver;
    }

    // Score 레퍼런스 선언
    Score s1;

    // 인스턴스를 만들어 그 주소를 레퍼런스에 저장한다.
    s1 = new Score();

    // 새 인스턴스를 만들어 s1에 주소를 저장한다.
    s1 = new Score();

    // 그러면 기존에 들어 있던 주소는 잃어 버린다.
    // 주소를 잃어버려 사용할 수 없는 메모리를
    // "가비지(garbage)"라 부른다.

    // 가비지는 가비지 컬렉터에 의해 메모리에서 해제된다.
    // 가비지 컬렉터(garbage collector)의 실행
    // - 메모리 부족할 때
    // - CPU가 한가할 때
    // - System.gc()를 호출하여 가비지 실행을 요청할 때
    //   물론 이 경우에 바로 실행하는 것이 아니라
    //   빠른 시간 내에 실행할 것을 종용하는 것이다.
    //   아무래도 원래의 시간보다는 앞 당겨 청소를 하게 될 것이다.
    //   반드시 청소를 한다는 보장은 없다.

  }
}

// ## 가비지 컬렉터(garbage collector)
// - 힙 메모리에 존재하는 가비지를 찾아 제거(?)하는 일을 한다.
// - 다른 용도로 사용할 수 있도록 메모리를 해제하는 일을 한다.
//
// ## 언제 작업하는가?
// - JVM이 관리하는 메모리가 부족할 때
// - CPU가 한가할 때

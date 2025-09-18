package com.eomcs.oop.ex01;

//# 레퍼런스 배열 - 사용 후
//
public class Exam0220 {
  public static void main(String[] args) {
    // 여러 개의 인스턴스 주소 저장하기

    class Score {
      String name;
      int kor;
      int eng;
      int math;
      int sum;
      float aver;
    }

    // 배열 문법을 이용하면 한 번에 여러 개의 레퍼런스를 선언할 수 있다.
    // - 문법:
    //     클래스명[] 배열명 = new 클래스명[레퍼런스개수];
    // - 주의!
    //   레퍼런스 배열이다. 인스턴스 배열이 아니다!
    //
    Score[] arr = new Score[3];
    // 강사님, 인스턴스 배열을 만들 수는 없나요?
    // => 없다!

    // 레퍼런스 배열을 생성하면 모든 항목이 null로 초기화 된다.
    // 아직 레퍼런스 배열의 각 항목에 인스턴스 주소가 없는 상태이다.
    System.out.println(arr[0]);
    System.out.println(arr[1]);
    System.out.println(arr[2]);

    System.out.println("------------------------");

    // 레퍼런스 배열의 각 항목에 인스턴스를 저장한 후 사용해야 한다.
    arr[0] = new Score();
    arr[1] = new Score();
    arr[2] = new Score();

    System.out.println(arr[0]);
    System.out.println(arr[1]);
    System.out.println(arr[2]);
  }
}

// 결론!
// - 여러 개의 인스턴스의 주소를 관리할 때는
//   레퍼런스 배열을 사용하는 게 편하다.
//
// 잊지말자!
// - 인스턴스 배열을 만들 수 없다.
// - 레퍼런스 배열만 가능하다.
// - 단 자바 기본 타입(byte, short, int, long, float, double, boolean, char)은
//   해당 타입의 메모리를 배열로 만들 수 있다.
// - 그 외 모든 타입은 오직 레퍼런스 배열만 생성한다.








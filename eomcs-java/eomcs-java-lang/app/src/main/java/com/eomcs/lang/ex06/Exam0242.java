package com.eomcs.lang.ex06;

//# 흐름 제어문 - switch 문법 II
//
public class Exam0242 {

  // 상수를 좀 더 조직적으로 관리하는 방법
  // => enum을 사용하여 상수를 정의한다.
  // => nested enum은 기본이 static 이다.
  //    그래서 static을 생략해도 된다.
  //
  enum Level {
    GUEST, MEMBER, ADMIN
  }

  public static void main(String[] args) {
    // enum으로 정의된 상수를 사용하려면
    // enum 타입의 변수를 선언해야 한다.
    // => final int처럼 직접 값을 지정하지 않아도 된다.
    // => 값을 직접 지정할 수도 있다. (나중에 따로 enum 문법에서 설명)
    //
    // enum을 사용하는 주된 이유는?
    // => 100, 200, "admin" 과 같이 값을 직접 지정할 필요가 없다.
    // => enum 변수에는 그 타입에 정의된 값만 저장할 수 있다.
    // => 안전한 코드를 작성할 수 있다.
    //
    Level level = Level.MEMBER;

    // 다음과 같이 switch나 case 값으로 enum 타입의 값이 올 수 있다.
    switch (level) {
      case GUEST:
        System.out.println("조회만 가능합니다.");
        break;
      case MEMBER:
        System.out.println("글작성 가능합니다.");
        break;
      case ADMIN:
        System.out.println("다른 회원의 글을 변경, 삭제할 수 있습니다.");
        break;
    }
  }
}


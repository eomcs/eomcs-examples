package com.eomcs.lang.ex07;

// # 메서드 : 가변 파라미터
//
public class Exam0250 {

  // 가변 파라미터
  // [리턴타입] 메서드명(타입... 변수) {...}
  // => 0 개 이상의 값을 받을 때 선언하는 방식.
  // => 메서드 내부에서는 배열처럼 사용한다.
  //
  // 다음은 hello()를 호출할 때 String 값을 0개 이상 전달할 수 있다.
  static void hello(String... names) {
    for (int i = 0; i < names.length; i++) {
      System.out.printf("%s님 반갑습니다.\n", names[i]);
    }
  }

  public static void main(String[] args) {

    hello(); // 이 경우 names 배열의 개수는 0이다.
    System.out.println("-------------------");

    hello("홍길동"); // 이 경우 names 배열의 개수는 1이다.
    System.out.println("-------------------");

    hello("홍길동", "임꺽정", "유관순"); // 이 경우 names 배열의 개수는 3이다.
    System.out.println("-------------------");

    // 가변 파라미터 자리에 배열을 직접 넣어도 된다.
    String[] arr = {"김구", "안중근", "윤봉길", "유관순"};

    hello(arr);
    System.out.println("-------------------");

    //    hello("홍길동", 20, "오호라"); // 다른 타입은 안된다. 컴파일 오류!
  }
}

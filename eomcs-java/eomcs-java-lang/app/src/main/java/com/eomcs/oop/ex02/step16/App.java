package com.eomcs.oop.ex02.step16;

// 데이터 타입 정의  
// 1) 클래스 사용전: 
//    - 낱개 변수 사용
// 2) 성적 데이터를 저장할 메모리를 새로 정의: 
//    - '사용자 정의 데이터 타입(user-defined data type)'이라 부른다.
//    - 여러 개로 이루어진 데이터를 한 묶음으로 다루면 관리하기가 쉽다.
// 3) 리팩토링: 
//   - 메서드 추출(extract method): 중복되는 코드가 있으면 별도의 메서드로 분리한다.
// 4) 리팩토링: 
//   - 메서드 추출(extract method): 한 개의 메서드는 한 개의 기능을 수행해야 한다.
// 5) GRASP(General Responsibility Assignment Software Patterns) 패턴
//   - Information Expert: 데이터를 다룰 때는 그 데이터를 갖고 있는 객체에게 묻는다.
//   - 리팩토링: 메서드 이동(Move Method)
//     - 메서드를 관련된 클래스로 이동시킨다. => 코드를 이해가 쉽다.
// 6) 인스턴스 메서드: 
//   - 인스턴스 주소를 더 쉽게 받는 문법
//
public class App {
  public static void main(String[] args) {

    Score s1 = new Score();
    Score s2 = new Score();
    Score s3 = new Score();

    s1.name = "홍길동";
    s1.kor = 100;
    s1.eng = 90;
    s1.math = 85;
    s1.compute();

    // 다음은 Score의 값을 다루기 위해 스태틱 메서드를 호출하는 예이다.
    // => static 메서드 = 클래스 메서드
    //
    //    Score.compute(s1);
    //
    // 클래스 메서드를 사용할 때 마다 매번 인스턴스의 주소를 파라미터로 넘겨줘야 했다.
    // 그러나 인스턴스 메서드를 사용하면 인스턴스 주소를 넘기기가 더 편하다.
    // 메서드 호출 앞에다 둔다.
    // 소스 코드의 목적을 이해하는데 더 직관적이다.
    // 마치 변수 뒤에 연산자를 놓는 i++ 의 예와 비슷하다.


    s2.name = "임꺽정";
    s2.kor = 90;
    s2.eng = 80;
    s2.math = 75;
    s2.compute();

    s3.name = "유관순";
    s3.kor = 80;
    s3.eng = 70;
    s3.math = 65;
    s3.compute();

    printScore(s1);
    printScore(s2);
    printScore(s3);

  }

  static void printScore(Score s) {
    System.out.printf("%s: %d, %d, %d, %d, %.1f\n", 
        s.name, s.kor, s.eng, s.math, s.sum, s.aver);
  }
}

// 클래스 메서드 vs 인스턴스 메서드
// 1) 클래스 메서드(스태틱 메서드)
//    - 메서드 선언부에 static이 붙는다.
//    - 특정 인스턴스가 아닌 모든 인스턴스에 대해 작업을 수행할 때 사용하는 메서드이다.
//    - 사용법:
//      클래스명.메서드명();
//
//    - 다음과 같이 레퍼런스를 통해서도 호출할 수 있다.
//      레퍼런스.메서드명();
//
//      그러나 가능한 레퍼런스를 사용하여 클래스 메서드를 호출하지 말라!
//      다른 개발자가 인스턴스 메서드인 줄 알고 헷갈려 한다.
//
// 2) 인스턴스 메서드(논스태틱 메서드)
//    - 메서드 선언부에 static이 붙지 않는다.
//    - 특정 인스턴스에 대해 작업을 수행할 때 사용하는 메서드이다.
//    - 사용법:
//      레퍼런스.메서드명();
//
// 정리!
// 사용자 정의 데이터 타입에서
// 인스턴스의 값을 다루는 연산자를 정의할 때는
// 인스턴스 메서드로 만드는 것이 사용하기 편하다!

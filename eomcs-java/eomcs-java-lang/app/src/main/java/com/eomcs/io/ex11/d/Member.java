package com.eomcs.io.ex11.d;

public class Member implements java.io.Serializable {

  // java.io.Serializable 인터페이스를 구현하는 클래스는
  // 항상 serialVersionUID 라는 스태틱 상수를 가져야 한다.
  // 이 변수는 인스턴스를 serialize 하고 deserialize 할 때
  // 인스턴스 형식에 대한 검증 번호로 사용된다.
  // 이 값을 가지고 serialize 된 인스턴스의 값을
  // 읽을 수 있는지 없는지 판단한다.
  //
  // 다음과 같이 개발자가 버전 번호를 직접 명시할 수 있다.
  // 명시하지 않는다면 컴파일러가 임의의 번호로 설정한다.
  // 필드를 추가하거나 변경하더라도 버전 번호가 같다면
  // JVM은 같은 형식으로 판단한다.
  //
  private static final long serialVersionUID = 1280;

  // serialVersionUID의 사용법
  // => 클래스를 변경하더라도 기존 데이터를 읽는데 문제가 없다면
  //    변경한 클래스의 버전을 이전 버전과 같게 하라!
  // => 그러나 클래스의 변경 사항이 너무 많아 이전 데이터를 읽기가 부적합하다면
  //    변경한 클래스의 버전을 바꿔 읽을 때 오류가 나게 하라!
  // => Member의 경우 클래스를 변경할 때 tel 변수를 추가하였다.
  //    따라서 이전 데이터를 읽는데는 문제가 없다.
  //    그래서 버전 번호를 바꾸지 않은 것이다.

  String name;

  //Exam0420의 세 번째 테스트를 실행할 때 주석으로 막아라!
  int age;

  boolean gender; // true(여자), false(남자)

  // Exam0420의 두 번째 테스트를 실행할 때 주석을 풀라!
  String tel;

  @Override
  public String toString() {
    return "Member [name=" + name +
        // Exam0420의 세 번째 테스트를 실행할 때 주석으로 막아라!
        //", age=" + age +
        ", gender=" + gender +
        // Exam0420의 두 번째 테스트를 실행할 때 주석을 풀라!
        ", tel=" + tel +
        "]";
  }
}

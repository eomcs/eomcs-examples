package com.eomcs.io.ex11.b;

// ObjectOutputStream/ObjectInputStream을 통해 인스턴스의 값을
// 바이트 배열로 자동으로 변환하여 출력하고 읽으려면
// java.io.Serializable 인터페이스를 구현해야 한다.
// 인스턴스의 값을 바이트 배열로 자동 변환(Serialize)할 수 있도록 허가한다는 표시다.
//
// 정리:
// => java.io.Serializable 인터페이스를 구현한다.
// => Serializable 인터페이스는 아무런 메서드가 정의되어 있지 않다.
//    단지 Serialize를 활성화시키는 기능을 수행한다.
//    즉 serialize 가능함을 표시하는 역할을 한다.
// => 이 인터페이스를 구현한 객체만이
//    ObjectOutputStream/ObjectInputStream으로
//    serialize/deserialize 할 수 있다.
//
public class Member implements java.io.Serializable {
  String name;
  int age;
  boolean gender; // true(여자), false(남자)

  @Override
  public String toString() {
    return "Member [name=" + name + ", age=" + age + ", gender=" + gender + "]";
  }
}


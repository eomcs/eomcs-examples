// Wrapper 클래스 - 오토박싱(auto-boxing)/오토언박싱(auto-unboxing)
package com.eomcs.basic.ex02;

public class Exam0221 {
  public static void main(String[] args) {

    // 오토박싱
    // - Java 1.5부터 지원하는 문법이다.
    // - 코드의 문맥에 따라 박싱(boxing)과 언박싱(unboxing)을 자동으로 수행한다.
    //
    // 즉 primitive data type 값을 Wrapper 클래스의 인스턴스에 바로 할당할 수 있다.
    //
    Integer obj = 100; // ==> Integer.valueOf(100)

    // obj는 레퍼런스인데 어떻게 가능한가?
    // => 내부적으로 Integer.valueOf(100) 호출 코드로 바뀐다.
    // => 즉 int 값이 obj에 바로 저장되는 것이 아니라,
    //    내부적으로 Integer 객체가 생성되어 그 주소가 저장된다.
    // => 이렇게 int 값을 자동으로 Integer 객체로 만드는 것을
    //    "오토박싱(auto-boxing)"이라 한다.
  }
}



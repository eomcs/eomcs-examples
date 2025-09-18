// 클래스에 제네릭(generic) 적용하기 : 적용 후
package com.eomcs.generic.ex01;

import java.util.Date;

public class Exam0242 {

  static class Box<T> {
    T value;
    public T get() {return this.value;}
    public void set(T value) {this.value = value;}
  }

  public static void main(String[] args) {

    // 제네릭 클래스를 사용할 때,
    // 타입 파라미터의 값을 빼면 경고가 뜬다.
    // 내부적으로는 Object를 지정한다.
    // 즉 다음 코드는 Box<Object> b5 = new Box<Object>() 와 같다.
    //
    Box b5 = new Box(); // 경고!

    // 타입을 지정하지 않았기 때문에 뭐든지 담을 수 있다.
    b5.set(new Member("홍길동", 20));
    b5.set(new String("Hello"));
    b5.set(new Date());

    // 제네릭은 다형성의 유연함을 그대로 유지하면서
    // 특정 타입의 데이터를 다루는
    // 범용 클래스를 만드는 매우 편리한 문법이다.

    // 결론:
    // => 제네릭 문법을 사용하면 Object 타입을 사용하는 것 보다 편리하다.
    // => 각 타입 별로 클래스를 따로 정의한듯한 효과가 있다.
  }
}
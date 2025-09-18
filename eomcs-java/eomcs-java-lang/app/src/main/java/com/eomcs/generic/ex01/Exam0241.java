// 클래스에 제네릭(generic) 적용하기 : 적용 후
package com.eomcs.generic.ex01;

import java.util.Date;

public class Exam0241 {

  static class Box<T> {
    T value; 
    public T get() {return this.value;}
    public void set(T value) {this.value = value;}
  }

  public static void main(String[] args) {

    // 제네릭으로 지정한 타입이 아닌 것을 넣으려 하면 컴파일 오류가 발생한다.
    // => 이것이 제네릭을 사용하는 이유이기도 하다.
    // => 즉 특정 클래스만 다루도록 제한할 수 있다.
    //
    Box<Date> b4 = new Box<>();

    // 다음과 같이 인스턴스를 생성할 때 지정한 타입이 아닌 것을 다루려고 하면 
    // 컴파일 오류가 발생한다.
    //    b4.set("Hello!"); // 컴파일 오류!
  }
}
// 클래스에 제네릭(generic) 적용하기 : 적용 후
package com.eomcs.generic.ex01;

public class Exam0231 {

  static class Box<T> {
    T value;  // T라는 타입의 인스턴스 주소를 저장할 필드

    public T get() { // T라는 타입의 객체를 리턴
      return this.value;
    }

    public void set(T value) { // T라는 타입의 객체를 받을 파라미터
      this.value = value;
    }
  }

  public static void main(String[] args) {

    // 레퍼런스 변수를 선언할 때 어떤 타입을 다룰지 지정하면,
    // new 를 실행할 때는 타입을 또 지정할 필요가 없다. 
    // 생략해도 된다.
    Box<String> b = new Box<>(); // => new Box<String>();

    // 다음과 같이 레퍼런스 선언과 객체 생성을 따로 해도 마찬가지이다.
    Box<Integer> b3;
    b3 = new Box<>(); // OK!
    //    b3 = new Box<Integer>(); // OK!
  }
}
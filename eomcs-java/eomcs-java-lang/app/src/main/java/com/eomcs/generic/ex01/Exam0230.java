// 클래스에 제네릭(generic) 적용하기 : 적용 후
package com.eomcs.generic.ex01;

import com.eomcs.generic.ex02.Member;


public class Exam0230 {

  //2) 제네릭을 적용한 클래스
  //- 클래스 전체에서 사용할 "타입 파라미터(타입 이름을 저장하는 변수)"를 선언하기
  //     class 클래스명<타입파라미터명, 타입파라미터명, ...> {...}
  //- 클래스가 다루는 어떤 타입이 있다고 가정하자. 그 타입을 T라고 부르겠다.
  //- T가 어떤 타입인지는 Box 객체를 생성할 때 결정된다.
  //
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

    // 2) 제네릭이 적용된 Box 클래스 이용
    // - 먼저 Box가 어떤 타입의 값을 다룰 것인지 지정해야 한다.
    Box<Member> b1 = new Box<Member>();

    // - 인스턴스를 생성할 때 지정한 타입의 객체를 넘긴다.
    b1.set(new Member("홍길동", 20));

    // - 값을 꺼낼 때 형변환 할 필요가 없다.
    Member m = b1.get();
    System.out.println(m);

    // - 제네릭에서 지정한 타입이 아닌 경우 컴파일 오류!
    //    b1.set(new String("Hello"));
    //    b1.set(100);
  }
}
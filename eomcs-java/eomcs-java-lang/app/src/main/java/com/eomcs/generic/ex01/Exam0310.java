// 제네릭(Generic) - 여러 개의 타입 파라미터
package com.eomcs.generic.ex01;

import com.eomcs.generic.ex02.Member;

// 여러 개의 타입 파라미터를 지정하기
class A<X,Y,Z> {
  X v1;
  Y v2;
  Z v3;
}


// # 제네릭의 타입 파라미터로 많이 사용하는 이름
// - 보통 긴 이름을 사용하지 않고 다음과 같이 한 개의 대문자로 된 이름을 많이 사용한다.
//
// T : Type이라는 의미를 표현할 수 있어 많이 사용하는 이름이다.
// E : Element라는 의미로 목록의 항목을 가리킬 때 사용한다.
// K : Key 객체를 가리킬 때 사용한다.
// N : Number의 의미로 숫자 타입을 가리킬 때 주로 사용한다.
// V : Value의 의미로 값의 타입을 가리킬 때 사용한다.
// S,U,V 등 : 한 번에 여러 타입을 가리킬 때 두 번째, 세 번째, 네 번째 이름으로 주로 사용한다.
//
// 그러나 이름을 짓는 것은 개발자 마음이다.
// 다만, 다른 개발자와의 원활한 소통을 위하여
// 가능한 위의 규칙에 따라 이름을 사용하기 바란다.


// 자바에서 제안하는 "타입 파라미터"의 이름으로 정의한다면,
class A2<T,S,U> {
  T v1;
  S v2;
  U v3;
}

public class Exam0310 {

  public static void main(String[] args) {
    A<String,Integer,Member> obj = new A<>();

    obj.v1 = new String("Hello");
    obj.v2 = Integer.valueOf(100);
    obj.v3 = new Member("홍길동", 20);

    // 지정된 타입이 아닌 타입의 값을 설정한다면,
    //    obj.v1 = new Member("홍길동", 20); // 컴파일 오류!
    //    obj.v2 = new String("Hello"); // 컴파일 오류!
    //    obj.v3 = Integer.valueOf(100); // 컴파일 오류!
  }
}

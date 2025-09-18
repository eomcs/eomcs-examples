// 상수 코드를 스태틱 중첩 클래스로 다루기
package com.eomcs.oop.ex11.g.step7;

public class Exam0110 {
  public static void main(String[] args) {

    // 분류를 지정할 때 OGNL 방식으로 표현할 수 있다.
    // OGNL(Object Graph Navigation Language)?
    // - 객체의 하위 프로퍼티 값에 접근하는 표기법.
    // - 문법:
    //      객체.프로퍼티명.프로퍼티명.프로퍼티명 = 값;
    // - 자바는 기본으로 객체의 필드를 사용할 때 dot(.)을 이용하여 지정한다.
    //   그 하위 필드도 마찬가지로 점(.)을 이용하여 지정할 수 있다.
    //   이런 식으로 점(.)을 이용하여 하위 필드를 지정하는 표기법을 OGNL이라 한다.
    //
    // - static nested class 를 사용하여 상수를 정의하면,
    //   분류 값을 OGNL 표기법처럼 사용할 수 있다.
    // - 다음과 같이 하나의 이름을 사용하는 대신에 점(.)을 사용하여 하위 프로퍼티를 접근하는 방식은
    //   코드를 읽기 쉽고 유지보수하기 쉽게 도와준다.
    // - 이렇게 클래스 이름으로 필드처럼 사용할 수 있어 보기가 쉽다.
    //
    //
    Product p = new Product();
    p.category = Category.appliance.TV;
    // p.category = Category.COMPUTER_MOUSE_BLUETOOTH; // 예전의 상수를 사용하는 방식
    p.name = "울트라비전 뷰";
    p.price = 2000000;

    Product p2 = new Product();
    p2.category = Category.computer.RAM;
    p2.name = "삼성모듈램 4GB";
    p2.price = 80000;

    Product p3 = new Product();
    p3.category = Category.book.NOVEL;
    p3.name = "토지";
    p3.price = 18000;
  }
}


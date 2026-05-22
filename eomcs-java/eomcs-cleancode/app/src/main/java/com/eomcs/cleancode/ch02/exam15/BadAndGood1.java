package com.eomcs.cleancode.ch02.exam15;

public class BadAndGood1 {

  // Bad
  // - 각 변수는 의미가 있지만 서로 어떤 관계인지 맥락이 없다.
  // - 사용자 정보인지, 배송지인지, 청구지인지 읽는 사람이 추측해야 한다.
  static class BadExample {
    String firstName;
    String lastName;
    String street;
    String city;
    String state;
    String zipCode;
  }

  // Good
  // - Address라는 클래스가 맥락을 제공한다.
  // - 변수들이 하나의 개념으로 묶여 "아, 주소 정보구나"라고 즉시 이해할 수 있다.
  static class Address {
    String firstName;
    String lastName;
    String street;
    String city;
    String state;
    String zipCode;
  }
}

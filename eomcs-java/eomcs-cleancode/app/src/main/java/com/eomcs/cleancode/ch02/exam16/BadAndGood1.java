package com.eomcs.cleancode.ch02.exam16;

public class BadAndGood1 {

  // Bad
  // - GSDAccount 가 모든 필드 이름에 반복된다.
  // - 클래스 이름(GSDAccountAddress)에 이미 맥락이 있는데 변수에 또 포함해 장황하다.
  // - 핵심 정보(firstName, street 등)가 긴 접두어에 묻혀 가독성이 떨어진다.
  static class GSDAccountAddress {
    String GSDAccountFirstName;
    String GSDAccountLastName;
    String GSDAccountStreet;
    String GSDAccountCity;
  }

  // Good
  // - Address 클래스 이름 자체가 이미 맥락을 제공한다.
  // - 변수는 핵심 정보만 표현하고 중복을 제거해 가독성이 향상된다.
  static class Address {
    String firstName;
    String lastName;
    String street;
    String city;
  }
}

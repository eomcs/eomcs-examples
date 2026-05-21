package com.eomcs.cleancode.ch02.exam03;

// 관사/형용사로 구분한 변수 이름
public class BadAndGood4 {
  static class Customer {}

  // Bad: 불필요한 관사/형용사를 붙인 이름
  String theMessage;
  Customer aCustomer;
  Customer theCustomer;

  // Good: 관사/형용사 제거한 이름
  String message;
  Customer customer;
}

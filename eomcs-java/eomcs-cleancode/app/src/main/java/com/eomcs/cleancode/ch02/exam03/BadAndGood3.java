package com.eomcs.cleancode.ch02.exam03;

// 같은 의미의 함수 이름
public class BadAndGood3 {

  // Bad: 어느 함수를 호출해야 하는지 알 수 없다.
  void getActiveAccount() {}

  void getActiveAccounts() {}

  void getActiveAccountInfo() {}

  // Good: 읽는 사람이 차이를 알 수 있는 이름을 사용해야 한다.
  void getActiveCustomer() {} // 단일 고객 조회

  void getAllActiveCustomers() {} // 전체 활성 고객 목록 조회
}

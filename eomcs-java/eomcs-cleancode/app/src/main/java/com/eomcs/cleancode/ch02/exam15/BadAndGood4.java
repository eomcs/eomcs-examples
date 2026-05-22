package com.eomcs.cleancode.ch02.exam15;

public class BadAndGood4 {

  // Bad
  // - firstName, lastName 만으로는 누구의 이름인지 맥락이 없다.
  static class BadExample {
    String firstName;
    String lastName;
  }

  // Partial (부분적 맥락)
  // - userFirstName, userLastName → 접두사로 최소한의 맥락을 제공한다.
  // - 하지만 필드가 늘어날수록 접두사 반복이 많아져 완전한 해결책은 아니다.
  static class PartialExample {
    String userFirstName;
    String userLastName;
  }

  // Good (완전한 맥락)
  // - User 클래스로 묶으면 firstName/lastName이 자연스럽게 사용자 이름임을 알 수 있다.
  // - 관련 데이터를 하나의 객체로 묶는 것이 가장 좋은 방법이다.
  static class User {
    String firstName;
    String lastName;
  }
}

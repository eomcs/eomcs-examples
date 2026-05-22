package com.eomcs.cleancode.ch02.exam13;

public class BadAndGood3 {

  static class User {
    String type;
    User(String type) { this.type = type; }
    String getType() { return type; }
  }

  // Bad
  // - UserCreator → 생성 클래스임은 알 수 있지만 어떤 생성 패턴인지 불명확하다.
  // - makeUser → 생성 방식과 의도가 드러나지 않는다.
  static class BadUserCreator {
    User makeUser(String type) {
      return new User(type);
    }
  }

  // Good
  // - UserFactory → Factory 패턴임을 이름만으로 즉시 이해할 수 있다.
  // - createUser → Factory의 표준 메서드 이름으로 생성 의도가 명확하다.
  static class GoodUserFactory {
    User createUser(String type) {
      return new User(type);
    }
  }
}

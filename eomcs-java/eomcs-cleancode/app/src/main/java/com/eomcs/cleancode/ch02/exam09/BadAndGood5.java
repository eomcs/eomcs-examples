package com.eomcs.cleancode.ch02.exam09;

public class BadAndGood5 {

  static class User {
    String id;
    boolean active;
  }

  // Bad
  // - fetchUser, getUser, retrieveUser → 같은 의미인데 다른 단어를 사용한다.
  // - API 사용자가 세 메서드의 차이를 알 수 없어 혼란을 준다.
  static class BadUserRepository {
    User fetchUser() { return null; }
    User getUser() { return null; }
    User retrieveUser() { return null; }
  }

  // Good
  // - getUser, getActiveUser, getUserById → 하나의 개념에 하나의 단어(get)만 사용한다.
  // - 각 메서드의 차이는 수식어나 파라미터로 명확히 표현한다.
  static class GoodUserRepository {
    User getUser() { return null; }
    User getActiveUser() { return null; }
    User getUserById(String id) { return null; }
  }
}

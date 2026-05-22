package com.eomcs.cleancode.ch02.exam16;

public class BadAndGood3 {

  // Bad
  // - GSD 라는 프로젝트 이름이 클래스명, 메서드명, 파라미터 타입에 모두 반복된다.
  // - 프로젝트 이름은 패키지나 디렉터리 구조로 이미 표현되므로 코드 안에서 반복할 필요가 없다.
  static class GSDUser {
    String name;
  }

  static class GSDUserService {
    void GSDProcessUser(GSDUser user) {
      System.out.println("사용자 처리: " + user.name);
    }
  }

  // Good
  // - 불필요한 GSD 접두어를 모두 제거하고 핵심 의미만 남긴다.
  // - UserService.processUser(User) 만으로 역할이 충분히 명확하다.
  static class User {
    String name;
  }

  static class UserService {
    void processUser(User user) {
      System.out.println("사용자 처리: " + user.name);
    }
  }
}

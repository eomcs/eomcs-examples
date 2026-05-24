package com.eomcs.cleancode.ch04.exam03;

public class BadAndGood7 {

  static class UserRepository {
    User findByName(String name) { return new User(name); }
  }

  static class User {
    private final String name;
    User(String name) { this.name = name; }
    String getName() { return name; }
  }

  // Bad: trim()이 있지만 왜 중요한지 알 수 없다.
  // - 겉보기에 trim()은 사소한 정리처럼 보인다.
  // - 앞뒤 공백이 있는 이름이 다른 사용자로 처리된다는 사실을 모르면 그냥 삭제할 수 있다.
  // - 나중에 누군가 "불필요한 코드"로 오해하고 제거하면 버그가 발생한다.
  static class BadUserService {
    UserRepository userRepository = new UserRepository();

    User findUser(String name) {
      String normalizedName = name.trim();
      return userRepository.findByName(normalizedName);
    }
  }

  // Good: 중요성을 강조하는 주석 (Amplification)
  // - 겉보기에 사소해 보이지만 핵심적인 역할을 하는 trim()을 강조한다.
  // - "이 줄은 그냥 있는 게 아니다"를 다른 개발자에게 알려준다.
  // - 삭제하거나 변경하기 전에 반드시 주석을 읽게 된다.
  static class GoodUserService {
    UserRepository userRepository = new UserRepository();

    User findUser(String name) {
      String normalizedName = name.trim();
      // trim()이 중요하다.
      // 앞뒤 공백이 있는 이름은 동일 사용자가 아닌 다른 사용자로 처리된다.
      return userRepository.findByName(normalizedName);
    }
  }
}

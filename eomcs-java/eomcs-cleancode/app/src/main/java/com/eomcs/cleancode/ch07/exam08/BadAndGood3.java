package com.eomcs.cleancode.ch07.exam08;

import java.util.Optional;

// 예제 3: null을 전달하지 마라 - process(User) (3가지 개선 방법)
public class BadAndGood3 {

  private BadAndGood3() {}

  static class User {
    private String name;
    User(String name) { this.name = name; }
    String getName() { return name; }
  }

  // Bad: null을 정상 입력처럼 허용한다.
  // - null이 정상적인 값처럼 취급된다.
  // - 함수가 검증과 처리 두 가지 책임을 가진다.
  // - null 체크가 함수 전반에 퍼진다.
  static class BadProcessor {
    public void process(User user) {
      if (user != null) { // null을 묵살한다
        System.out.println(user.getName());
      }
    }
  }

  static class BadClient {
    void run(BadProcessor processor) {
      processor.process(null); // null을 전달해도 아무 일도 일어나지 않는다
    }
  }

  // -----------------------------------------------------------------------

  // Good 방법 1: 함수 내부에서 예외를 던진다.
  // - null이 전달되면 즉시 실패한다.
  // - 함수 계약이 명확해진다.
  static class GoodProcessorV1 {
    public void process(User user) {
      if (user == null) {
        throw new IllegalArgumentException("user must not be null");
      }

      System.out.println(user.getName());
    }
  }

  // Good 방법 2: 호출부에서 null을 미리 걸러낸다.
  // - 함수 자체는 null을 받지 않는다고 가정한다.
  // - null 체크 책임이 호출자에게 있음을 명확히 한다.
  static class GoodProcessorV2 {
    public void process(User user) {
      System.out.println(user.getName());
    }
  }

  static class GoodClientV2 {
    void run(GoodProcessorV2 processor, User user) {
      if (user != null) {
        processor.process(user); // 호출부에서 null을 제거한다
      }
    }
  }

  // Good 방법 3: Optional로 "없을 수 있음"을 타입으로 표현한다.
  // - null 자체를 모델에서 제거한다.
  // - 코드의 의도가 타입으로 명확하게 드러난다.
  static class GoodProcessorV3 {
    public void process(Optional<User> userOpt) {
      userOpt.ifPresent(user ->
          System.out.println(user.getName())
      );
    }
  }

  static class GoodClientV3 {
    void run(GoodProcessorV3 processor, User user) {
      processor.process(Optional.ofNullable(user)); // Optional로 감싸서 전달한다
    }
  }
}

package com.eomcs.cleancode.ch12.exam06;

// 예제 1: 구현이 하나뿐인 인터페이스와 팩토리를 만들지 마라 - UserService
public class BadAndGood1 {

  private BadAndGood1() {}

  // Bad: 과도한 인터페이스와 팩토리
  //   - 구현이 하나뿐인데 인터페이스가 있다
  //   - 팩토리까지 추가되어 구조가 불필요하게 커졌다
  //   - 단순한 이름 검증을 이해하려고 여러 클래스를 봐야 한다
  //   - "확장 가능성"을 미리 예측해서 너무 복잡하게 만든다
  interface BadUserNameValidator {
    boolean validate(String name);
  }

  static class DefaultUserNameValidator implements BadUserNameValidator {

    @Override
    public boolean validate(String name) {
      return name != null && name.length() >= 2;
    }
  }

  static class UserNameValidatorFactory {

    BadUserNameValidator create() {
      return new DefaultUserNameValidator();
    }
  }

  static class BadUserService {

    private final BadUserNameValidator validator;

    BadUserService(UserNameValidatorFactory factory) {
      this.validator = factory.create();
    }

    public void register(String name) {
      if (!validator.validate(name)) {
        throw new IllegalArgumentException("invalid name");
      }
    }
  }

  // Good: 지금 필요한 구조만 남긴다
  //   - 클래스 수가 줄어든다
  //   - 읽는 흐름이 단순해진다
  //   - 나중에 검증 정책이 복잡해질 때 분리해도 늦지 않다
  static class UserService {

    public void register(String name) {
      validateUserName(name);
    }

    private void validateUserName(String name) {
      if (name == null || name.length() < 2) {
        throw new IllegalArgumentException("invalid name");
      }
    }
  }
}

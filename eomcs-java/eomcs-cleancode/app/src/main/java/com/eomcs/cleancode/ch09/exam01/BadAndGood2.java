package com.eomcs.cleancode.ch09.exam01;

// 예제 2: TDD 법칙 세 가지 - PasswordValidator
public class BadAndGood2 {

  private BadAndGood2() {}

  // Bad: 테스트 없이 모든 규칙을 한꺼번에 구현한 비밀번호 검증기.
  // - 어느 규칙이 실제 요구사항인지 테스트로 드러나지 않는다.
  // - 실패 시 어떤 조건 때문에 실패했는지 추적하기 어렵다.
  // - 구현이 테스트보다 앞서간다.
  static class BadPasswordValidator {
    public boolean isValid(String password) {
      if (password == null) {
        return false;
      }
      if (password.length() < 8) {
        return false;
      }
      if (!password.matches(".*[0-9].*")) {
        return false;
      }
      if (!password.matches(".*[A-Z].*")) {
        return false;
      }
      return true;
    }
  }

  static class BadPasswordClient {
    void run() {
      BadPasswordValidator validator = new BadPasswordValidator();

      boolean valid = validator.isValid("Abc12345");
      System.out.println(valid);
    }
  }

  // -----------------------------------------------------------------------

  // Good: TDD로 만들어진 비밀번호 검증기.
  // - 1단계: 비밀번호는_8자_이상이어야_한다 → password.length() >= 8 만 구현
  // - 2단계: 비밀번호에는_숫자가_포함되어야_한다 → matches(".*[0-9].*") 추가
  // - 대문자 조건은 아직 테스트로 요구되지 않았으므로 구현하지 않는다.
  static class GoodPasswordValidator {
    public boolean isValid(String password) {
      return password.length() >= 8
          && password.matches(".*[0-9].*");
    }
  }

  static class GoodPasswordClient {
    void run() {
      GoodPasswordValidator validator = new GoodPasswordValidator();

      boolean valid = validator.isValid("abcdefg1");
      System.out.println(valid);
    }
  }
}

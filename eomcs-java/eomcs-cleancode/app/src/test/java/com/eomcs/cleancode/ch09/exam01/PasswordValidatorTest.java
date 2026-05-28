package com.eomcs.cleancode.ch09.exam01;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam01.BadAndGood2.GoodPasswordValidator;
import org.junit.jupiter.api.Test;

// TDD 단계별 테스트 - PasswordValidator
//
// 1단계: 비밀번호는_8자_이상이어야_한다 → PasswordValidator 없으므로 컴파일 실패
// 2단계: return password.length() >= 8 만 구현 → 첫 번째 테스트 통과
// 3단계: 비밀번호가_8자_이상이면_유효하다 추가 → 통과
// 4단계: 비밀번호에는_숫자가_포함되어야_한다 추가 → 숫자 없는 8자는 false여야 하므로 실패
// 5단계: && password.matches(".*[0-9].*") 추가 → 모든 테스트 통과
class PasswordValidatorTest {

  // 1단계: 실패하는 첫 번째 테스트
  @Test
  void 비밀번호는_8자_이상이어야_한다() {
    GoodPasswordValidator validator = new GoodPasswordValidator();

    boolean valid = validator.isValid("abc");

    assertFalse(valid);
  }

  // 3단계: 통과 확인 테스트
  @Test
  void 비밀번호가_8자_이상이면_유효한지_확인한다() {
    GoodPasswordValidator validator = new GoodPasswordValidator();

    boolean valid = validator.isValid("abcdefg1");

    assertTrue(valid);
  }

  // 4단계: 다음 실패 테스트 - 숫자 없이 8자만으로는 유효하지 않아야 한다
  // → return password.length() >= 8 만 있으면 이 테스트는 실패한다
  @Test
  void 비밀번호에는_숫자가_포함되어야_한다() {
    GoodPasswordValidator validator = new GoodPasswordValidator();

    boolean valid = validator.isValid("abcdefgh"); // 8자지만 숫자 없음

    assertFalse(valid);
  }

  // 5단계: 숫자가 포함된 8자 이상 비밀번호는 유효하다
  @Test
  void 숫자가_포함된_8자_이상_비밀번호는_유효하다() {
    GoodPasswordValidator validator = new GoodPasswordValidator();

    boolean valid = validator.isValid("abcdefg1");

    assertTrue(valid);
  }
}

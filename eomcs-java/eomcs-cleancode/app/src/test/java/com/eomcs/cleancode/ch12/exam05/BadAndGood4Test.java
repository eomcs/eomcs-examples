package com.eomcs.cleancode.ch12.exam05;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch12.exam05.BadAndGood4.AdultVerifiedUserBuilder;
import com.eomcs.cleancode.ch12.exam05.BadAndGood4.User;
import org.junit.jupiter.api.Test;

// 예제 4: 테스트도 도메인 언어처럼 읽혀야 한다
//
// Bad: 테스트 이름이 의미 없고, 구현 조건을 그대로 검증한다.
// Good: 테스트 이름이 요구사항을 설명하고, 행위를 검증한다.
class BadAndGood4Test {

  // Bad: 테스트 이름이 의미 없다
  //   - 무엇을 검증하는지 바로 알기 어렵다
  //   - 구현 조건(>= 19, isEmailVerified)을 그대로 노출한다
  @Test
  void test1() {
    User user = new User("kim@example.com", 20, true);

    assertTrue(user.getAge() >= 19 && user.isEmailVerified());
  }

  // Good: 테스트 이름이 요구사항을 설명한다
  //   - 테스트 코드도 도메인 언어처럼 읽힌다
  //   - 구현 조건이 아니라 행위(canRegister)를 검증한다
  @Test
  void adultVerifiedUserCanRegister() {
    User user = new AdultVerifiedUserBuilder()
        .withEmail("kim@example.com")
        .build();

    assertTrue(user.canRegister());
  }
}

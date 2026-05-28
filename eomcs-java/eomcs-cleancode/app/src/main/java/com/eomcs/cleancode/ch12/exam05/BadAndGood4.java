package com.eomcs.cleancode.ch12.exam05;

// 예제 4: 테스트도 도메인 언어처럼 읽혀야 한다 - User / AdultVerifiedUserBuilder
public class BadAndGood4 {

  private BadAndGood4() {}

  static class User {

    private static final int ADULT_AGE = 19;

    private final String email;
    private final int age;
    private final boolean emailVerified;

    User(String email, int age, boolean emailVerified) {
      this.email = email;
      this.age = age;
      this.emailVerified = emailVerified;
    }

    String getEmail() { return email; }
    int getAge() { return age; }
    boolean isEmailVerified() { return emailVerified; }

    // Good: 행위를 메서드 이름으로 표현한다
    boolean canRegister() {
      return age >= ADULT_AGE && emailVerified;
    }
  }

  // Good: 테스트 의도를 드러내는 빌더
  //   - 테스트에서 "성인이고 이메일이 인증된 사용자"라는 의미를 이름으로 전달한다
  static class AdultVerifiedUserBuilder {

    private String email = "user@example.com";

    AdultVerifiedUserBuilder withEmail(String email) {
      this.email = email;
      return this;
    }

    User build() {
      return new User(email, 20, true); // 성인 + 이메일 인증 완료
    }
  }
}

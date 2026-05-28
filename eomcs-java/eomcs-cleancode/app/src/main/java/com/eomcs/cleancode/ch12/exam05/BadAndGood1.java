package com.eomcs.cleancode.ch12.exam05;

// 예제 1: 조건의 의도를 이름으로 드러내라 - DiscountService / User
public class BadAndGood1 {

  private BadAndGood1() {}

  static class User {

    private static final int ADULT_AGE = 19;

    private final int age;
    private final String status;

    User(int age, String status) {
      this.age = age;
      this.status = status;
    }

    int getAge() { return age; }
    String getStatus() { return status; }

    // Good: 조건의 의도가 이름으로 드러난다
    boolean isAdultActiveMember() {
      return age >= ADULT_AGE && isActive();
    }

    private boolean isActive() {
      return "ACTIVE".equals(status);
    }
  }

  // Bad: 조건의 의미를 직접 해석해야 한다
  //   - 19, "ACTIVE"가 무엇을 뜻하는지 드러나지 않는다
  //   - 할인 조건이라는 도메인 의도가 숨겨져 있다
  static class BadDiscountService {

    private static final int DISCOUNT_RATE = 10;

    int calculateDiscount(User user, int price) {
      if (user.getAge() >= 19 && user.getStatus().equals("ACTIVE")) {
        return price * DISCOUNT_RATE / 100;
      }
      return 0;
    }
  }

  // Good: 읽는 사람이 구현보다 비즈니스 의미를 먼저 이해한다
  //   - 숫자와 문자열의 의미가 명확해진다
  static class DiscountService {

    private static final int ADULT_MEMBER_DISCOUNT_RATE = 10;

    int calculateDiscount(User user, int price) {
      if (user.isAdultActiveMember()) {
        return price * ADULT_MEMBER_DISCOUNT_RATE / 100;
      }
      return 0;
    }
  }
}

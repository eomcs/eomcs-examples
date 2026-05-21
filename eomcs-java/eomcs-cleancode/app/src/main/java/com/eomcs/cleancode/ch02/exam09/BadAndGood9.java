package com.eomcs.cleancode.ch02.exam09;

public class BadAndGood9 {

  // Bad
  // - new User("ADMIN") → 문자열 "ADMIN"의 의미가 불명확하다.
  // - "admin", "Admin" 같은 잘못된 값을 넣어도 컴파일 오류가 나지 않는다.
  static class BadUser {
    String role;
    BadUser(String role) {
      this.role = role;
    }
  }

  static class BadUsage {
    void demo() {
      BadUser admin = new BadUser("ADMIN");
      BadUser guest = new BadUser("GUEST");
      System.out.println(admin.role + ", " + guest.role);
    }
  }

  // Good
  // - createAdminUser, createGuestUser → 메서드 이름이 생성 목적을 직접 설명한다.
  // - 타입 안정성이 높아지고 가독성과 의도가 명확해진다.
  static class GoodUser {
    private String role;

    private GoodUser(String role) {
      this.role = role;
    }

    String getRole() { return role; }

    static GoodUser createAdminUser() {
      return new GoodUser("ADMIN");
    }

    static GoodUser createGuestUser() {
      return new GoodUser("GUEST");
    }
  }

  static class GoodUsage {
    void demo() {
      GoodUser admin = GoodUser.createAdminUser();
      GoodUser guest = GoodUser.createGuestUser();
      System.out.println(admin.getRole() + ", " + guest.getRole());
    }
  }
}

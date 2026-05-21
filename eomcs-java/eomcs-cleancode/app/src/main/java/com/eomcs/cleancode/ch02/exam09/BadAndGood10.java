package com.eomcs.cleancode.ch02.exam09;

public class BadAndGood10 {

  // Bad
  // - 객체 생성 후 상태를 따로 설정한다 → 생성 과정이 분산된다.
  // - setRole, setActive 호출을 빠뜨려도 컴파일 오류가 없어 실수 가능성이 높다.
  static class BadUser {
    private String role;
    private boolean active;

    void setRole(String role) { this.role = role; }
    void setActive(boolean active) { this.active = active; }
    String getRole() { return role; }
    boolean isActive() { return active; }
  }

  static class BadUsage {
    void demo() {
      BadUser user = new BadUser();
      user.setRole("ADMIN");
      user.setActive(true);
      System.out.println(user.getRole() + ", " + user.isActive());
    }
  }

  // Good
  // - createAdminUser → 생성 로직이 한 곳에 집중된다.
  // - 올바른 초기 상태가 보장되고 객체의 일관성이 유지된다.
  static class GoodUser {
    private String role;
    private boolean active;

    private GoodUser(String role, boolean active) {
      this.role = role;
      this.active = active;
    }

    String getRole() { return role; }
    boolean isActive() { return active; }

    static GoodUser createAdminUser() {
      return new GoodUser("ADMIN", true);
    }
  }

  static class GoodUsage {
    void demo() {
      GoodUser admin = GoodUser.createAdminUser();
      System.out.println(admin.getRole() + ", " + admin.isActive());
    }
  }
}

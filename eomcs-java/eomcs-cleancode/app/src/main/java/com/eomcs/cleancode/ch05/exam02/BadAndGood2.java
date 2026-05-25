package com.eomcs.cleancode.ch05.exam02;

// 예제 2: 가로 정렬 (Horizontal Alignment)
public class BadAndGood2 {

  private BadAndGood2() {}

  // Bad: 필드 선언을 세로로 억지 정렬했다.
  // - 타입보다 공백의 길이에 시선이 먼저 간다.
  // - 타입, 이름이 한 묶음으로 읽히지 않는다.
  // - 필드가 추가되면 기존 줄의 정렬을 다시 맞춰야 한다.
  static class BadProfile {
    private String  name;
    private int     age;
    private boolean active;

    BadProfile(String name, int age, boolean active) {
      String  userName   = name;
      int     userAge    = age;
      boolean userActive = active;

      this.name   = userName;
      this.age    = userAge;
      this.active = userActive;
    }

    @Override
    public String toString() {
      return name + ", " + age + ", " + active;
    }
  }

  // Good: 불필요한 정렬을 제거한다.
  // - 각 줄이 자연스럽게 읽힌다.
  // - 자동 포매터와 충돌하지 않는다.
  // - 새 필드가 추가돼도 기존 줄을 수정할 필요가 없다.
  static class GoodProfile {
    private String name;
    private int age;
    private boolean active;

    GoodProfile(String name, int age, boolean active) {
      String userName = name;
      int userAge = age;
      boolean userActive = active;

      this.name = userName;
      this.age = userAge;
      this.active = userActive;
    }

    @Override
    public String toString() {
      return name + ", " + age + ", " + active;
    }
  }
}

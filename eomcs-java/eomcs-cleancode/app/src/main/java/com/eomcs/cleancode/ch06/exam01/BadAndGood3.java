package com.eomcs.cleancode.ch06.exam01;

import java.time.LocalDate;
import java.time.Period;

// 예제 3: 자료 추상화 - User
public class BadAndGood3 {

  private BadAndGood3() {}

  // Bad: 내부 필드를 public으로 직접 노출한다.
  // - 이름 조합 규칙(firstName + " " + lastName)이 외부 코드로 퍼진다.
  // - 나이 계산 규칙(2026 - birthYear)이 외부 코드로 퍼진다.
  // - 내부 데이터 변경 시 외부 코드도 함께 변경해야 한다.
  static class BadUser {
    public String firstName;
    public String lastName;
    public int birthYear;

    BadUser(String firstName, String lastName, int birthYear) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.birthYear = birthYear;
    }
  }

  static class BadUserClient {
    void print(BadUser user) {
      // 외부 코드가 내부 데이터를 직접 조합한다
      String fullName = user.firstName + " " + user.lastName;
      int age = LocalDate.now().getYear() - user.birthYear;
      System.out.println(fullName + " (" + age + "세)");
    }
  }

  // Good: 내부 자료를 숨기고 의미 있는 추상화만 제공한다.
  // - 이름 조합 규칙이 User 내부에 캡슐화된다.
  // - 나이 계산 규칙이 User 내부에 캡슐화된다.
  // - 외부 코드는 데이터를 조합할 필요 없이 의미 있는 메서드를 호출하면 된다.
  // - birthYear를 LocalDate로 바꿔도 외부 코드는 변경되지 않는다.
  static class GoodUser {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    GoodUser(String firstName, String lastName, LocalDate birthDate) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.birthDate = birthDate;
    }

    public String getDisplayName() {
      return firstName + " " + lastName;
    }

    public int getAge() {
      return Period.between(birthDate, LocalDate.now()).getYears();
    }
  }

  static class GoodUserClient {
    void print(GoodUser user) {
      // 외부 코드는 더 이상 내부 필드를 조합하지 않는다
      String name = user.getDisplayName();
      int age = user.getAge();
      System.out.println(name + " (" + age + "세)");
    }
  }
}

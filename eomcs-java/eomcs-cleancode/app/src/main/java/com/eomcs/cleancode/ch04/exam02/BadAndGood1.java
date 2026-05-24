package com.eomcs.cleancode.ch04.exam02;

public class BadAndGood1 {

  static final int HOURLY_FLAG = 0x01;

  static class Employee {
    int flags;
    int age;

    Employee(int flags, int age) { this.flags = flags; this.age = age; }

    // Good: 조건의 의미를 메서드 이름으로 표현한다.
    boolean isEligibleForFullBenefits() {
      return isHourlyEmployee() && isSeniorEmployee();
    }

    private boolean isHourlyEmployee() { return (flags & HOURLY_FLAG) != 0; }
    private boolean isSeniorEmployee() { return age > 65; }
  }

  // Bad
  // - flags & HOURLY_FLAG 비트 연산의 의미를 코드만으로 알 수 없다.
  // - age > 65가 "시니어"를 뜻한다는 사실을 코드가 드러내지 않는다.
  // - 주석 없이는 조건의 목적을 해석해야 한다.
  static class BadBenefitService {
    void giveBenefits(Employee employee) { System.out.println("복지 지급"); }

    void processBenefits(Employee employee) {
      // check if the user is eligible for full benefits
      if ((employee.flags & HOURLY_FLAG) != 0 && employee.age > 65) {
        giveBenefits(employee);
      }
    }
  }

  // Good: 조건을 의미 있는 메서드로 추출하여 주석을 제거한다.
  // - isEligibleForFullBenefits()라는 이름이 조건의 목적을 바로 설명한다.
  // - 비트 연산과 나이 조건의 세부 구현은 Employee 클래스 안에 숨겨진다.
  // - 주석 없이도 코드가 자연어처럼 읽힌다.
  static class GoodBenefitService {
    void giveBenefits(Employee employee) { System.out.println("복지 지급"); }

    void processBenefits(Employee employee) {
      if (employee.isEligibleForFullBenefits()) {
        giveBenefits(employee);
      }
    }
  }
}

package com.eomcs.cleancode.ch03.exam04;

public class BadAndGood2 {

  enum EmployeeType { COMMISSIONED, HOURLY, SALARIED }

  // 다형성 기반 Employee 계층
  abstract static class Employee {
    abstract int calculatePay();
    abstract int calculateBonus();
  }

  static class CommissionedEmployee extends Employee {
    int calculatePay()   { return 1000; }
    int calculateBonus() { return 500; }
  }

  static class HourlyEmployee extends Employee {
    int calculatePay()   { return 800; }
    int calculateBonus() { return 200; }
  }

  static class SalariedEmployee extends Employee {
    int calculatePay()   { return 3000; }
    int calculateBonus() { return 300; }
  }

  // Good: switch를 완전히 없앨 수 없다면, 팩토리 한 곳에만 격리한다.
  // - 생성 책임(어떤 구체 클래스를 만들지)에만 switch를 사용한다.
  // - 이후 사용부에서는 switch 없이 다형성만으로 동작한다.
  static class EmployeeFactory {
    static Employee create(EmployeeType type) {
      switch (type) {
        case COMMISSIONED: return new CommissionedEmployee();
        case HOURLY:       return new HourlyEmployee();
        case SALARIED:     return new SalariedEmployee();
        default: throw new IllegalArgumentException("Unknown type: " + type);
      }
    }
  }

  // 사용부: switch가 숨겨져 있고 다형성만 사용한다.
  static class GoodUsage {
    void demo() {
      Employee e = EmployeeFactory.create(EmployeeType.HOURLY);
      System.out.println("급여:   " + e.calculatePay());
      System.out.println("보너스: " + e.calculateBonus());

      // 새 타입이 추가되더라도 이 코드는 수정할 필요가 없다.
    }
  }
}

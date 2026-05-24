package com.eomcs.cleancode.ch03.exam04;

public class BadAndGood1 {

  enum EmployeeType { COMMISSIONED, HOURLY, SALARIED }

  // Bad
  // - switch로 타입별 분기 → 새 타입 추가 시 calculatePay, calculateBonus, calculateTax
  //   모든 switch를 찾아서 수정해야 한다 (유지보수 비용 폭발).
  // - 동일한 switch 구조가 세 메서드에 반복된다 (중복).
  // - SRP 위반: 하나의 함수가 여러 타입의 처리 방식을 모두 알고 있다.
  static class BadEmployeeService {
    static class Employee {
      private EmployeeType type;
      private int hourlyRate;
      private int hoursWorked;
      Employee(EmployeeType type, int hourlyRate, int hoursWorked) {
        this.type = type; this.hourlyRate = hourlyRate; this.hoursWorked = hoursWorked;
      }
      EmployeeType getType() { return type; }
      int getHourlyRate() { return hourlyRate; }
      int getHoursWorked() { return hoursWorked; }
    }

    int calculatePay(Employee e) {
      switch (e.getType()) {
        case COMMISSIONED: return 1000;
        case HOURLY:       return e.getHourlyRate() * e.getHoursWorked();
        case SALARIED:     return 3000;
        default: throw new IllegalArgumentException("Unknown type");
      }
    }

    int calculateBonus(Employee e) {
      switch (e.getType()) {
        case COMMISSIONED: return 500;
        case HOURLY:       return 200;
        case SALARIED:     return 300;
        default: throw new IllegalArgumentException("Unknown type");
      }
    }

    int calculateTax(Employee e) {
      switch (e.getType()) {
        case COMMISSIONED: return 100;
        case HOURLY:       return e.getHourlyRate() * e.getHoursWorked() / 10;
        case SALARIED:     return 300;
        default: throw new IllegalArgumentException("Unknown type");
      }
    }
  }

  // Good
  // - 다형성으로 switch를 완전히 제거한다.
  // - 각 클래스가 자신의 로직만 책임진다 (SRP 준수).
  // - 새 타입 추가 시 기존 코드 수정 없이 새 클래스를 추가하기만 하면 된다 (OCP 준수).
  abstract static class Employee {
    abstract int calculatePay();
    abstract int calculateBonus();
    abstract int calculateTax();
  }

  static class CommissionedEmployee extends Employee {
    int calculatePay()   { return 1000; }
    int calculateBonus() { return 500; }
    int calculateTax()   { return 100; }
  }

  static class HourlyEmployee extends Employee {
    private int hourlyRate;
    private int hoursWorked;
    HourlyEmployee(int hourlyRate, int hoursWorked) {
      this.hourlyRate = hourlyRate; this.hoursWorked = hoursWorked;
    }
    int calculatePay()   { return hourlyRate * hoursWorked; }
    int calculateBonus() { return 200; }
    int calculateTax()   { return calculatePay() / 10; }
  }

  static class SalariedEmployee extends Employee {
    int calculatePay()   { return 3000; }
    int calculateBonus() { return 300; }
    int calculateTax()   { return 300; }
  }

  static class GoodUsage {
    void demo() {
      Employee employee = new HourlyEmployee(10, 160);
      System.out.println("급여:   " + employee.calculatePay());
      System.out.println("보너스: " + employee.calculateBonus());
      System.out.println("세금:   " + employee.calculateTax());
    }
  }
}

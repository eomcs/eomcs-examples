package com.eomcs.cleancode.ch07.exam06;

// 예제 1: 정상 흐름을 정의하라 - MealExpense (Special Case Pattern)
public class BadAndGood1 {

  private BadAndGood1() {}

  static class Employee {
    private String name;
    private int mealPerDiem;

    Employee(String name, int mealPerDiem) {
      this.name = name;
      this.mealPerDiem = mealPerDiem;
    }

    String getName() { return name; }
    int getMealPerDiem() { return mealPerDiem; }
  }

  // Bad: 식비 내역 없음을 예외로 처리한다.
  static class MealExpenseNotFoundException extends RuntimeException {
    MealExpenseNotFoundException() { super("식비 내역이 없습니다."); }
  }

  static class BadMealExpense {
    private int total;
    BadMealExpense(int total) { this.total = total; }
    int getTotal() { return total; }
  }

  interface BadMealExpenseRepository {
    BadMealExpense findMealExpense(Employee employee);
  }

  // Bad: null이면 예외를 던진다.
  // - 식비 내역이 없는 상황이 실제 오류처럼 처리된다.
  // - 정상 업무 규칙("없으면 기본 식비")이 catch 블록 안에 숨는다.
  // - 호출자가 예외 상황과 기본 식비 정책을 모두 알아야 한다.
  static class BadExpenseReport {
    private BadMealExpenseRepository repository;
    BadExpenseReport(BadMealExpenseRepository repo) { this.repository = repo; }

    public BadMealExpense getMealExpense(Employee employee) {
      BadMealExpense mealExpense = repository.findMealExpense(employee);

      if (mealExpense == null) {
        throw new MealExpenseNotFoundException();
      }

      return mealExpense;
    }
  }

  static class BadExpenseClient {
    void calculate(BadExpenseReport expenseReport, Employee employee) {
      int total = 0;

      try {
        BadMealExpense mealExpense = expenseReport.getMealExpense(employee);
        total += mealExpense.getTotal();
      } catch (MealExpenseNotFoundException e) {
        total += employee.getMealPerDiem(); // 업무 규칙이 catch 블록에 숨어 있다
      }

      System.out.println(employee.getName() + " 식비 합계: " + total);
    }
  }

  // -----------------------------------------------------------------------

  // Good: MealExpense를 인터페이스로 만들고 특수 사례 객체로 정상 흐름을 흡수한다.
  interface MealExpense {
    int getTotal();
  }

  // 실제 식비 내역
  static class ActualMealExpense implements MealExpense {
    private final int total;
    ActualMealExpense(int total) { this.total = total; }

    @Override
    public int getTotal() { return total; }
  }

  // Special Case Object: 식비 내역이 없을 때 기본 식비를 반환한다.
  // - "없음"을 예외가 아니라 정상 객체로 표현한다.
  // - 호출자는 실제 식비인지 기본 식비인지 구분하지 않아도 된다.
  static class PerDiemMealExpense implements MealExpense {
    private final Employee employee;
    PerDiemMealExpense(Employee employee) { this.employee = employee; }

    @Override
    public int getTotal() { return employee.getMealPerDiem(); }
  }

  interface GoodMealExpenseRepository {
    ActualMealExpense findMealExpense(Employee employee);
  }

  // Good: null이면 Special Case Object를 반환한다.
  // - 호출자는 try-catch 없이 정상 흐름만 표현하면 된다.
  // - "식비 내역이 없으면 기본 식비를 쓴다"는 규칙이 객체 안에 캡슐화된다.
  static class GoodExpenseReport {
    private GoodMealExpenseRepository repository;
    GoodExpenseReport(GoodMealExpenseRepository repo) { this.repository = repo; }

    public MealExpense getMealExpense(Employee employee) {
      ActualMealExpense mealExpense = repository.findMealExpense(employee);

      if (mealExpense == null) {
        return new PerDiemMealExpense(employee);
      }

      return mealExpense;
    }
  }

  static class GoodExpenseClient {
    void calculate(GoodExpenseReport expenseReport, Employee employee) {
      int total = 0;

      MealExpense mealExpense = expenseReport.getMealExpense(employee);
      total += mealExpense.getTotal(); // 정상 흐름만 보인다

      System.out.println(employee.getName() + " 식비 합계: " + total);
    }
  }
}

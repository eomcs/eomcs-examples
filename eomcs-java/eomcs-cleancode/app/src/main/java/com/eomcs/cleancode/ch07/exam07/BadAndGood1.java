package com.eomcs.cleancode.ch07.exam07;

import java.util.Collections;
import java.util.List;

// 예제 1: null을 반환하지 마라 - getEmployees (빈 리스트 반환)
public class BadAndGood1 {

  private BadAndGood1() {}

  static class Employee {
    private String name;
    Employee(String name) { this.name = name; }
    String getName() { return name; }
  }

  interface EmployeeRepository {
    boolean isEmpty();
    List<Employee> findAll();
  }

  // Bad: 직원이 없을 때 null을 반환한다.
  // - 호출자가 항상 null 체크를 강제당한다.
  // - null 체크를 빠뜨리면 NPE가 발생한다.
  // - "직원이 없음"과 "오류"를 구분할 수 없다.
  static class BadEmployeeService {
    private EmployeeRepository repository;
    BadEmployeeService(EmployeeRepository repo) { this.repository = repo; }

    public List<Employee> getEmployees() {
      if (repository.isEmpty()) {
        return null; // 호출자에게 null 체크를 떠넘긴다
      }
      return repository.findAll();
    }
  }

  static class BadEmployeeClient {
    void run(BadEmployeeService employeeService) {
      List<Employee> employees = employeeService.getEmployees();

      // null 체크를 빠뜨리면 NPE 발생
      if (employees != null) {
        for (Employee e : employees) {
          System.out.println(e.getName());
        }
      }
    }
  }

  // -----------------------------------------------------------------------

  // Good: 직원이 없을 때 빈 리스트를 반환한다.
  // - 호출자가 null 체크 없이 바로 사용할 수 있다.
  // - NPE 위험이 없다.
  // - "직원이 없음"이 빈 컬렉션으로 명확하게 표현된다.
  static class GoodEmployeeService {
    private EmployeeRepository repository;
    GoodEmployeeService(EmployeeRepository repo) { this.repository = repo; }

    public List<Employee> getEmployees() {
      if (repository.isEmpty()) {
        return Collections.emptyList(); // 빈 리스트로 "없음"을 표현한다
      }
      return repository.findAll();
    }
  }

  static class GoodEmployeeClient {
    void run(GoodEmployeeService employeeService) {
      List<Employee> employees = employeeService.getEmployees();

      // null 체크 없이 바로 사용한다
      for (Employee e : employees) {
        System.out.println(e.getName());
      }
    }
  }
}

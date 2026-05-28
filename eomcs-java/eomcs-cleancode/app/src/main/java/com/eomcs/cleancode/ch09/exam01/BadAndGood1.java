package com.eomcs.cleancode.ch09.exam01;

// 예제 1: TDD 법칙 세 가지 - Calculator
public class BadAndGood1 {

  private BadAndGood1() {}

  // Bad: 테스트 없이 한꺼번에 구현한 계산기.
  // - 테스트가 없어서 코드가 올바른지 자동으로 검증할 수 없다.
  // - subtract(), multiply() 같은 기능이 실제로 필요한지 알 수 없다.
  // - 요구사항보다 많은 기능을 미리 구현했다.
  // - 코드 변경 후 기존 기능이 깨졌는지 확인하기 어렵다.
  static class BadCalculator {
    public int add(int a, int b) { return a + b; }
    public int subtract(int a, int b) { return a - b; }
    public int multiply(int a, int b) { return a * b; }
  }

  static class BadCalculatorClient {
    void run() {
      BadCalculator calculator = new BadCalculator();

      int result = calculator.add(2, 3);
      System.out.println(result);
    }
  }

  // -----------------------------------------------------------------------

  // Good: TDD로 만들어진 계산기.
  // - 실패 테스트가 요구한 만큼만 구현한다.
  // - add()는 두 개의 테스트(두_수를_더한다, 다른_두_수도_더한다)를 통해 등장했다.
  // - subtract(), multiply()는 아직 테스트로 요구되지 않았으므로 존재하지 않는다.
  static class GoodCalculator {
    public int add(int a, int b) {
      return a + b;
    }
  }

  static class GoodCalculatorClient {
    void run() {
      GoodCalculator calculator = new GoodCalculator();

      int result = calculator.add(2, 3);
      System.out.println(result);
    }
  }
}

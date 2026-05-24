package com.eomcs.cleancode.ch03.exam06;

public class BadAndGood9 {

  // Bad
  // - assertEquals(expected, actual) → expected와 actual 중 어느 쪽이 먼저인지
  //   호출부에서 순서를 기억해야 한다.
  static class BadAssertion {
    void assertEquals(int expected, int actual) {
      if (expected != actual) {
        throw new AssertionError("Expected: " + expected + ", but was: " + actual);
      }
    }
  }

  // Good: 함수 이름에 인자 순서를 포함한다.
  // - assertExpectedEqualsActual → 이름 자체가 인자 순서를 알려주어 기억할 필요가 없다.
  static class GoodAssertion1 {
    void assertExpectedEqualsActual(int expected, int actual) {
      if (expected != actual) {
        throw new AssertionError("Expected: " + expected + ", but was: " + actual);
      }
    }
  }

  // Good: 인자 이름으로 의미를 표현한다.
  // - expectedValue, actualValue → 파라미터 이름 자체가 역할을 설명해 순서 의존성이 줄어든다.
  static class GoodAssertion2 {
    void assertEquals(int expectedValue, int actualValue) {
      if (expectedValue != actualValue) {
        throw new AssertionError("Expected: " + expectedValue + ", but was: " + actualValue);
      }
    }
  }
}

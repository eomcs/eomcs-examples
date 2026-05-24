package com.eomcs.cleancode.ch03.exam06;

public class BadAndGood4 {

  // Bad
  // - compare(a, b) → 어떤 비교인지, a와 b의 관계가 불명확하다.
  // - 호출부에서 compare(x, y) 를 봐도 무슨 결과를 반환하는지 알 수 없다.
  static class BadComparator {
    boolean compare(int a, int b) {
      return a > b;
    }
  }

  // Good
  // - 2개 인자는 허용되지만 두 인자의 관계가 명확해야 한다.
  // - isGreaterThan(value, threshold) → value가 threshold보다 크면 true임이 이름에 표현된다.
  static class GoodComparator {
    boolean isGreaterThan(int value, int threshold) {
      return value > threshold;
    }
  }
}

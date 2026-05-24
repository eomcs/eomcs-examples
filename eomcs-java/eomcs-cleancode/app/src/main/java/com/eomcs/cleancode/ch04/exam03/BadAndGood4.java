package com.eomcs.cleancode.ch04.exam03;

public class BadAndGood4 {

  static class Role implements Comparable<Role> {
    private final int priority;
    Role(int priority) { this.priority = priority; }

    @Override
    public int compareTo(Role other) { return Integer.compare(this.priority, other.priority); }

    @Override
    public boolean equals(Object o) {
      if (o instanceof Role other) return this.priority == other.priority;
      return false;
    }

    @Override
    public int hashCode() { return Integer.hashCode(priority); }
  }

  // Bad: compareTo 결과값만 있고 그 의미를 알 수 없다.
  // - compareTo의 반환값(음수/0/양수)이 비교 맥락에서 어떤 순서를 뜻하는지 불명확하다.
  // - 읽는 사람이 compareTo 계약을 알고 있어야만 이해할 수 있다.
  static class BadAssertionTest {
    void test(Role admin, Role user, Role guest) {
      assert admin.compareTo(admin) == 0;
      assert admin.compareTo(user) < 0;
      assert guest.compareTo(user) > 0;
    }
  }

  // Good: 의미를 명료하게 밝히는 주석 (Clarification)
  // - 수정할 수 없는 compareTo API의 반환값 의미를 짧은 주석으로 보완한다.
  // - 각 assert가 무엇을 검증하는지 한눈에 파악할 수 있다.
  // - 단, 주석이 코드와 불일치하면 매우 위험하므로 항상 동기화해야 한다.
  static class GoodAssertionTest {
    void test(Role admin, Role user, Role guest) {
      assert admin.compareTo(admin) == 0; // admin == admin
      assert admin.compareTo(user) < 0;   // admin comes before user
      assert guest.compareTo(user) > 0;   // guest comes after user
    }
  }
}

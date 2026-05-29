package com.eomcs.cleancode.ch13.exam03;

import java.util.ArrayList;
import java.util.List;

// 예제 3: 데이터 복사 - 공유하지 말고 복사해서 사용하라
public class BadAndGood3 {

  private BadAndGood3() {}

  // Bad: 외부에서 전달된 리스트의 참조를 그대로 보관한다
  //   - 다른 스레드에서 numbers를 변경하면 sum() 결과가 달라진다
  //   - 결과가 불안정하다
  //   - 동기화 없이는 안전하지 않다
  static class BadStatistics {

    private List<Integer> numbers;

    BadStatistics(List<Integer> numbers) {
      this.numbers = numbers; // 참조 공유
    }

    public int sum() {
      return numbers.stream().mapToInt(i -> i).sum();
    }
  }

  // Good: 생성 시점에 복사하여 내부 상태를 외부로부터 격리한다
  //   - 외부에서 원본 리스트를 변경해도 영향받지 않는다
  //   - 동기화 없이도 안전하다
  //   - 복사는 메모리를 쓰지만 동기화보다 훨씬 단순하고 안전하다
  static class Statistics {

    private final List<Integer> numbers;

    Statistics(List<Integer> numbers) {
      this.numbers = new ArrayList<>(numbers); // 복사
    }

    public int sum() {
      return numbers.stream().mapToInt(i -> i).sum();
    }
  }
}

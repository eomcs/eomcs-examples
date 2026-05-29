package com.eomcs.cleancode.ch13.exam01;

import java.util.List;

// 예제 3: 오해 1 - "동시성은 항상 성능을 향상시킨다"
// 동시성은 공짜가 아니다: 컨텍스트 스위칭 비용, 스레드 생성 비용, 락 경쟁이 존재한다
public class BadAndGood3 {

  private BadAndGood3() {}

  // Bad: 작은 데이터에 불필요한 병렬 처리를 적용한다
  //   - 스레드 관리 비용이 실제 연산 비용보다 클 수 있다
  //   - 오히려 순차 처리보다 느려질 수 있다
  static class BadSumCalculator {

    public int sum(List<Integer> numbers) {
      return numbers.parallelStream()
          .mapToInt(i -> i)
          .sum();
    }
  }

  // Good: 데이터 크기와 연산 비용을 고려하여 처리 방식을 선택한다
  //   - 작은 데이터는 순차 처리가 더 효율적이다
  //   - 병렬 처리는 대용량 데이터나 CPU 집약적 연산에 적합하다
  static class SumCalculator {

    private static final int PARALLEL_THRESHOLD = 10_000;

    public int sum(List<Integer> numbers) {
      if (numbers.size() < PARALLEL_THRESHOLD) {
        return numbers.stream()
            .mapToInt(i -> i)
            .sum();
      }
      return numbers.parallelStream()
          .mapToInt(i -> i)
          .sum();
    }
  }
}

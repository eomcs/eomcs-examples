package com.eomcs.advanced.stream.exam11;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;

// 단순 집계 - 기본형 스트림 집계:
//
// 기본형 스트림(IntStream, LongStream, DoubleStream)은
// 박싱 없이 sum(), average(), min(), max(), summaryStatistics()를 제공한다.
//
// sum()
//   - 모든 요소의 합계를 반환한다. (int / long / double)
//   - 스트림이 비어 있으면 0을 반환한다.
//
// average()
//   - 모든 요소의 평균을 OptionalDouble로 반환한다.
//   - 스트림이 비어 있으면 OptionalDouble.empty()를 반환한다.
//
// min() / max() (기본형 스트림)
//   - Comparator 없이 호출한다.
//   - OptionalInt / OptionalLong / OptionalDouble을 반환한다.
//
// summaryStatistics()
//   - count, sum, min, max, average를 단 1번 순회로 계산한다.
//   - IntSummaryStatistics / LongSummaryStatistics / DoubleSummaryStatistics 반환.
//

public class App2 {

  public static void main(String[] args) {

    List<Integer> scores = Arrays.asList(85, 92, 78, 95, 88, 70, 99, 83, 91, 76);

    // ─────────────────────────────────────────────────────────────
    // 예제 1. sum() - 합계
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] sum() - 합계");

    int total = scores.stream()
        .mapToInt(Integer::intValue)
        .sum();
    System.out.println("  점수 합계: " + total); // 857

    // 짝수만 합계
    int evenSum = scores.stream()
        .mapToInt(Integer::intValue)
        .filter(n -> n % 2 == 0)
        .sum();
    System.out.println("  짝수 합계: " + evenSum);

    // 빈 스트림의 sum은 0
    int emptySum = scores.stream()
        .mapToInt(Integer::intValue)
        .filter(n -> n > 100)
        .sum();
    System.out.println("  빈 스트림 sum: " + emptySum); // 0

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. average() - 평균
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] average() - 평균");

    OptionalDouble avg = scores.stream()
        .mapToInt(Integer::intValue)
        .average();
    System.out.printf("  평균: %.2f%n", avg.orElse(0.0)); // 85.70

    // 90점 이상 평균
    OptionalDouble highAvg = scores.stream()
        .mapToInt(Integer::intValue)
        .filter(n -> n >= 90)
        .average();
    System.out.printf("  90점 이상 평균: %.2f%n", highAvg.orElse(0.0));

    // 빈 스트림의 average는 OptionalDouble.empty()
    OptionalDouble emptyAvg = scores.stream()
        .mapToInt(Integer::intValue)
        .filter(n -> n > 100)
        .average();
    System.out.println("  빈 스트림 average: " + emptyAvg); // OptionalDouble.empty

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. min() / max() - 기본형 스트림
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] min() / max() - 기본형 스트림 (Comparator 불필요)");

    OptionalInt min = scores.stream().mapToInt(Integer::intValue).min();
    OptionalInt max = scores.stream().mapToInt(Integer::intValue).max();

    System.out.println("  최저 점수: " + min.orElse(-1)); // 70
    System.out.println("  최고 점수: " + max.orElse(-1)); // 99

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. summaryStatistics() - 1번 순회로 모든 통계
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] summaryStatistics() - 단 1번 순회로 모든 통계");

    // 각각 별도로 계산하면 스트림을 5번 순회
    // summaryStatistics()는 단 1번 순회로 모두 계산
    IntSummaryStatistics stats = scores.stream()
        .mapToInt(Integer::intValue)
        .summaryStatistics();

    System.out.println("  개수:   " + stats.getCount());                      // 10
    System.out.println("  합계:   " + stats.getSum());                        // 857
    System.out.printf("  평균:   %.2f%n", stats.getAverage());               // 85.70
    System.out.println("  최솟값: " + stats.getMin());                        // 70
    System.out.println("  최댓값: " + stats.getMax());                        // 99

    System.out.println();
    System.out.println("→ sum()은 비어 있으면 0, average()는 비어 있으면 OptionalDouble.empty()를 반환한다.");
    System.out.println("→ 기본형 스트림의 min()/max()는 Comparator 없이 OptionalInt 등을 반환한다.");
    System.out.println("→ summaryStatistics()로 count/sum/average/min/max를 단 1번 순회로 구한다.");
  }
}

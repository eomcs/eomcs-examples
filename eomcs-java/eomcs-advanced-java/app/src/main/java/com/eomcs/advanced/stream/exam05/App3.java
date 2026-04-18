package com.eomcs.advanced.stream.exam05;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

// 매핑 - 기본형 특화 스트림 변환:
//
// mapToInt(ToIntFunction<T>)    : Stream<T> → IntStream
// mapToLong(ToLongFunction<T>)  : Stream<T> → LongStream
// mapToDouble(ToDoubleFunction<T>): Stream<T> → DoubleStream
//
// 기본형 스트림의 장점:
//   - 박싱(boxing) 오버헤드가 없다. Integer 객체 대신 int 기본형을 직접 다룬다.
//   - sum(), average(), min(), max(), summaryStatistics()를 추가로 제공한다.
//   - 대량 수치 데이터 처리에서 Stream<Integer>보다 성능이 유리하다.
//
// 기본형 스트림 → 객체 스트림 복귀:
//   intStream.boxed()         : IntStream → Stream<Integer>
//   intStream.mapToObj(f)     : IntStream → Stream<R>
//   intStream.asLongStream()  : IntStream → LongStream
//   intStream.asDoubleStream(): IntStream → DoubleStream
//
// IntSummaryStatistics:
//   - count, sum, min, max, average를 한 번의 순회로 모두 계산한다.
//   - summaryStatistics()로 반환된다.
//

public class App3 {

  public static void main(String[] args) {

    // ─────────────────────────────────────────────────────────────
    // 예제 1. mapToInt - 객체 → IntStream 변환
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] mapToInt - 객체 → IntStream");

    List<String> words = Arrays.asList("Java", "Stream", "API", "is", "powerful");

    // Stream<String>에서 각 단어의 길이를 IntStream으로 추출
    System.out.print("  단어 길이: ");
    words.stream()
        .mapToInt(String::length) // String → int (언박싱 없이 기본형 int)
        .forEach(len -> System.out.print(len + " ")); // 4 6 3 2 8
    System.out.println();

    // IntStream이므로 sum(), average(), max(), min() 바로 사용 가능
    int    total = words.stream().mapToInt(String::length).sum();
    double avg   = words.stream().mapToInt(String::length).average().orElse(0);
    int    max   = words.stream().mapToInt(String::length).max().orElse(0);
    int    min   = words.stream().mapToInt(String::length).min().orElse(0);

    System.out.println("  합계:  " + total); // 23
    System.out.printf( "  평균:  %.1f%n", avg); // 4.6
    System.out.println("  최대:  " + max);   // 8
    System.out.println("  최소:  " + min);   // 2

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. Stream<Integer> vs IntStream - 박싱 오버헤드 비교
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] Stream<Integer> vs IntStream - 박싱 오버헤드");

    List<String> scores = Arrays.asList("85", "92", "78", "95", "88");

    // Stream<Integer>: parseInt → Integer 박싱 → 합계 계산 시 다시 언박싱
    int sum1 = scores.stream()
        .map(Integer::parseInt)      // String → Integer (박싱 발생)
        .reduce(0, (a, b) -> a + b); // Integer → int (언박싱 발생)
    System.out.println("  Stream<Integer> 합계: " + sum1); // 438

    // IntStream: parseInt → int 바로 사용 (박싱 없음)
    int sum2 = scores.stream()
        .mapToInt(Integer::parseInt) // String → int (박싱 없음)
        .sum();                       // int 기본형으로 바로 계산
    System.out.println("  IntStream 합계:       " + sum2); // 438

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. summaryStatistics - 한 번에 통계 계산
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] IntSummaryStatistics - 한 번에 통계 계산");

    List<Integer> numbers = Arrays.asList(45, 82, 67, 91, 53, 78, 36, 95, 62, 74);

    // 방법 1: 각각 별도 스트림으로 계산 → 4번 순회
    long   count = numbers.stream().count();
    int    sumV  = numbers.stream().mapToInt(Integer::intValue).sum();
    double avgV  = numbers.stream().mapToInt(Integer::intValue).average().orElse(0);
    int    maxV  = numbers.stream().mapToInt(Integer::intValue).max().orElse(0);
    int    minV  = numbers.stream().mapToInt(Integer::intValue).min().orElse(0);
    System.out.printf("  개별 계산: count=%d, sum=%d, avg=%.1f, max=%d, min=%d%n",
        count, sumV, avgV, maxV, minV);

    // 방법 2: summaryStatistics() → 단 1번 순회로 모두 계산
    java.util.IntSummaryStatistics stats = numbers.stream()
        .mapToInt(Integer::intValue)
        .summaryStatistics(); // 한 번에 count, sum, average, max, min 계산
    System.out.printf("  summaryStatistics: count=%d, sum=%d, avg=%.1f, max=%d, min=%d%n",
        stats.getCount(), stats.getSum(), stats.getAverage(),
        stats.getMax(), stats.getMin());

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. mapToLong - 큰 수치 계산
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] mapToLong - 큰 수치 계산");

    List<String> fileSizes = Arrays.asList(
        "1073741824",  // 1 GB
        "536870912",   // 512 MB
        "2147483648",  // 2 GB
        "268435456"    // 256 MB
    );

    // 파일 크기 합계 (int 범위 초과 → long 필요)
    long totalBytes = fileSizes.stream()
        .mapToLong(Long::parseLong) // String → long
        .sum();
    System.out.printf("  총 용량: %,d bytes (%.2f GB)%n",
        totalBytes, totalBytes / 1_073_741_824.0); // 약 3.75 GB

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. mapToDouble - 실수 계산
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] mapToDouble - 실수 계산");

    List<String> prices = Arrays.asList("10.5", "23.8", "7.2", "15.0", "42.3");

    // 가격 평균
    double avgPrice = prices.stream()
        .mapToDouble(Double::parseDouble)
        .average()
        .orElse(0);
    System.out.printf("  평균 가격: %.2f%n", avgPrice); // 19.76

    // 할인 적용 후 합계 (10% 할인)
    double discounted = prices.stream()
        .mapToDouble(Double::parseDouble)
        .map(p -> p * 0.9) // 10% 할인 (DoubleStream.map은 double → double)
        .sum();
    System.out.printf("  할인 후 합계: %.2f%n", discounted);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 6. 기본형 스트림 → 객체 스트림 복귀
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 6] 기본형 스트림 → 객체 스트림 변환");

    // boxed(): IntStream → Stream<Integer>
    List<Integer> boxedList = IntStream.rangeClosed(1, 5)
        .boxed()   // int → Integer 박싱
        .toList();
    System.out.println("  boxed():   " + boxedList); // [1, 2, 3, 4, 5]

    // mapToObj(): IntStream → Stream<R> (원소를 객체로 변환)
    List<String> formatted = IntStream.rangeClosed(1, 5)
        .mapToObj(i -> String.format("item-%02d", i)) // int → String
        .toList();
    System.out.println("  mapToObj: " + formatted); // [item-01, item-02, ...]

    // asLongStream / asDoubleStream: 기본형 스트림끼리 변환
    long   longSum   = IntStream.rangeClosed(1, 10).asLongStream().sum();
    double doubleAvg = IntStream.rangeClosed(1, 10).asDoubleStream().average().orElse(0);
    System.out.println("  asLongStream sum:    " + longSum);       // 55
    System.out.printf( "  asDoubleStream avg:  %.1f%n", doubleAvg); // 5.5

    System.out.println();
    System.out.println("→ mapToInt/Long/Double은 박싱 오버헤드 없이 기본형 스트림을 만든다.");
    System.out.println("→ summaryStatistics()는 count/sum/average/max/min을 단 1번 순회로 계산한다.");
    System.out.println("→ boxed()는 기본형 스트림을 객체 스트림으로, asLongStream/asDoubleStream은 기본형끼리 변환한다.");
  }
}

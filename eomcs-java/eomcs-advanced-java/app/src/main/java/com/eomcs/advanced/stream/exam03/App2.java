package com.eomcs.advanced.stream.exam03;

import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

// 스트림 소스 생성 (2) - 숫자 범위 스트림:
//
// IntStream / LongStream / DoubleStream
//   - 기본형(primitive)을 직접 다루는 특화 스트림이다.
//   - Integer, Long, Double로 박싱(boxing)하는 오버헤드가 없다.
//   - sum(), average(), min(), max() 같은 수치 집계 메서드를 추가로 제공한다.
//
// IntStream.range(start, end)
//   - [start, end) 범위의 정수를 순서대로 생성한다. (end 미포함)
//   - for (int i = start; i < end; i++) 패턴의 선언형 대체이다.
//
// IntStream.rangeClosed(start, end)
//   - [start, end] 범위의 정수를 순서대로 생성한다. (end 포함)
//
// IntStream.of(values...)
//   - 지정한 int 값들로 스트림을 생성한다.
//
// Random.ints() / longs() / doubles()
//   - 난수 스트림을 생성한다. 기본적으로 무한 스트림이므로 limit()으로 제한한다.
//   - Random.ints(count, min, max) 처럼 크기와 범위를 함께 지정할 수도 있다.
//

public class App2 {

  public static void main(String[] args) {

    // ─────────────────────────────────────────────────────────────
    // 예제 1. IntStream.range() - for 루프를 선언형으로 대체
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] IntStream.range() vs for 루프");

    // 명령형: 인덱스 기반 for 루프
    System.out.print("  for 루프:      ");
    for (int i = 0; i < 5; i++) {
      System.out.print(i + " "); // 0 1 2 3 4
    }
    System.out.println();

    // 선언형: range(start, end) - end 미포함 [0, 5)
    System.out.print("  range(0,5):    ");
    IntStream.range(0, 5).forEach(i -> System.out.print(i + " ")); // 0 1 2 3 4
    System.out.println();

    // rangeClosed(start, end) - end 포함 [1, 5]
    System.out.print("  rangeClosed(1,5): ");
    IntStream.rangeClosed(1, 5).forEach(i -> System.out.print(i + " ")); // 1 2 3 4 5
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. 숫자 범위 스트림으로 수치 계산
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] 범위 스트림으로 수치 계산");

    // 1부터 100까지의 합 (가우스 공식: 5050)
    int sum = IntStream.rangeClosed(1, 100).sum();
    System.out.println("  1~100 합계:      " + sum); // 5050

    // 1부터 10까지의 평균
    double avg = IntStream.rangeClosed(1, 10).average().orElse(0);
    System.out.println("  1~10 평균:       " + avg); // 5.5

    // 짝수만 골라 합계 (2+4+...+10 = 30)
    int evenSum = IntStream.rangeClosed(1, 10)
        .filter(n -> n % 2 == 0)
        .sum();
    System.out.println("  1~10 짝수 합계:  " + evenSum); // 30

    // 1~5 팩토리얼: 1×2×3×4×5 = 120
    int factorial = IntStream.rangeClosed(1, 5)
        .reduce(1, (acc, n) -> acc * n); // reduce 초기값 = 1
    System.out.println("  5!:              " + factorial); // 120

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. LongStream.range() - 큰 범위 순회
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] LongStream.range() - 큰 범위 순회");

    // 1부터 1,000,000까지의 합
    long bigSum = LongStream.rangeClosed(1, 1_000_000).sum();
    System.out.println("  1~1,000,000 합계: " + bigSum); // 500000500000

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. IntStream.of() - 특정 값들로 스트림 생성
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] IntStream.of() / DoubleStream.of()");

    // 특정 정수들
    IntStream.of(10, 20, 30, 40, 50)
        .forEach(n -> System.out.print(n + " "));
    System.out.println();

    // 특정 실수들
    DoubleStream.of(1.5, 2.5, 3.5)
        .map(d -> d * 2)
        .forEach(d -> System.out.print(d + " ")); // 3.0 5.0 7.0
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. Random.ints() - 난수 스트림
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] Random.ints() - 난수 스트림");

    Random random = new Random(42); // seed 고정으로 재현 가능한 난수

    // 기본: 무한 스트림 → limit()으로 5개만 가져온다.
    System.out.print("  무한 스트림(limit 5):  ");
    random.ints()               // 무한 난수 스트림
        .limit(5)               // 5개로 제한
        .forEach(n -> System.out.print(n + " "));
    System.out.println();

    // 범위 지정: ints(streamSize, origin, bound) → [origin, bound) 범위의 난수 streamSize개
    System.out.print("  ints(5, 1, 10):        ");
    new Random(42).ints(5, 1, 10) // 1 이상 10 미만의 난수 5개
        .forEach(n -> System.out.print(n + " "));
    System.out.println();

    // doubles: [0.0, 1.0) 범위의 난수
    System.out.print("  doubles(3, 0.0, 1.0):  ");
    new Random(42).doubles(3, 0.0, 1.0)
        .mapToObj(d -> String.format("%.3f", d))
        .forEach(s -> System.out.print(s + " "));
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 6. boxed() - 기본형 스트림 → 객체 스트림 변환
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 6] boxed() - IntStream → Stream<Integer>");

    // IntStream은 collect(Collectors.toList())를 직접 쓸 수 없다.
    // boxed()로 Stream<Integer>로 변환한 뒤 collect한다.
    java.util.List<Integer> intList = IntStream.rangeClosed(1, 5)
        .boxed()                                    // IntStream → Stream<Integer>
        .collect(java.util.stream.Collectors.toList());
    System.out.println("  boxed toList: " + intList); // [1, 2, 3, 4, 5]

    // mapToObj()로도 변환 가능하다. 변환 과정에서 원소를 가공할 수 있다.
    java.util.List<String> strList = IntStream.rangeClosed(1, 5)
        .mapToObj(i -> "item-" + i)                 // int → String
        .collect(java.util.stream.Collectors.toList());
    System.out.println("  mapToObj:     " + strList); // [item-1, item-2, ...]

    System.out.println();
    System.out.println("→ IntStream.range()는 for 루프를 선언형으로 대체한다. range()는 end 미포함, rangeClosed()는 포함.");
    System.out.println("→ IntStream은 sum(), average(), min(), max()를 추가로 제공한다.");
    System.out.println("→ 기본형 스트림을 List로 수집하려면 boxed()로 Stream<Integer>로 변환해야 한다.");
  }
}

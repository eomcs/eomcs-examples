package com.eomcs.advanced.stream.exam12;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

// 범용 집계 - reduce():
//
// reduce()는 스트림의 모든 요소를 하나의 값으로 합치는 최종 연산이다.
// count(), sum(), min(), max()가 reduce()의 특수한 형태이다.
//
// Stream<T>.reduce 형태:
//   형태 1: reduce(BinaryOperator<T>)          → Optional<T>  (초기값 없음)
//   형태 2: reduce(T identity, BinaryOperator<T>) → T          (초기값 있음)
//
// IntStream.reduce 형태:
//   형태 1: reduce(IntBinaryOperator)           → OptionalInt  (초기값 없음)
//   형태 2: reduce(int identity, IntBinaryOperator) → int      (초기값 있음)
//   → IntBinaryOperator는 (int, int) → int 이므로 박싱/언박싱 경고가 없다.
//
// identity(항등원):
//   - 연산 f에서 f(identity, x) == x를 만족하는 값이다.
//   - 덧셈의 항등원: 0  (0 + x == x)
//   - 곱셈의 항등원: 1  (1 * x == x)
//   - 최댓값의 항등원: Integer.MIN_VALUE
//

public class App {

  public static void main(String[] args) {

    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

    // ─────────────────────────────────────────────────────────────
    // 예제 1. reduce(BinaryOperator) - 초기값 없음 → Optional
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] reduce(BinaryOperator) - 초기값 없음");

    // 문자열 스트림에서 reduce: String은 null 안전 → 경고 없음
    List<String> words = Arrays.asList("Java", "Stream", "reduce", "example");

    Optional<String> joined = words.stream()
        .reduce((a, b) -> a + " " + b);
    System.out.println("  joined: " + joined.orElse("")); // Java Stream reduce example

    Optional<String> longest = words.stream()
        .reduce((a, b) -> a.length() >= b.length() ? a : b);
    System.out.println("  가장 긴 단어: " + longest.orElse("")); // example

    // 빈 스트림 → Optional.empty()
    Optional<String> emptyResult = List.<String>of().stream()
        .reduce((a, b) -> a + b);
    System.out.println("  빈 스트림: " + emptyResult); // Optional.empty

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. IntStream.reduce - 초기값 없음 → OptionalInt
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] IntStream.reduce(IntBinaryOperator) - 초기값 없음");

    // IntBinaryOperator는 (int, int) → int 이므로 박싱/언박싱 경고 없음
    OptionalInt sum = numbers.stream()
        .mapToInt(Integer::intValue)
        .reduce((a, b) -> a + b);
    System.out.println("  합계: " + sum.orElse(0)); // 15

    OptionalInt product = numbers.stream()
        .mapToInt(Integer::intValue)
        .reduce((a, b) -> a * b);
    System.out.println("  곱:   " + product.orElse(0)); // 120

    // 빈 스트림 → OptionalInt.empty
    OptionalInt emptySum = List.<Integer>of().stream()
        .mapToInt(Integer::intValue)
        .reduce((a, b) -> a + b);
    System.out.println("  빈 스트림 합계: " + emptySum); // OptionalInt.empty

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. IntStream.reduce(identity, IntBinaryOperator) - 초기값 있음 → int
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] IntStream.reduce(identity, IntBinaryOperator) - 초기값 있음");

    // 합계 (identity: 0)
    int sum2 = numbers.stream()
        .mapToInt(Integer::intValue)
        .reduce(0, (a, b) -> a + b);
    System.out.println("  합계(identity=0): " + sum2); // 15

    // 곱 (identity: 1)
    int product2 = numbers.stream()
        .mapToInt(Integer::intValue)
        .reduce(1, (a, b) -> a * b);
    System.out.println("  곱(identity=1):   " + product2); // 120

    // 최댓값 (identity: Integer.MIN_VALUE)
    int max = numbers.stream()
        .mapToInt(Integer::intValue)
        .reduce(Integer.MIN_VALUE, (a, b) -> a >= b ? a : b);
    System.out.println("  최댓값:           " + max); // 5

    // 빈 스트림 → identity 반환
    int emptySum2 = List.<Integer>of().stream()
        .mapToInt(Integer::intValue)
        .reduce(0, (a, b) -> a + b);
    System.out.println("  빈 스트림(identity=0): " + emptySum2); // 0

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. reduce로 count / sum / min / max 직접 구현
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] reduce로 count / sum / min / max 구현");

    // count — 각 요소마다 1을 더함
    int count = numbers.stream()
        .mapToInt(Integer::intValue)
        .reduce(0, (acc, n) -> acc + 1);
    System.out.println("  count: " + count); // 5

    // sum
    int reduceSum = numbers.stream()
        .mapToInt(Integer::intValue)
        .reduce(0, (a, b) -> a + b);
    System.out.println("  sum:   " + reduceSum); // 15

    // min
    OptionalInt reduceMin = numbers.stream()
        .mapToInt(Integer::intValue)
        .reduce((a, b) -> a <= b ? a : b);
    System.out.println("  min:   " + reduceMin.orElse(-1)); // 1

    // max
    OptionalInt reduceMax = numbers.stream()
        .mapToInt(Integer::intValue)
        .reduce((a, b) -> a >= b ? a : b);
    System.out.println("  max:   " + reduceMax.orElse(-1)); // 5

    System.out.println();
    System.out.println("→ reduce(BinaryOperator)는 초기값 없음 → 빈 스트림 시 Optional.empty() 반환.");
    System.out.println("→ reduce(identity, BinaryOperator)는 초기값 있음 → 빈 스트림 시 identity 반환.");
    System.out.println("→ IntStream.reduce(IntBinaryOperator)는 (int,int)→int 이므로 박싱 경고가 없다.");
    System.out.println("→ count/sum/min/max는 reduce의 특수한 형태이다.");
  }
}

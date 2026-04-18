package com.eomcs.advanced.stream.exam11;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// 단순 집계 - count() / min() / max():
//
// count()
//   - 스트림의 요소 수를 long으로 반환한다.
//   - filter()와 조합하면 조건을 만족하는 요소의 수를 센다.
//
// min(Comparator<T>) / max(Comparator<T>)
//   - Comparator 기준으로 최솟값 / 최댓값을 Optional<T>로 반환한다.
//   - 스트림이 비어 있으면 Optional.empty()를 반환한다.
//   - 기본형 스트림(IntStream 등)은 Comparator 없이 min() / max()를 호출한다.
//

public class App {

  public static void main(String[] args) {

    List<Integer> numbers = Arrays.asList(3, 7, 2, 9, 4, 6, 1, 8, 5, 10);
    List<String>  names   = Arrays.asList("Charlie", "Alice", "Eve", "Bob", "Dave");

    // ─────────────────────────────────────────────────────────────
    // 예제 1. count() - 요소 수
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] count() - 요소 수");

    long total = numbers.stream().count();
    System.out.println("  전체 요소 수:         " + total); // 10

    long evenCount = numbers.stream()
        .filter(n -> n % 2 == 0)
        .count();
    System.out.println("  짝수 개수:            " + evenCount); // 5

    long longNameCount = names.stream()
        .filter(name -> name.length() > 3)
        .count();
    System.out.println("  이름 길이 3 초과 개수: " + longNameCount); // 3 (Charlie, Alice, Dave)

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. min() / max() - 최솟값 / 최댓값
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] min() / max() - 최솟값 / 최댓값");

    // 정수 최솟값 / 최댓값
    Optional<Integer> min = numbers.stream()
        .min(Integer::compareTo);
    Optional<Integer> max = numbers.stream()
        .max(Integer::compareTo);
    System.out.println("  최솟값: " + min.orElse(-1)); // 1
    System.out.println("  최댓값: " + max.orElse(-1)); // 10

    // 문자열 길이 기준 최솟값 / 최댓값
    Optional<String> shortest = names.stream()
        .min((a, b) -> a.length() - b.length());
    Optional<String> longest = names.stream()
        .max((a, b) -> a.length() - b.length());
    System.out.println("  가장 짧은 이름: " + shortest.orElse("없음")); // Bob, Eve (셋 중 먼저)
    System.out.println("  가장 긴 이름:   " + longest.orElse("없음"));  // Charlie

    // Comparator.comparingInt 사용
    Optional<String> shortestByComparing = names.stream()
        .min(java.util.Comparator.comparingInt(String::length));
    System.out.println("  가장 짧은 이름(comparingInt): " + shortestByComparing.orElse("없음"));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. 빈 스트림에서 min / max
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] 빈 스트림에서 min / max → Optional.empty()");

    Optional<Integer> emptyMin = numbers.stream()
        .filter(n -> n > 100)   // 조건을 만족하는 요소 없음
        .min(Integer::compareTo);

    System.out.println("  결과: " + emptyMin);            // Optional.empty
    System.out.println("  isPresent: " + emptyMin.isPresent()); // false
    System.out.println("  orElse(-1): " + emptyMin.orElse(-1)); // -1

    System.out.println();
    System.out.println("→ count()는 long을 반환한다. filter()와 조합하면 조건 만족 개수를 센다.");
    System.out.println("→ min() / max()는 Optional<T>를 반환한다. 빈 스트림이면 Optional.empty()이다.");
    System.out.println("→ Comparator.comparingInt()로 특정 필드 기준 최솟값/최댓값을 구한다.");
  }
}

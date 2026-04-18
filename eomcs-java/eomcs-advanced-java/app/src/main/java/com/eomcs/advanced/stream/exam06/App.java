package com.eomcs.advanced.stream.exam06;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

// 정렬 - sorted():
//
// sorted()
//   - 스트림 요소를 자연 순서(natural order)로 정렬한다.
//   - 요소 타입이 Comparable을 구현해야 한다. (Integer, String 등 기본 타입은 이미 구현)
//   - 커스텀 객체는 Comparable을 구현하지 않으면 ClassCastException 발생.
//
// sorted(Comparator<T>)
//   - Comparator를 인자로 받아 지정한 순서로 정렬한다.
//   - 커스텀 객체나 역순 정렬에 사용한다.
//
// Comparator 생성 메서드:
//   Comparator.naturalOrder()       자연 순서 (오름차순)
//   Comparator.reverseOrder()       역순 (내림차순)
//   Comparator.comparing(keyExtractor)  특정 필드 기준 오름차순
//   comparator.reversed()           기존 Comparator 역순
//   comparator.thenComparing(...)   1차 정렬 후 2차 정렬
//
// 중간 연산:
//   - sorted()는 중간 연산이다. 최종 연산이 실행될 때 정렬이 수행된다.
//   - 정렬은 전체 요소를 버퍼에 모은 뒤 처리한다 (상태 있는 연산 = stateful).
//   - 무한 스트림에 sorted()를 적용하면 종료되지 않는다.
//

public class App {

  public static void main(String[] args) {

    List<Integer> numbers = Arrays.asList(5, 3, 8, 1, 9, 2, 7, 4, 6, 10);
    List<String>  names   = Arrays.asList("Charlie", "Alice", "Eve", "Bob", "Dave");

    // ─────────────────────────────────────────────────────────────
    // 예제 1. sorted() - 자연 순서 정렬
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] sorted() - 자연 순서 (오름차순)");

    // 정수 오름차순
    System.out.print("  정수 오름차순: ");
    numbers.stream()
        .sorted()
        .forEach(n -> System.out.print(n + " ")); // 1 2 3 4 5 6 7 8 9 10
    System.out.println();

    // 문자열 알파벳 순
    System.out.print("  문자열 알파벳 순: ");
    names.stream()
        .sorted()
        .forEach(s -> System.out.print(s + " ")); // Alice Bob Charlie Dave Eve
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. sorted(Comparator) - 역순 정렬
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] sorted(Comparator) - 역순 정렬");

    // 정수 내림차순
    System.out.print("  정수 내림차순: ");
    numbers.stream()
        .sorted(Comparator.reverseOrder())
        .forEach(n -> System.out.print(n + " ")); // 10 9 8 7 6 5 4 3 2 1
    System.out.println();

    // 문자열 역순
    System.out.print("  문자열 역순:   ");
    names.stream()
        .sorted(Comparator.reverseOrder())
        .forEach(s -> System.out.print(s + " ")); // Eve Dave Charlie Bob Alice
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. sorted() + filter/limit - 정렬 후 슬라이싱
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] sorted() + filter/limit - 정렬 후 상위 N개");

    // 5보다 큰 수 중 가장 작은 3개
    System.out.print("  5 초과 중 상위 3개(오름차순): ");
    numbers.stream()
        .filter(n -> n > 5)    // 8 9 7 6 10
        .sorted()              // 6 7 8 9 10
        .limit(3)              // 6 7 8
        .forEach(n -> System.out.print(n + " "));
    System.out.println();

    // 알파벳 마지막 2개 이름
    System.out.print("  알파벳 뒤에서 2개:            ");
    names.stream()
        .sorted(Comparator.reverseOrder()) // Eve Dave Charlie Bob Alice
        .limit(2)                          // Eve Dave
        .forEach(s -> System.out.print(s + " "));
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. sorted() + distinct - 중복 제거 후 정렬
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] distinct() + sorted() - 중복 제거 후 정렬");

    List<Integer> withDuplicates = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5);

    System.out.print("  원본:           ");
    withDuplicates.forEach(n -> System.out.print(n + " "));
    System.out.println();

    System.out.print("  중복 제거 + 정렬: ");
    withDuplicates.stream()
        .distinct() // 3 1 4 5 9 2 6
        .sorted()   // 1 2 3 4 5 6 9
        .forEach(n -> System.out.print(n + " "));
    System.out.println();

    System.out.println();
    System.out.println("→ sorted()는 자연 순서, sorted(Comparator)는 지정 순서로 정렬한다.");
    System.out.println("→ 정렬은 전체 요소를 버퍼에 모아야 하므로 무한 스트림에는 사용할 수 없다.");
  }
}

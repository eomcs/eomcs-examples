package com.eomcs.advanced.stream.exam07;

import java.util.Arrays;
import java.util.List;

// 엿보기 - peek():
//
// peek(Consumer<T>)
//   - 스트림의 각 요소를 소비하지 않고 중간에 들여다본다.
//   - 요소를 변경하거나 제거하지 않는다. 원래 요소를 그대로 다음 연산으로 전달한다.
//   - 주로 파이프라인 디버깅에 사용한다. (중간 단계의 요소 확인)
//   - 중간 연산이다.
//
// peek()의 특성:
//   - 지연 실행(lazy): 최종 연산이 없으면 peek()의 Consumer는 실행되지 않는다.
//   - 부작용(side effect) 전용: 요소를 변환하거나 필터링하는 데 사용하지 않는다.
//   - forEach()와의 차이: forEach()는 최종 연산(스트림 종료), peek()은 중간 연산(계속 진행).
//
// peek() 사용 목적:
//   1. 파이프라인의 중간 단계 요소 로깅/출력 (디버깅)
//   2. 중간 단계에서 외부 상태 기록 (카운팅, 로그 저장 등)
//   3. 파이프라인 실행 순서 이해
//

public class App {

  public static void main(String[] args) {

    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    // ─────────────────────────────────────────────────────────────
    // 예제 1. peek() 기본 - 중간 단계 확인
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] peek() - 중간 단계 요소 확인");

    List<Integer> result = numbers.stream()
        .filter(n -> n % 2 == 0)                      // 짝수 필터
        .peek(n -> System.out.print("  filter 후: " + n + "\n")) // filter 통과 요소 확인
        .map(n -> n * n)                               // 제곱
        .peek(n -> System.out.print("  map 후:    " + n + "\n")) // map 변환 후 확인
        .toList();

    System.out.println("  결과: " + result); // [4, 16, 36, 64, 100]

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. peek() 지연 실행 - 최종 연산 없으면 실행되지 않음
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] peek() 지연 실행 - 최종 연산이 없으면 Consumer는 실행되지 않는다");

    // 최종 연산 없음 → peek()의 람다 실행 안 됨
    System.out.println("  최종 연산 없이 스트림 구성 (실행 없음):");
    numbers.stream()
        .filter(n -> n > 5)
        .peek(n -> System.out.println("  이 줄은 출력되지 않음: " + n));
    // 아무것도 출력되지 않는다.
    System.out.println("  (peek의 Consumer가 실행되지 않음)");

    System.out.println();

    // 최종 연산 추가 → peek() 실행됨
    System.out.println("  최종 연산(count) 추가 후:");
    long count = numbers.stream()
        .filter(n -> n > 5)
        .peek(n -> System.out.println("  처리됨: " + n))
        .count();
    System.out.println("  count: " + count); // 5

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. peek() vs forEach() - 역할 비교
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] peek() vs forEach() - 역할 비교");

    // forEach: 최종 연산, 스트림을 종료한다 → 이후 연산 불가
    System.out.println("  forEach(최종 연산):");
    numbers.stream()
        .filter(n -> n <= 3)
        .forEach(n -> System.out.print("  " + n)); // 1 2 3 (스트림 종료)
    System.out.println();

    // peek: 중간 연산, 스트림을 유지한다 → 이후 연산 가능
    System.out.println("  peek(중간 연산) → 이후 map, toList 가능:");
    numbers.stream()
        .filter(n -> n <= 3)
        .peek(n -> System.out.print("  peek:" + n + " "))  // 중간에 들여다봄
        .map(n -> n * 10)
        .toList(); // [10, 20, 30]
    System.out.println();

    System.out.println();
    System.out.println("→ peek()은 요소를 소비하지 않는 중간 연산으로 주로 디버깅에 사용한다.");
    System.out.println("→ 최종 연산이 없으면 peek()의 Consumer는 실행되지 않는다.");
    System.out.println("→ forEach()는 스트림을 종료하는 최종 연산, peek()은 계속 이어지는 중간 연산이다.");
  }
}

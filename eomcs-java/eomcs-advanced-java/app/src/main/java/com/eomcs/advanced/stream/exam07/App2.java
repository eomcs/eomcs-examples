package com.eomcs.advanced.stream.exam07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 엿보기 - 파이프라인 실행 순서 이해:
//
// 스트림 파이프라인의 실행 순서:
//   - 스트림은 요소를 하나씩 파이프라인 전체를 통과시킨다.
//   - "소스의 모든 요소를 filter → 그 결과를 모두 map → ..." 순서가 아니다.
//   - "요소1을 filter → map → peek → collect, 요소2를 filter → ..." 순서다.
//   - 이를 수직 처리(vertical processing) 또는 깊이 우선(depth-first)이라 한다.
//
// peek()으로 실행 순서를 직접 확인할 수 있다.
//

public class App2 {

  public static void main(String[] args) {

    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

    // ─────────────────────────────────────────────────────────────
    // 예제 1. 파이프라인 실행 순서 추적
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] 파이프라인 실행 순서 - 수직 처리(depth-first)");
    System.out.println("  예상: 요소 하나씩 전체 파이프라인을 통과");
    System.out.println();

    numbers.stream()
        .peek(n  -> System.out.println("  [소스]   " + n))
        .filter(n -> {
          boolean pass = n % 2 == 0;
          System.out.println("  [filter] " + n + " → " + (pass ? "통과" : "제거"));
          return pass;
        })
        .peek(n  -> System.out.println("  [filter 후] " + n))
        .map(n   -> {
          int mapped = n * 10;
          System.out.println("  [map]    " + n + " → " + mapped);
          return mapped;
        })
        .peek(n  -> System.out.println("  [map 후] " + n))
        .forEach(n -> System.out.println("  [forEach] " + n));

    // 출력을 보면 1→filter→제거, 2→filter→통과→map→forEach, 3→filter→제거, ...
    // 전체 요소를 단계별로 처리하는 것이 아니라 요소 하나씩 파이프라인 전체를 통과한다.

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. limit()에서의 단락 평가 확인
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] limit()에서 단락 평가 - peek()으로 처리 중단 확인");

    numbers.stream()
        .peek(n -> System.out.println("  peek: " + n))
        .filter(n -> n % 2 == 0)     // 2, 4 통과
        .limit(1)                     // 첫 번째 짝수(2) 하나만
        .forEach(n -> System.out.println("  forEach: " + n));

    // peek에서 1, 2까지만 출력되고 종료됨 (3, 4, 5는 처리되지 않음)
    System.out.println("  → limit(1)이 충족되면 이후 요소(3,4,5)는 처리되지 않는다");

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. peek()을 이용한 로깅/카운팅
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] peek()을 이용한 중간 카운팅");

    int[] filterCount = {0};
    int[] mapCount    = {0};

    List<Integer> result = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream()
        .filter(n -> n % 2 == 0)
        .peek(n -> filterCount[0]++)  // filter 통과 횟수 카운팅
        .map(n -> n * n)
        .peek(n -> mapCount[0]++)     // map 변환 횟수 카운팅
        .toList();

    System.out.println("  결과:           " + result);
    System.out.println("  filter 통과 수: " + filterCount[0]); // 5
    System.out.println("  map 변환 수:    " + mapCount[0]);    // 5

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. peek()으로 외부 컬렉션에 수집 (부작용 활용)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] peek()으로 중간 단계 요소를 외부 리스트에 수집");

    List<Integer> beforeMap = new ArrayList<>();
    List<Integer> afterMap  = new ArrayList<>();

    Arrays.asList(1, 2, 3, 4, 5).stream()
        .filter(n -> n % 2 != 0)          // 홀수만
        .peek(beforeMap::add)             // map 전 요소 저장
        .map(n -> n * n)                  // 제곱
        .peek(afterMap::add)              // map 후 요소 저장
        .toList();

    System.out.println("  map 전 (홀수):  " + beforeMap); // [1, 3, 5]
    System.out.println("  map 후 (제곱):  " + afterMap);  // [1, 9, 25]

    System.out.println();
    System.out.println("→ 스트림은 요소 하나씩 전체 파이프라인을 통과한다(수직 처리).");
    System.out.println("→ peek()으로 각 단계의 실행 순서와 처리 요소를 직접 확인할 수 있다.");
    System.out.println("→ limit() 등 단락 평가 연산은 조건 충족 시 이후 요소를 처리하지 않는다.");
  }
}

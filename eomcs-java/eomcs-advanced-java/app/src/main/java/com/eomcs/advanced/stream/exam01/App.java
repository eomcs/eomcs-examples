package com.eomcs.advanced.stream.exam01;

import java.util.Arrays;
import java.util.List;

// 명령형(Imperative) vs 선언형(Declarative) - 필터링과 출력:
//
// 명령형 프로그래밍:
//   "어떻게(How)" 처리할지를 단계별로 기술한다.
//   for 루프, if 조건, 임시 변수 등 제어 흐름을 직접 작성한다.
//
// 선언형 프로그래밍(스트림):
//   "무엇을(What)" 원하는지를 기술한다.
//   필터링·변환·집계 등의 연산을 메서드 체인으로 연결한다.
//   제어 흐름은 스트림 내부에서 처리하므로 코드가 짧고 의도가 명확하다.
//
// Stream 파이프라인 구조:
//   소스(Source)  →  중간 연산(Intermediate)  →  최종 연산(Terminal)
//   List/배열 등      filter, map, sorted ...      forEach, collect ...
//
// 핵심 특징:
//   - 중간 연산은 지연(lazy) 실행된다. 최종 연산이 호출될 때 한꺼번에 처리된다.
//   - 스트림은 원본 컬렉션을 변경하지 않는다.
//   - 스트림은 한 번 소비하면 재사용할 수 없다.
//

public class App {

  public static void main(String[] args) {

    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    // ─────────────────────────────────────────────────────────────
    // 예제 1. 짝수만 출력
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] 짝수만 출력");

    // 명령형: 루프와 조건문으로 직접 제어
    System.out.print("  명령형: ");
    for (int n : numbers) {
      if (n % 2 == 0) {       // 짝수 조건을 개발자가 직접 if로 작성
        System.out.print(n + " ");
      }
    }
    System.out.println();

    // 선언형: "짝수인 것을 출력한다"는 의도가 코드에 그대로 드러난다.
    System.out.print("  선언형: ");
    numbers.stream()
        .filter(n -> n % 2 == 0) // 중간 연산: 짝수만 통과
        .forEach(n -> System.out.print(n + " ")); // 최종 연산: 출력
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. 5보다 큰 수만 출력
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] 5보다 큰 수만 출력");

    // 명령형
    System.out.print("  명령형: ");
    for (int n : numbers) {
      if (n > 5) {
        System.out.print(n + " ");
      }
    }
    System.out.println();

    // 선언형
    System.out.print("  선언형: ");
    numbers.stream()
        .filter(n -> n > 5)
        .forEach(n -> System.out.print(n + " "));
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. 짝수이면서 5보다 큰 수만 출력 (복합 조건)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] 짝수이면서 5보다 큰 수만 출력");

    // 명령형: 조건이 늘어날수록 if 중첩이 깊어진다.
    System.out.print("  명령형: ");
    for (int n : numbers) {
      if (n % 2 == 0) {
        if (n > 5) {
          System.out.print(n + " ");
        }
      }
    }
    System.out.println();

    // 선언형: filter()를 추가로 연결하면 된다. 중첩 없이 읽기 쉽다.
    System.out.print("  선언형: ");
    numbers.stream()
        .filter(n -> n % 2 == 0) // 짝수 필터
        .filter(n -> n > 5)      // 5 초과 필터 (filter를 이어 붙인다)
        .forEach(n -> System.out.print(n + " "));
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. 문자열 리스트에서 특정 조건으로 필터링
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] 길이가 4 이상인 과일 이름만 출력");

    List<String> fruits = Arrays.asList("사과", "바나나", "딸기", "수박", "포도", "키위", "블루베리");

    // 명령형
    System.out.print("  명령형: ");
    for (String fruit : fruits) {
      if (fruit.length() >= 4) {
        System.out.print(fruit + " ");
      }
    }
    System.out.println();

    // 선언형: 한국어 문자열도 길이 기준으로 쉽게 필터링할 수 있다.
    System.out.print("  선언형: ");
    fruits.stream()
        .filter(fruit -> fruit.length() >= 4)
        .forEach(fruit -> System.out.print(fruit + " "));
    System.out.println();

    System.out.println();
    System.out.println("→ 선언형 코드는 '무엇을 원하는지'가 코드에 직접 드러나 읽기 쉽다.");
    System.out.println("→ 조건이 늘어도 filter()를 추가하면 되므로 명령형보다 유지보수가 쉽다.");
  }
}

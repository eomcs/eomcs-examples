package com.eomcs.advanced.stream.exam10;

import java.util.Arrays;
import java.util.List;

// 반복 - forEach():
//
// forEach(Consumer<T>)
//   - 스트림의 각 요소에 대해 Consumer를 실행하는 최종 연산이다.
//   - void를 반환하며 스트림을 종료한다.
//   - 순차 스트림에서는 소스의 순서대로 처리한다.
//   - 병렬 스트림에서는 순서가 보장되지 않는다.
//
// forEach vs 명령형 for-each:
//   - 결과는 같지만 forEach는 선언형 스타일이다.
//   - forEach 내부에서 외부 변수를 수정하는 것은 부작용(side effect)이다.
//     스트림은 부작용 없는 함수형 스타일을 권장한다.
//   - 결과를 모으려면 forEach 대신 collect()를 사용한다.
//
// 메서드 레퍼런스와 forEach:
//   forEach(System.out::println)  → 각 요소를 출력하는 가장 흔한 패턴
//

public class App {

  public static void main(String[] args) {

    List<String> names   = Arrays.asList("Alice", "Bob", "Charlie", "Dave", "Eve");
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

    // ─────────────────────────────────────────────────────────────
    // 예제 1. forEach 기본 - 명령형 vs 선언형
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] forEach 기본 - 명령형 vs 선언형");

    // 명령형
    System.out.println("  명령형:");
    for (String name : names) {
      System.out.println("    " + name);
    }

    // 선언형 - 람다
    System.out.println("  선언형(람다):");
    names.stream()
        .forEach(name -> System.out.println("    " + name));

    // 선언형 - 메서드 레퍼런스
    System.out.println("  선언형(메서드 레퍼런스):");
    names.stream()
        .forEach(System.out::println);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. forEach + 중간 연산 조합
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] forEach + filter + map");

    // 짝수만 제곱하여 출력
    numbers.stream()
        .filter(n -> n % 2 == 0)
        .map(n -> n * n)
        .forEach(n -> System.out.println("  " + n)); // 4 16

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. forEach와 부작용 - 안티패턴 vs 올바른 패턴
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] forEach와 부작용 - 안티패턴 vs 올바른 패턴");

    // 안티패턴: forEach 내부에서 외부 리스트에 수집
    // → 병렬 스트림에서 스레드 안전 문제 발생 가능
    java.util.List<Integer> result = new java.util.ArrayList<>();
    numbers.stream()
        .filter(n -> n % 2 == 0)
        .forEach(result::add); // 부작용 - 외부 상태 변경
    System.out.println("  [안티패턴] forEach로 수집: " + result);

    // 올바른 패턴: collect()로 수집
    List<Integer> collected = numbers.stream()
        .filter(n -> n % 2 == 0)
        .toList(); // 또는 .collect(Collectors.toList())
    System.out.println("  [올바른 패턴] collect로 수집: " + collected);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. forEach vs for-each 루프 - 스트림 없이도 가능
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] List.forEach() - 스트림 없이 직접 호출");

    // Collection.forEach()는 스트림을 만들지 않고 바로 호출 가능
    names.forEach(name -> System.out.println("  " + name));

    System.out.println();
    System.out.println("→ forEach()는 스트림을 종료하는 최종 연산이다.");
    System.out.println("→ 결과를 수집하려면 forEach 대신 collect()를 사용한다.");
    System.out.println("→ List.forEach()는 스트림 없이 컬렉션에서 바로 호출할 수 있다.");
  }
}

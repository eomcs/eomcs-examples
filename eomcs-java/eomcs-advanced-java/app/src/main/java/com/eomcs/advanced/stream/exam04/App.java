package com.eomcs.advanced.stream.exam04;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

// 필터링 - filter()와 Predicate 조합:
//
// filter(Predicate<T>)
//   - 조건을 만족하는 요소만 통과시키는 중간 연산이다.
//   - 조건을 만족하지 않는 요소는 버린다.
//   - 지연(lazy) 실행된다. 최종 연산이 호출될 때 실행된다.
//
// Predicate<T>
//   - boolean 값을 반환하는 함수형 인터페이스다.
//   - boolean test(T t) 메서드 하나를 가진다.
//   - 람다: n -> n > 5
//   - 메서드 레퍼런스: String::isBlank
//
// Predicate 조합 메서드:
//   and(Predicate)   - 두 조건 모두 만족해야 true. 단락 평가: 앞이 false면 뒤는 실행 안 함.
//   or(Predicate)    - 두 조건 중 하나만 만족해도 true. 단락 평가: 앞이 true면 뒤는 실행 안 함.
//   negate()         - 조건을 반전시킨다. true ↔ false
//   not(Predicate)   - negate()와 동일. 정적 팩토리 메서드. (Java 11+)
//
// 조합 방법 비교:
//   filter(a).filter(b) == filter(a.and(b))  → 같은 결과이나 and()가 명시적으로 의도를 드러냄
//   filter(a.or(b))     → 두 조건 중 하나라도 만족하는 요소를 통과시킨다
//

public class App {

  public static void main(String[] args) {

    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    List<String>  names   = Arrays.asList("Alice", "Bob", "Charlie", "Dave", "Eve", "Frank");

    // ─────────────────────────────────────────────────────────────
    // 예제 1. 기본 filter - 단일 조건
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] 단일 조건 filter");

    System.out.print("  짝수:       ");
    numbers.stream()
        .filter(n -> n % 2 == 0)
        .forEach(n -> System.out.print(n + " ")); // 2 4 6 8 10
    System.out.println();

    System.out.print("  5 초과:     ");
    numbers.stream()
        .filter(n -> n > 5)
        .forEach(n -> System.out.print(n + " ")); // 6 7 8 9 10
    System.out.println();

    System.out.print("  길이 4 이상: ");
    names.stream()
        .filter(name -> name.length() >= 4)
        .forEach(name -> System.out.print(name + " ")); // Alice Charlie Dave Frank
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. Predicate.and() - 두 조건 모두 만족
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] Predicate.and() - AND 조합");

    Predicate<Integer> isEven    = n -> n % 2 == 0; // 짝수
    Predicate<Integer> isGt5     = n -> n > 5;      // 5 초과

    // 방법 1: filter를 두 번 체인 (가장 흔한 방식)
    System.out.print("  filter 체인:   ");
    numbers.stream()
        .filter(n -> n % 2 == 0)
        .filter(n -> n > 5)
        .forEach(n -> System.out.print(n + " ")); // 6 8 10
    System.out.println();

    // 방법 2: Predicate.and()로 명시적으로 결합
    // filter 체인과 동일한 결과이지만, Predicate를 변수로 관리할 때 유용하다.
    System.out.print("  Predicate.and: ");
    numbers.stream()
        .filter(isEven.and(isGt5)) // 짝수 AND 5 초과
        .forEach(n -> System.out.print(n + " ")); // 6 8 10
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. Predicate.or() - 두 조건 중 하나라도 만족
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] Predicate.or() - OR 조합");

    Predicate<Integer> isLt3  = n -> n < 3;  // 3 미만
    Predicate<Integer> isGt8  = n -> n > 8;  // 8 초과

    // "3 미만이거나 8 초과" → 양 끝 값들
    System.out.print("  3 미만 OR 8 초과: ");
    numbers.stream()
        .filter(isLt3.or(isGt8))
        .forEach(n -> System.out.print(n + " ")); // 1 2 9 10
    System.out.println();

    // 문자열 조건 OR 조합
    Predicate<String> startsWithA = name -> name.startsWith("A");
    Predicate<String> startsWithE = name -> name.startsWith("E");

    System.out.print("  A 또는 E로 시작: ");
    names.stream()
        .filter(startsWithA.or(startsWithE))
        .forEach(name -> System.out.print(name + " ")); // Alice Eve
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. Predicate.negate() / Predicate.not() - 조건 반전
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] Predicate.negate() / Predicate.not() - 조건 반전");

    Predicate<String> isBlank = String::isBlank;

    List<String> sentences = Arrays.asList("Java", "", "  ", "Stream", "  ", "API");

    // negate(): 기존 Predicate 인스턴스의 결과를 반전
    System.out.print("  negate (비어있지 않음): ");
    sentences.stream()
        .filter(isBlank.negate()) // 빈 문자열이 아닌 것
        .forEach(s -> System.out.print("'" + s + "' "));
    System.out.println();

    // Predicate.not(): Java 11+, 메서드 레퍼런스와 함께 더 간결하게 표현
    System.out.print("  not (비어있지 않음):    ");
    sentences.stream()
        .filter(Predicate.not(String::isBlank)) // 메서드 레퍼런스에 직접 적용
        .forEach(s -> System.out.print("'" + s + "' "));
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. Predicate 조합 - and / or / negate 복합
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] Predicate 복합 조합");

    // "(짝수 OR 3의 배수) AND 10 미만" 조건
    Predicate<Integer> isMultipleOf3 = n -> n % 3 == 0;
    Predicate<Integer> isLt10       = n -> n < 10;

    List<Integer> extended = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

    System.out.print("  (짝수 OR 3의 배수) AND 10 미만: ");
    extended.stream()
        .filter(isEven.or(isMultipleOf3).and(isLt10))
        .forEach(n -> System.out.print(n + " ")); // 2 3 4 6 8 9
    System.out.println();
    // 주의: and()가 or()보다 먼저 평가되지 않는다. 왼쪽에서 오른쪽으로 체인된다.
    // isEven.or(isMultipleOf3).and(isLt10)
    //   = (isEven OR isMultipleOf3) AND isLt10

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 6. Predicate를 파라미터로 전달 - 유연한 필터링
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 6] Predicate를 파라미터로 전달 - 재사용 가능한 필터");

    // Predicate를 외부에서 주입하면 동일한 메서드로 다양한 조건을 처리할 수 있다.
    System.out.println("  짝수 합계:    " + sumIf(numbers, n -> n % 2 == 0));    // 30
    System.out.println("  5 초과 합계:  " + sumIf(numbers, n -> n > 5));          // 40
    System.out.println("  3의 배수 합계: " + sumIf(numbers, n -> n % 3 == 0));   // 18

    System.out.println();
    System.out.println("→ filter()는 지연 실행된다. 최종 연산이 없으면 아무것도 처리되지 않는다.");
    System.out.println("→ filter 체인과 Predicate.and()는 같은 결과를 낸다. 조건을 변수로 관리할 때 and()가 유용하다.");
    System.out.println("→ Predicate.not()은 메서드 레퍼런스에 직접 negate를 적용할 수 있어 더 간결하다.");
  }

  // Predicate를 파라미터로 받아 조건을 만족하는 요소의 합계를 반환한다.
  static int sumIf(List<Integer> list, Predicate<Integer> condition) {
    return list.stream()
        .filter(condition)
        .mapToInt(Integer::intValue)
        .sum();
  }
}

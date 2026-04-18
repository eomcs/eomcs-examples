package com.eomcs.advanced.stream.exam09;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// 검색 - findFirst / findAny:
//
// findFirst()
//   - 스트림에서 첫 번째 요소를 Optional<T>로 반환한다.
//   - 스트림이 비어 있으면 Optional.empty()를 반환한다.
//   - 순차 스트림에서는 항상 스트림의 첫 번째 요소를 반환한다.
//   - 병렬 스트림에서도 순서를 보장하므로 오버헤드가 있다.
//
// findAny()
//   - 스트림에서 임의의 요소를 Optional<T>로 반환한다.
//   - 어느 요소가 반환될지는 보장되지 않는다.
//   - 순차 스트림에서는 findFirst()와 같은 결과를 내는 경우가 많다.
//   - 병렬 스트림에서 순서를 신경 쓰지 않아도 될 때 성능이 더 좋다.
//
// 반환 타입 Optional<T>:
//   - 결과가 없을 수 있음을 타입으로 표현한다.
//   - null 반환 없이 안전하게 결과를 처리할 수 있다.
//   - orElse(), orElseGet(), orElseThrow(), ifPresent(), map() 등으로 처리한다.
//
// filter + findFirst 패턴:
//   - 조건을 만족하는 첫 번째 요소를 Optional로 반환하는 가장 흔한 조합이다.
//   - 명령형의 "for 루프 + null 반환" 패턴을 대체한다.
//

public class App2 {

  public static void main(String[] args) {

    List<Integer> numbers = Arrays.asList(3, 7, 2, 9, 4, 6, 1, 8, 5, 10);
    List<String> names = Arrays.asList("Charlie", "Alice", "Eve", "Bob", "Dave");

    // ─────────────────────────────────────────────────────────────
    // 예제 1. findFirst - 첫 번째 요소 반환
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] findFirst - 첫 번째 요소");

    // 명령형: null 반환 → 호출자가 null 체크 강제
    Integer firstImperative = null;
    for (int n : numbers) {
      if (n > 5) {
        firstImperative = n;
        break;
      }
    }
    System.out.println("  명령형 (5 초과 첫 요소): " + (firstImperative != null ? firstImperative : "없음"));

    // 선언형: Optional로 반환 → null 체크 없이 안전하게 처리
    Optional<Integer> firstGt5 =
        numbers.stream().filter(n -> n > 5).findFirst(); // 조건 만족 첫 번째 요소 → Optional[7]
    System.out.println("  findFirst(n > 5): " + firstGt5.orElse(-1)); // 7

    // 조건을 만족하는 요소가 없는 경우
    Optional<Integer> firstGt100 =
        numbers.stream().filter(n -> n > 100).findFirst(); // 없으면 Optional.empty()
    System.out.println("  findFirst(n > 100): " + firstGt100.orElse(-1)); // -1 (기본값)

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. findFirst - 정렬 후 첫 번째 (최솟값 패턴)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] sorted + findFirst - 정렬 후 첫 번째");

    // 가장 작은 짝수 찾기
    Optional<Integer> minEven =
        numbers.stream()
            .filter(n -> n % 2 == 0) // 2 4 6 8 10
            .sorted() // 2 4 6 8 10 (오름차순)
            .findFirst(); // Optional[2]
    System.out.println("  가장 작은 짝수: " + minEven.orElse(-1)); // 2

    // 알파벳 순으로 첫 번째 이름
    Optional<String> firstName =
        names.stream()
            .sorted() // Alice Bob Charlie Dave Eve
            .findFirst(); // Optional[Alice]
    System.out.println("  알파벳 첫 이름: " + firstName.orElse("없음")); // Alice

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. findAny - 임의 요소 반환
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] findAny - 임의 요소 (순차 스트림)");

    // 순차 스트림에서는 findFirst와 동일한 결과를 내는 경우가 많다.
    Optional<Integer> anyGt5 =
        numbers.stream().filter(n -> n > 5).findAny(); // 순차: 첫 번째 조건 만족 요소를 반환 (보통 findFirst와 같음)
    System.out.println("  findAny(순차, n>5): " + anyGt5.orElse(-1)); // 7 (보통)

    // 병렬 스트림에서는 순서가 보장되지 않는다.
    Optional<Integer> anyGt5Parallel =
        numbers.parallelStream()
            .filter(n -> n > 5)
            .findAny(); // 병렬: 어느 스레드가 먼저 찾느냐에 따라 다른 값이 반환될 수 있다.
    System.out.println("  findAny(병렬, n>5): " + anyGt5Parallel.orElse(-1) + " (순서 미보장)");

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. findFirst vs findAny - 병렬 스트림에서 차이
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] findFirst vs findAny - 병렬 스트림 비교");

    List<Integer> ordered = Arrays.asList(1, 8, 3, 4, 5, 6, 7, 2, 9, 10);

    // findFirst: 병렬에서도 항상 첫 번째 조건 만족 요소 보장 (오버헤드 있음)
    Optional<Integer> first =
        ordered.parallelStream().filter(n -> n % 2 == 0).findFirst(); // 항상 2 (첫 번째 짝수)
    System.out.println("  병렬 findFirst(짝수): " + first.orElse(-1)); // 2 (항상)

    // findAny: 병렬에서 어떤 짝수든 반환 가능 (더 빠를 수 있음)
    Optional<Integer> any =
        ordered.parallelStream().filter(n -> n % 2 == 0).findAny(); // 2, 4, 6, 8, 10 중 하나 (비결정적)
    System.out.println("  병렬 findAny(짝수):  " + any.orElse(-1) + " (비결정적)");

    System.out.println("  → 순서가 중요하면 findFirst, 성능이 중요하면 findAny 사용");

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. Optional 처리 패턴 비교
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] Optional 처리 패턴 비교");

    Optional<String> found =
        names.stream().filter(name -> name.startsWith("C")).findFirst(); // Optional[Charlie]

    // orElse: 기본값 반환
    System.out.println("  orElse:       " + found.orElse("없음")); // Charlie

    // orElseGet: 값이 없을 때만 Supplier 실행
    System.out.println("  orElseGet:    " + found.orElseGet(() -> "기본 이름")); // Charlie

    // orElseThrow: 없으면 예외 발생
    System.out.println("  orElseThrow:  " + found.orElseThrow()); // Charlie

    // ifPresent: 있을 때만 실행
    found.ifPresent(name -> System.out.println("  ifPresent:    " + name)); // Charlie

    // map: 있을 때 변환
    Optional<Integer> length = found.map(String::length);
    System.out.println("  map(length):  " + length.orElse(0)); // 7

    // ifPresentOrElse: 있을 때와 없을 때 각각 처리 (Java 9+)
    found.ifPresentOrElse(
        name -> System.out.println("  있음: " + name), () -> System.out.println("  없음"));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 6. 빈 스트림에서 findFirst / findAny
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 6] 빈 스트림에서 findFirst / findAny");

    Optional<Integer> emptyFirst = List.<Integer>of().stream().findFirst();
    Optional<Integer> emptyAny = List.<Integer>of().stream().findAny();

    System.out.println("  빈 스트림 findFirst: " + emptyFirst); // Optional.empty
    System.out.println("  빈 스트림 findAny:   " + emptyAny); // Optional.empty

    // isPresent() / isEmpty()로 확인
    System.out.println("  isPresent: " + emptyFirst.isPresent()); // false
    System.out.println("  isEmpty:   " + emptyFirst.isEmpty()); // true (Java 11+)

    System.out.println();
    System.out.println("→ findFirst()는 순서를 보장하며 항상 첫 번째 요소를 반환한다.");
    System.out.println("→ findAny()는 병렬 스트림에서 순서를 신경 쓰지 않아 더 빠를 수 있다.");
    System.out.println("→ 두 메서드 모두 Optional을 반환해 null 없이 안전하게 결과를 처리할 수 있다.");
  }
}

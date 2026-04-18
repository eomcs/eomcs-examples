package com.eomcs.advanced.stream.exam12;

import java.util.Arrays;
import java.util.List;

// 범용 집계 - reduce() 3인자 / 실전 패턴:
//
// 형태 3: reduce(U identity, BiFunction<U,T,U> accumulator, BinaryOperator<U> combiner) → U
//   - 결과 타입(U)이 요소 타입(T)과 다를 때 사용한다.
//   - accumulator: 누적값(U)에 요소(T)를 더해 새 누적값(U)을 만든다.
//   - combiner:    병렬 스트림에서 부분 결과(U)를 합칠 때 사용한다.
//                  순차 스트림에서는 호출되지 않는다.
//
// 실전에서 3인자 reduce 대신 collect를 주로 사용하는 이유:
//   - reduce는 불변(immutable) 결과를 만들 때 적합하다.
//   - 가변 컨테이너(List, Map 등)에 누적할 때는 collect()가 더 적합하다.
//

public class App2 {

  public static void main(String[] args) {

    List<String> words = Arrays.asList("apple", "banana", "cherry", "date", "elderberry");

    // ─────────────────────────────────────────────────────────────
    // 예제 1. reduce 3인자 - 결과 타입이 다른 경우
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] reduce(identity, accumulator, combiner) - 결과 타입이 다름");

    // 단어 리스트에서 총 문자 수 합산 (String → int)
    // T = String, U = int
    int totalLength = words.stream()
        .reduce(
            0,                             // identity (U: int)
            (acc, word) -> acc + word.length(), // accumulator: (int, String) → int
            (a, b) -> a + b                // combiner: (int, int) → int (병렬 스트림용)
        );
    System.out.println("  총 문자 수: " + totalLength); // 5+6+6+4+10 = 31

    // 단어 길이가 5 이상인 단어의 총 문자 수
    int filteredLength = words.stream()
        .filter(w -> w.length() >= 5)
        .reduce(
            0,
            (acc, word) -> acc + word.length(),
            (a, b) -> a + b
        );
    System.out.println("  길이 5 이상 단어의 총 문자 수: " + filteredLength); // apple+banana+cherry+elderberry

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. 실전 패턴 - 문자열 연결
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] 실전 패턴 - 문자열 연결");

    List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Dave");

    // reduce로 쉼표 구분 문자열 연결
    String joined = names.stream()
        .reduce("", (acc, name) -> acc.isEmpty() ? name : acc + ", " + name);
    System.out.println("  joined: " + joined); // Alice, Bob, Charlie, Dave

    // 실전에서는 Collectors.joining()이 더 간결
    String joinedByCollector = names.stream()
        .collect(java.util.stream.Collectors.joining(", "));
    System.out.println("  joining: " + joinedByCollector); // Alice, Bob, Charlie, Dave

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. reduce 실행 과정 시각화
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] reduce 실행 과정 시각화");

    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

    System.out.println("  reduce(0, (acc, n) -> acc + n) 실행 과정:");
    numbers.stream()
        .mapToInt(Integer::intValue)
        .reduce(0, (acc, n) -> {
          int result = acc + n;
          System.out.printf("    acc=%d, n=%d → %d%n", acc, n, result);
          return result;
        });
    // acc=0, n=1 → 1
    // acc=1, n=2 → 3
    // acc=3, n=3 → 6
    // acc=6, n=4 → 10
    // acc=10, n=5 → 15

    System.out.println();
    System.out.println("→ 3인자 reduce는 결과 타입(U)이 요소 타입(T)과 다를 때 사용한다.");
    System.out.println("→ combiner는 병렬 스트림에서 부분 결과를 합칠 때만 호출된다.");
    System.out.println("→ 가변 컨테이너(List, Map)로 수집할 때는 reduce보다 collect()가 적합하다.");
  }
}

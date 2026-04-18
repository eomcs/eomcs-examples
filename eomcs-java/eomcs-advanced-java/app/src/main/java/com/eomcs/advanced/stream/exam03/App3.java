package com.eomcs.advanced.stream.exam03;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// 스트림 소스 생성 (3) - 무한 스트림 (iterate / generate):
//
// Stream.iterate(seed, UnaryOperator<T>)  (Java 8+)
//   - seed(초기값)에서 시작해 함수를 반복 적용하여 무한 스트림을 생성한다.
//   - 이전 값을 기반으로 다음 값을 계산한다. → 순서가 있는(상태 있는) 시퀀스에 적합하다.
//   - 반드시 limit() 또는 takeWhile()로 종료 조건을 지정해야 한다.
//
// Stream.iterate(seed, Predicate<T>, UnaryOperator<T>)  (Java 9+)
//   - Predicate가 false가 되면 스트림이 종료된다. for 루프와 유사하다.
//   - for (T x = seed; predicate(x); x = f(x)) 패턴의 선언형 대체이다.
//
// Stream.generate(Supplier<T>)
//   - Supplier를 반복 호출해 무한 스트림을 생성한다.
//   - 이전 값에 의존하지 않는 독립적인 값 생성에 적합하다. (난수, 상수 등)
//   - 반드시 limit() 또는 takeWhile()로 종료 조건을 지정해야 한다.
//
// takeWhile(Predicate<T>)  (Java 9+)
//   - 조건을 만족하는 동안 요소를 취하고, 처음으로 false가 되는 순간 스트림을 종료한다.
//   - filter()와 달리 조건이 false인 첫 요소에서 즉시 종료한다. (이후 요소는 검사 안 함)
//
// dropWhile(Predicate<T>)  (Java 9+)
//   - 조건을 만족하는 동안 요소를 버리고, 처음으로 false가 되는 순간부터 요소를 취한다.
//

public class App3 {

  public static void main(String[] args) {

    // ─────────────────────────────────────────────────────────────
    // 예제 1. Stream.iterate() - 등차 수열 (공차 d)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] Stream.iterate() - 등차 수열");

    // 0, 2, 4, 6, 8 ... (초기값 0, 공차 2)
    System.out.print("  짝수 수열: ");
    Stream.iterate(0, n -> n + 2) // 0 → 2 → 4 → 6 → ...
        .limit(6) // 6개로 제한 (limit 없으면 무한 루프)
        .forEach(n -> System.out.print(n + " ")); // 0 2 4 6 8 10
    System.out.println();

    // 1, 3, 5, 7, 9 ... (홀수 수열)
    System.out.print("  홀수 수열: ");
    Stream.iterate(1, n -> n + 2).limit(5).forEach(n -> System.out.print(n + " ")); // 1 3 5 7 9
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. Stream.iterate() - 등비 수열 (공비 r)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] Stream.iterate() - 등비 수열");

    // 1, 2, 4, 8, 16 ... (초기값 1, 공비 2)
    System.out.print("  2의 거듭제곱: ");
    Stream.iterate(1, n -> n * 2)
        .limit(8)
        .forEach(n -> System.out.print(n + " ")); // 1 2 4 8 16 32 64 128
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. Stream.iterate() Java 9+ - Predicate로 종료 조건 지정
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] Stream.iterate(seed, Predicate, UnaryOperator) - Java 9+");

    // for (int i = 0; i < 10; i += 3) 패턴과 동일
    System.out.print("  0~9 step 3: ");
    Stream.iterate(0, n -> n < 10, n -> n + 3) // 0, 3, 6, 9
        .forEach(n -> System.out.print(n + " "));
    System.out.println();

    // 2의 거듭제곱이 1000 미만인 동안 생성
    System.out.print("  2^n < 1000: ");
    Stream.iterate(1, n -> n < 1000, n -> n * 2)
        .forEach(n -> System.out.print(n + " ")); // 1 2 4 8 ... 512
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. Stream.generate() - 독립적인 값 생성
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] Stream.generate() - 상수·난수·고유 ID 생성");

    // 상수 스트림: 동일한 값을 반복
    System.out.print("  상수 스트림: ");
    Stream.generate(() -> "Hello")
        .limit(4)
        .forEach(s -> System.out.print(s + " ")); // Hello Hello Hello Hello
    System.out.println();

    // 난수 스트림: Math.random()을 반복 호출 (각 호출은 독립적)
    System.out.print("  난수 스트림: ");
    Stream.generate(Math::random)
        .limit(5)
        .map(d -> String.format("%.3f", d))
        .forEach(s -> System.out.print(s + " "));
    System.out.println();

    // 고유 ID 생성: 순차 번호를 생성하는 Supplier
    int[] counter = {1}; // 람다에서 참조하려면 배열이나 AtomicInteger 사용
    System.out.print("  순번 스트림: ");
    Stream.generate(() -> "ID-" + counter[0]++)
        .limit(5)
        .forEach(s -> System.out.print(s + " ")); // ID-1 ID-2 ID-3 ID-4 ID-5
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. takeWhile() - 조건이 false가 되는 순간 종료 (Java 9+)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] takeWhile() vs filter()");

    List<Integer> sorted = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    // filter(): 모든 요소를 검사하고, 조건을 만족하는 요소를 모두 반환한다.
    List<Integer> filtered =
        sorted.stream()
            .filter(n -> n < 5) // 모든 요소를 검사
            .collect(Collectors.toList());
    System.out.println("  filter(n<5):     " + filtered); // [1, 2, 3, 4]

    // takeWhile(): 조건이 false가 되는 첫 요소에서 즉시 종료한다. 이후 요소는 검사하지 않는다.
    // 정렬된 스트림에서 특히 효율적이다.
    List<Integer> taken =
        sorted.stream()
            .takeWhile(n -> n < 5) // 5에서 종료 → 5 이후 요소는 검사 안 함
            .collect(Collectors.toList());
    System.out.println("  takeWhile(n<5):  " + taken); // [1, 2, 3, 4]

    // 비정렬 데이터에서의 차이: takeWhile은 첫 번째 false에서 중단한다.
    List<Integer> unsorted = List.of(1, 5, 3, 2, 4);
    List<Integer> filterResult = unsorted.stream().filter(n -> n < 4).collect(Collectors.toList());
    List<Integer> takeWhileResult =
        unsorted.stream().takeWhile(n -> n < 4).collect(Collectors.toList());
    System.out.println("  비정렬 filter:     " + filterResult); // [1, 3, 2]
    System.out.println("  비정렬 takeWhile:  " + takeWhileResult); // [1] ← 1(<4) 통과, 5(>4) → 중단
    // 잠깐: 1<4 pass, 5<4 false → 중단 → [1]

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 6. dropWhile() - 조건을 만족하는 동안 버리기 (Java 9+)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 6] dropWhile() - 조건이 false가 되는 순간부터 취하기");

    List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    // dropWhile: n < 5인 동안 버리고, 5 이상이 되는 순간부터 모두 취한다.
    List<Integer> dropped =
        numbers.stream()
            .dropWhile(n -> n < 5) // 1,2,3,4 버림 → 5부터 취함
            .collect(Collectors.toList());
    System.out.println("  dropWhile(n<5): " + dropped); // [5, 6, 7, 8, 9, 10]

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 7. 피보나치 수열 - iterate로 이전 두 값을 추적
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 7] 피보나치 수열 - iterate와 int[] 상태");

    // int[]{prev, curr}로 두 값을 동시에 추적한다.
    // [0,1] → [1,1] → [1,2] → [2,3] → [3,5] → ...
    System.out.print("  피보나치:  ");
    Stream.iterate(new int[] {0, 1}, f -> new int[] {f[1], f[0] + f[1]})
        .limit(10)
        .map(f -> f[0]) // 첫 번째 값(현재 항)만 추출
        .forEach(n -> System.out.print(n + " ")); // 0 1 1 2 3 5 8 13 21 34
    System.out.println();

    System.out.println();
    System.out.println("→ Stream.iterate()는 이전 값을 기반으로 다음 값을 계산하는 수열에 적합하다.");
    System.out.println("→ Stream.generate()는 이전 값에 의존하지 않는 독립적인 값 생성에 적합하다.");
    System.out.println("→ takeWhile()/dropWhile()은 정렬된 데이터에서 범위를 지정할 때 효율적이다.");
  }
}

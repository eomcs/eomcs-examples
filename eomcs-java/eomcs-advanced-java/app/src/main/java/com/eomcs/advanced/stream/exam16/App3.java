package com.eomcs.advanced.stream.exam16;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

// 병렬 스트림과 공유 가변 상태:
//
// 병렬 스트림의 각 작업은 여러 스레드에서 동시에 실행될 수 있다.
// 따라서 외부의 가변 객체(ArrayList, int[] 등)를 직접 변경하면 경쟁 조건이 발생한다.
//
// 나쁜 예:
//   List<Integer> result = new ArrayList<>();
//   stream.parallel().forEach(result::add); // ArrayList는 스레드 안전하지 않다.
//
// 좋은 예:
//   stream.parallel().filter(...).boxed().toList();
//   collect() / toList()처럼 스트림의 수집 연산을 사용한다.
//

public class App3 {

  public static void main(String[] args) {

    // ─────────────────────────────────────────────────────────────
    // 예제 1. 나쁜 예 - 스레드 안전하지 않은 ArrayList에 직접 추가
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] 나쁜 예 - 공유 ArrayList 변경");

    List<Integer> unsafe = new ArrayList<>();

    try {
      IntStream.rangeClosed(1, 10_000)
          .parallel()
          .filter(n -> n % 2 == 0)
          .forEach(unsafe::add);  // 여러 스레드가 동시에 add() → 경쟁 조건 발생

      System.out.printf("  기대 개수: 5,000, 실제 개수: %,d%n", unsafe.size());
    } catch (RuntimeException e) {
      System.out.println("  실행 중 예외 발생: " + e.getClass().getSimpleName());
    }

    System.out.println("  → ArrayList는 여러 스레드가 동시에 add()하면 안전하지 않다.");
    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. 동기화 리스트 - 안전하지만 락 경합 발생
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] 동기화 리스트 사용");

    List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
    IntStream.rangeClosed(1, 10_000)
        .parallel()
        .filter(n -> n % 2 == 0)
        .forEach(synchronizedList::add);  // 동기화로 안전하지만 스레드 간 락 경합 발생

    System.out.printf("  기대 개수: 5,000, 실제 개수: %,d%n", synchronizedList.size());
    System.out.println("  → 동기화로 안전해졌지만, 락 경합 때문에 병렬 처리 이점이 줄어들 수 있다.");
    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. 권장 방식 - 스트림 수집 연산으로 안전하게 수집
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] 권장 - 스트림 수집 연산 사용");

    List<Integer> collected = IntStream.rangeClosed(1, 10_000)
        .parallel()
        .filter(n -> n % 2 == 0)
        .boxed()    // IntStream → Stream<Integer> (collect/toList는 객체 스트림 필요)
        .toList();  // 내부적으로 스레드 안전하게 부분 결과를 병합

    System.out.printf("  기대 개수: 5,000, 실제 개수: %,d%n", collected.size());
    System.out.println();
    System.out.println("→ 병렬 스트림에서는 외부 상태 변경보다 collect()/toList()/reduce() 같은 연산을 사용한다.");
  }
}

package com.eomcs.advanced.stream.exam10;

import java.util.Arrays;
import java.util.List;

// 반복 - forEachOrdered():
//
// forEachOrdered(Consumer<T>)
//   - 스트림의 encounter order(만남 순서)를 보장하며 각 요소에 Consumer를 실행한다.
//   - 순차 스트림에서는 forEach()와 동일하게 동작한다.
//   - 병렬 스트림에서 순서가 중요할 때 forEach() 대신 forEachOrdered()를 사용한다.
//
// forEach vs forEachOrdered (병렬 스트림):
//   forEach:         순서 미보장, 성능 우선
//   forEachOrdered:  소스 순서 보장, 병렬 처리의 이점 일부 희생
//
// encounter order(만남 순서):
//   - 스트림 소스의 요소가 파이프라인을 만나는 순서이다.
//   - List는 삽입 순서, Set은 순서 없음, sorted() 이후에는 정렬 순서이다.
//

public class App2 {

  public static void main(String[] args) {

    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    // ─────────────────────────────────────────────────────────────
    // 예제 1. 순차 스트림 - forEach vs forEachOrdered 동일
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] 순차 스트림 - forEach vs forEachOrdered (결과 동일)");

    System.out.print("  forEach:         ");
    numbers.stream().filter(n -> n % 2 == 0).forEach(n -> System.out.print(n + " ")); // 2 4 6 8 10
    System.out.println();

    System.out.print("  forEachOrdered:  ");
    numbers.stream()
        .filter(n -> n % 2 == 0)
        .forEachOrdered(n -> System.out.print(n + " ")); // 2 4 6 8 10
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. 병렬 스트림 - forEach(순서 미보장) vs forEachOrdered(순서 보장)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] 병렬 스트림 - forEach vs forEachOrdered");

    System.out.print("  forEach(병렬):         ");
    numbers.parallelStream()
        .filter(n -> n % 2 == 0)
        .forEach(n -> System.out.print(n + " ")); // 순서 미보장 (실행마다 다를 수 있음)
    System.out.println("  ← 순서 미보장");

    System.out.print("  forEachOrdered(병렬):  ");
    numbers.parallelStream()
        .filter(n -> n % 2 == 0)
        .forEachOrdered(n -> System.out.print(n + " ")); // 항상 2 4 6 8 10
    System.out.println("  ← 순서 보장");

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. 정렬 후 forEachOrdered - 정렬 순서 유지
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] sorted() + forEachOrdered - 정렬 순서 유지");

    List<String> names = Arrays.asList("Charlie", "Alice", "Eve", "Bob", "Dave");

    System.out.print("  sorted + forEachOrdered: ");
    names.parallelStream()
        .sorted()
        .forEachOrdered(name -> System.out.print(name + " ")); // Alice Bob Charlie Dave Eve
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. 언제 forEachOrdered를 써야 하는가?
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] 언제 forEachOrdered를 쓰는가?");

    // 순서가 중요한 출력 (예: 순번이 있는 목록)
    System.out.println("  순번 있는 목록 출력 (병렬 스트림에서도 순서 보장):");
    int[] index = {1};
    names.parallelStream()
        .sorted()
        .forEachOrdered(name -> System.out.println("    " + index[0]++ + ". " + name));

    System.out.println();
    System.out.println("→ 순차 스트림에서 forEach와 forEachOrdered는 동일하게 동작한다.");
    System.out.println("→ 병렬 스트림에서 순서가 중요하면 forEachOrdered를 사용한다.");
    System.out.println("→ 병렬 스트림에서 forEachOrdered는 순서 보장을 위해 성능 일부를 희생한다.");
  }
}

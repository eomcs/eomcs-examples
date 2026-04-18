package com.eomcs.advanced.stream.exam13;

import java.util.Arrays;
import java.util.List;

// 배열 변환 - toArray():
//
// toArray()
//   - 스트림의 요소를 배열로 수집하는 최종 연산이다.
//   - 두 가지 형태가 있다.
//
// 형태 1: toArray() → Object[]
//   - 타입 인수 없이 호출한다.
//   - Object[] 를 반환한다. 특정 타입 배열로 캐스팅이 필요하다.
//
// 형태 2: toArray(IntFunction<A[]>) → A[]
//   - 배열 생성자 레퍼런스를 넘긴다. (예: String[]::new)
//   - 정확한 타입의 배열을 반환한다. 캐스팅 불필요.
//   - IntFunction<A[]>: int(배열 크기) → A[] 를 만드는 함수이다.
//

public class App {

  public static void main(String[] args) {

    List<String>  names   = Arrays.asList("Charlie", "Alice", "Eve", "Bob", "Dave");
    List<Integer> numbers = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6);

    // ─────────────────────────────────────────────────────────────
    // 예제 1. toArray() → Object[]
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] toArray() → Object[]");

    Object[] objArray = names.stream().toArray();
    System.out.println("  타입: " + objArray.getClass().getSimpleName()); // Object[]
    System.out.println("  길이: " + objArray.length); // 5
    System.out.print("  내용: ");
    for (Object o : objArray) System.out.print(o + " ");
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. toArray(IntFunction<A[]>) → A[] (권장)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] toArray(IntFunction) → 타입 배열 (권장)");

    // 배열 생성자 레퍼런스: String[]::new == (int n) -> new String[n]
    String[] strArray = names.stream().toArray(String[]::new);
    System.out.println("  타입: " + strArray.getClass().getSimpleName()); // String[]
    System.out.println("  길이: " + strArray.length); // 5
    System.out.println("  내용: " + Arrays.toString(strArray));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. 중간 연산 후 toArray
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] filter + sorted + toArray");

    // 짝수만 정렬 후 배열로
    int[] evenSorted = numbers.stream()
        .mapToInt(Integer::intValue)
        .filter(n -> n % 2 == 0)
        .sorted()
        .toArray(); // IntStream.toArray() → int[]
    System.out.println("  짝수 정렬 배열: " + Arrays.toString(evenSorted)); // [2, 4, 6]

    // 이름 길이 순 정렬 후 배열로
    String[] sortedByLength = names.stream()
        .sorted(java.util.Comparator.comparingInt(String::length))
        .toArray(String[]::new);
    System.out.println("  이름 길이순 배열: " + Arrays.toString(sortedByLength));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. toArray vs toList - 가변성 차이
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] toArray vs toList - 가변성 비교");

    String[] array = names.stream().toArray(String[]::new);
    List<String> list = names.stream().toList(); // Java 16+ (불변)

    // 배열은 요소 변경 가능
    array[0] = "Modified";
    System.out.println("  배열 요소 변경 가능: " + Arrays.toString(array));

    // toList()는 불변 리스트
    try {
      list.set(0, "Modified");
    } catch (UnsupportedOperationException e) {
      System.out.println("  toList()는 불변: UnsupportedOperationException 발생");
    }

    System.out.println();
    System.out.println("→ toArray()는 Object[]를 반환한다. 타입 배열이 필요하면 toArray(T[]::new)를 사용한다.");
    System.out.println("→ IntStream.toArray()는 int[]를 직접 반환한다.");
    System.out.println("→ 배열은 요소 변경 가능, toList()는 불변이다.");
  }
}

package com.eomcs.advanced.stream.exam14;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

// 수집 기초 - toList / toUnmodifiableList / toSet / toUnmodifiableSet:
//
// collect(Collector<T,A,R>) → R
//   - 스트림의 요소를 컬렉션이나 다른 자료구조로 수집하는 최종 연산이다.
//   - Collectors 클래스가 다양한 Collector 팩토리 메서드를 제공한다.
//
// toList() (Java 16+)
//   - 스트림 요소를 불변 List로 수집한다.
//   - collect(Collectors.toList())보다 간결하다. (Java 16+)
//
// Collectors.toList()
//   - 가변 List로 수집한다. (추가/삭제 가능)
//
// Collectors.toUnmodifiableList()
//   - 불변 List로 수집한다. (Java 10+)
//   - null 요소를 허용하지 않는다.
//
// Collectors.toSet()
//   - 가변 Set으로 수집한다. 중복 제거, 순서 미보장.
//
// Collectors.toCollection(Supplier)
//   - 특정 컬렉션 구현체로 수집한다. (LinkedList, TreeSet 등)
//

public class App {

  public static void main(String[] args) {

    List<String>  names   = Arrays.asList("Charlie", "Alice", "Eve", "Bob", "Alice", "Dave");
    List<Integer> numbers = Arrays.asList(5, 3, 8, 1, 9, 2, 7, 4, 6, 10);

    // ─────────────────────────────────────────────────────────────
    // 예제 1. toList() vs Collectors.toList()
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] toList() vs Collectors.toList()");

    // Java 16+ toList() — 불변 리스트
    List<String> immutableList = names.stream()
        .filter(name -> name.length() > 3)
        .toList();
    System.out.println("  toList(): " + immutableList);
    try {
      immutableList.add("New");
    } catch (UnsupportedOperationException e) {
      System.out.println("  toList()는 불변: 추가 불가");
    }

    // Collectors.toList() — 가변 리스트
    List<String> mutableList = names.stream()
        .filter(name -> name.length() > 3)
        .collect(Collectors.toList());
    mutableList.add("Frank"); // 추가 가능
    System.out.println("  Collectors.toList(): " + mutableList);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. Collectors.toUnmodifiableList()
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] Collectors.toUnmodifiableList() - 불변 리스트 (Java 10+)");

    List<Integer> unmodifiable = numbers.stream()
        .filter(n -> n > 5)
        .sorted()
        .collect(Collectors.toUnmodifiableList());
    System.out.println("  결과: " + unmodifiable); // [6, 7, 8, 9, 10]
    try {
      unmodifiable.add(99);
    } catch (UnsupportedOperationException e) {
      System.out.println("  불변 리스트: 추가 불가");
    }

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. Collectors.toSet() - 중복 제거
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] Collectors.toSet() - 중복 제거 (순서 미보장)");

    Set<String> nameSet = names.stream()
        .collect(Collectors.toSet());
    System.out.println("  toSet(): " + nameSet); // Alice 중복 제거, 순서 미보장

    // toUnmodifiableSet() - 불변 Set
    Set<String> unmodifiableSet = names.stream()
        .collect(Collectors.toUnmodifiableSet());
    System.out.println("  toUnmodifiableSet(): " + unmodifiableSet);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. Collectors.toCollection() - 특정 구현체로 수집
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] Collectors.toCollection() - 특정 컬렉션 구현체");

    // LinkedList로 수집
    LinkedList<String> linkedList = names.stream()
        .filter(name -> name.length() > 3)
        .collect(Collectors.toCollection(LinkedList::new));
    System.out.println("  LinkedList: " + linkedList);

    // TreeSet으로 수집 (정렬 + 중복 제거)
    TreeSet<String> treeSet = names.stream()
        .collect(Collectors.toCollection(TreeSet::new));
    System.out.println("  TreeSet(정렬+중복제거): " + treeSet); // [Alice, Bob, Charlie, Dave, Eve]

    System.out.println();
    System.out.println("→ toList()는 Java 16+, 불변. Collectors.toList()는 가변.");
    System.out.println("→ toSet()은 중복 제거, 순서 미보장. TreeSet으로 정렬된 Set을 얻으려면 toCollection(TreeSet::new).");
    System.out.println("→ toCollection(Supplier)으로 원하는 컬렉션 구현체를 지정할 수 있다.");
  }
}

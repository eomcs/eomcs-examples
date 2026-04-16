package com.eomcs.quickstart.collection.exam08;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Collections.unmodifiableXxx():
// - java.util.Collections 유틸리티 클래스가 제공하는 읽기 전용 래퍼 메서드이다.
// - 기존 컬렉션 인스턴스를 인수로 받아 수정 연산(add/set/put/remove 등)을 금지한 래퍼를 반환한다.
// - 수정 메서드를 호출하면 UnsupportedOperationException이 발생한다.
// - 래퍼는 원본 컬렉션의 뷰(view)이다.
//   → 원본 컬렉션을 수정하면 래퍼를 통해 읽은 결과도 함께 변경된다.
//   → 원본에 대한 참조를 외부에 노출하지 않아야 완전한 불변성을 보장할 수 있다.
//
// 주요 팩토리 메서드:
//   Collections.unmodifiableList(List<T> list)
//   Collections.unmodifiableSet(Set<T> set)
//   Collections.unmodifiableMap(Map<K,V> map)
//   Collections.unmodifiableSortedSet(SortedSet<T> set)
//   Collections.unmodifiableSortedMap(SortedMap<K,V> map)
//

public class App {

  public static void main(String[] args) {

    // 1. unmodifiableList() - 기본 사용
    System.out.println("[unmodifiableList() - 기본 사용]");
    List<String> original = new ArrayList<>();
    original.add("apple");
    original.add("banana");
    original.add("cherry");

    List<String> readOnly = Collections.unmodifiableList(original);
    System.out.println("readOnly: " + readOnly);
    System.out.println("get(0): " + readOnly.get(0));  // 읽기 허용
    System.out.println("size(): " + readOnly.size());  // 읽기 허용

    // 수정 시도 → UnsupportedOperationException
    try {
      readOnly.add("mango");
    } catch (UnsupportedOperationException e) {
      System.out.println("add() → UnsupportedOperationException");
    }
    try {
      readOnly.set(0, "grape");
    } catch (UnsupportedOperationException e) {
      System.out.println("set() → UnsupportedOperationException");
    }
    try {
      readOnly.remove(0);
    } catch (UnsupportedOperationException e) {
      System.out.println("remove() → UnsupportedOperationException");
    }

    // 2. unmodifiableSet() - 기본 사용
    System.out.println("\n[unmodifiableSet() - 기본 사용]");
    Set<Integer> originalSet = new HashSet<>();
    originalSet.add(1);
    originalSet.add(2);
    originalSet.add(3);

    Set<Integer> readOnlySet = Collections.unmodifiableSet(originalSet);
    System.out.println("readOnlySet: " + readOnlySet);
    System.out.println("contains(2): " + readOnlySet.contains(2)); // 읽기 허용

    try {
      readOnlySet.add(4);
    } catch (UnsupportedOperationException e) {
      System.out.println("add() → UnsupportedOperationException");
    }

    // 3. unmodifiableMap() - 기본 사용
    System.out.println("\n[unmodifiableMap() - 기본 사용]");
    Map<String, Integer> originalMap = new HashMap<>();
    originalMap.put("apple", 1000);
    originalMap.put("banana", 500);

    Map<String, Integer> readOnlyMap = Collections.unmodifiableMap(originalMap);
    System.out.println("readOnlyMap: " + readOnlyMap);
    System.out.println("get(\"apple\"): " + readOnlyMap.get("apple")); // 읽기 허용

    try {
      readOnlyMap.put("cherry", 300);
    } catch (UnsupportedOperationException e) {
      System.out.println("put() → UnsupportedOperationException");
    }
    try {
      readOnlyMap.remove("apple");
    } catch (UnsupportedOperationException e) {
      System.out.println("remove() → UnsupportedOperationException");
    }

    // 4. 원본 수정 시 래퍼에도 반영 (뷰 특성)
    System.out.println("\n[원본 수정 → 래퍼에도 반영 (뷰 특성)]");
    List<String> mutableList = new ArrayList<>();
    mutableList.add("A");
    mutableList.add("B");
    List<String> viewList = Collections.unmodifiableList(mutableList);
    System.out.println("수정 전 viewList: " + viewList); // [A, B]

    mutableList.add("C"); // 원본 수정
    System.out.println("원본 add(C) 후 viewList: " + viewList); // [A, B, C] - 함께 반영

    // 5. 완전한 불변성 - 원본 참조를 차단
    System.out.println("\n[완전한 불변성 - 원본 참조 차단]");
    // 나쁜 예: 원본 참조가 외부에 남아 있음
    List<String> leak = new ArrayList<>(List.of("X", "Y"));
    List<String> notSafe = Collections.unmodifiableList(leak);
    leak.add("Z"); // 원본을 통한 우회 수정
    System.out.println("notSafe (원본 수정됨): " + notSafe); // [X, Y, Z]

    // 좋은 예: 새 컬렉션으로 복사 후 래핑
    List<String> safe = Collections.unmodifiableList(new ArrayList<>(List.of("X", "Y")));
    // 원본 참조가 없으므로 래퍼 외부에서 수정 불가
    System.out.println("safe (원본 참조 없음): " + safe); // [X, Y]
  }
}

package com.eomcs.quickstart.collection.exam08;

import java.util.List;
import java.util.Map;
import java.util.Set;

// List.of() / Set.of() / Map.of() (Java 9+):
// - 간결한 팩토리 메서드로 불변(immutable) 컬렉션을 생성한다.
// - 생성 후 수정 연산(add/set/put/remove)을 호출하면 UnsupportedOperationException이 발생한다.
// - Collections.unmodifiableXxx()와 달리 뷰가 아니다.
//   원본 컬렉션이 존재하지 않으므로 우회 수정이 불가능하다.
// - null 요소/키/값을 허용하지 않는다. (null → NullPointerException)
// - Set.of()와 Map.of()의 요소 순서는 보장되지 않는다.
// - Map.of()는 최대 10쌍까지 지원한다.
//   10쌍 초과 시 Map.ofEntries(Map.entry(k, v), ...)를 사용한다.
//
// List.copyOf() / Set.copyOf() / Map.copyOf() (Java 10+):
// - 기존 컬렉션의 요소를 복사하여 새로운 불변 컬렉션을 생성한다.
// - 원본과 독립적이므로 원본이 변경되어도 복사본에 영향을 주지 않는다.
// - null 요소를 허용하지 않는다.
//

public class App2 {

  public static void main(String[] args) {

    // 1. List.of() - 불변 List 생성
    System.out.println("[List.of() - 불변 List]");
    List<String> immutableList = List.of("apple", "banana", "cherry", "mango");
    System.out.println("immutableList: " + immutableList);
    System.out.println("get(1): " + immutableList.get(1));       // 읽기 허용
    System.out.println("contains(\"apple\"): " + immutableList.contains("apple"));

    try { immutableList.add("grape"); } catch (UnsupportedOperationException e) {
      System.out.println("add() → UnsupportedOperationException");
    }
    try { immutableList.set(0, "grape"); } catch (UnsupportedOperationException e) {
      System.out.println("set() → UnsupportedOperationException");
    }

    // null 불허
    try { List.of("a", null, "b"); } catch (NullPointerException e) {
      System.out.println("List.of(null) → NullPointerException");
    }

    // 2. Set.of() - 불변 Set 생성
    System.out.println("\n[Set.of() - 불변 Set]");
    Set<Integer> immutableSet = Set.of(10, 20, 30, 40, 50);
    System.out.println("immutableSet: " + immutableSet); // 순서 미보장
    System.out.println("contains(30): " + immutableSet.contains(30));

    try { immutableSet.add(60); } catch (UnsupportedOperationException e) {
      System.out.println("add() → UnsupportedOperationException");
    }

    // 중복 요소 불허
    try { Set.of(1, 2, 2, 3); } catch (IllegalArgumentException e) {
      System.out.println("Set.of(중복) → IllegalArgumentException");
    }

    // null 불허
    try { Set.of(1, null, 3); } catch (NullPointerException e) {
      System.out.println("Set.of(null) → NullPointerException");
    }

    // 3. Map.of() - 불변 Map 생성 (최대 10쌍)
    System.out.println("\n[Map.of() - 불변 Map (최대 10쌍)]");
    Map<String, Integer> immutableMap = Map.of(
        "apple", 1000,
        "banana", 500,
        "cherry", 300
    );
    System.out.println("immutableMap: " + immutableMap); // 순서 미보장
    System.out.println("get(\"apple\"): " + immutableMap.get("apple"));

    try { immutableMap.put("mango", 700); } catch (UnsupportedOperationException e) {
      System.out.println("put() → UnsupportedOperationException");
    }

    // 중복 키 불허
    try { Map.of("a", 1, "a", 2); } catch (IllegalArgumentException e) {
      System.out.println("Map.of(중복 키) → IllegalArgumentException");
    }

    // 4. Map.ofEntries() - 10쌍 초과 시 사용
    System.out.println("\n[Map.ofEntries() - 10쌍 초과]");
    Map<String, Integer> largeMap = Map.ofEntries(
        Map.entry("a", 1),
        Map.entry("b", 2),
        Map.entry("c", 3),
        Map.entry("d", 4),
        Map.entry("e", 5),
        Map.entry("f", 6),
        Map.entry("g", 7),
        Map.entry("h", 8),
        Map.entry("i", 9),
        Map.entry("j", 10),
        Map.entry("k", 11)  // 11쌍 - Map.of()로는 불가
    );
    System.out.println("largeMap 크기: " + largeMap.size()); // 11

    // 5. List.copyOf() - 기존 컬렉션 복사하여 불변 List 생성 (Java 10+)
    System.out.println("\n[List.copyOf() - 복사 불변 List]");
    List<String> mutable = new java.util.ArrayList<>();
    mutable.add("X");
    mutable.add("Y");
    mutable.add("Z");

    List<String> copied = List.copyOf(mutable); // 복사
    System.out.println("copied: " + copied);

    mutable.add("W"); // 원본 수정
    System.out.println("원본 add(W) 후 copied: " + copied); // 영향 없음 (독립적)
    System.out.println("원본: " + mutable);

    try { copied.add("V"); } catch (UnsupportedOperationException e) {
      System.out.println("copied.add() → UnsupportedOperationException");
    }

    // 6. Set.copyOf() / Map.copyOf()
    System.out.println("\n[Set.copyOf() / Map.copyOf()]");
    Set<String> mutableSet = new java.util.HashSet<>(Set.of("A", "B", "C"));
    Set<String> copiedSet = Set.copyOf(mutableSet);
    mutableSet.add("D");
    System.out.println("원본 add(D) 후 copiedSet: " + copiedSet); // 영향 없음

    Map<String, Integer> mutableMap = new java.util.HashMap<>(Map.of("x", 1, "y", 2));
    Map<String, Integer> copiedMap = Map.copyOf(mutableMap);
    mutableMap.put("z", 3);
    System.out.println("원본 put(z) 후 copiedMap: " + copiedMap); // 영향 없음

    // 7. List.of() vs Collections.unmodifiableList() 비교
    System.out.println("\n[List.of() vs unmodifiableList() 비교]");
    List<String> base = new java.util.ArrayList<>(List.of("A", "B"));
    List<String> unmod = java.util.Collections.unmodifiableList(base);
    List<String> immut = List.of("A", "B");

    base.add("C"); // 원본 수정
    System.out.println("unmodifiableList (원본 수정 반영): " + unmod); // [A, B, C]
    System.out.println("List.of()        (독립):           " + immut); // [A, B]
  }
}

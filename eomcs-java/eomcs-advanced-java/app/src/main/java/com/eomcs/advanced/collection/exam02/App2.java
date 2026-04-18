package com.eomcs.advanced.collection.exam02;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

// Set 인터페이스:
// - java.util 패키지에 소속되어 있다.
// - Collection을 상속한다.
// - 중복 항목을 허용하지 않는다.
// - 인덱스로 접근하는 메서드가 없다. (Collection의 메서드만 사용)
//
// 주요 구현체:
//   HashSet        : 순서 미보장. 해시 기반으로 빠른 검색(O(1))
//   LinkedHashSet  : 추가 순서 유지. HashSet보다 약간 느리지만 순서가 필요할 때 사용
//   TreeSet        : 항목을 자연 순서(오름차순)로 정렬. 검색 속도 O(log n)
//

public class App2 {

  public static void main(String[] args) {

    // 1. HashSet - 순서 미보장, 중복 불허
    System.out.println("[HashSet - 순서 미보장, 중복 불허]");
    Set<String> hashSet = new HashSet<>();
    hashSet.add("바나나");
    hashSet.add("사과");
    hashSet.add("딸기");
    hashSet.add("사과"); // 중복 → 무시
    System.out.println("크기: " + hashSet.size()); // 3
    System.out.println(hashSet); // 순서 미보장

    // 2. contains() - 해시 기반으로 빠르게 검색
    System.out.println("\n[contains]");
    System.out.println("contains(\"사과\"): " + hashSet.contains("사과")); // true
    System.out.println("contains(\"포도\"): " + hashSet.contains("포도")); // false

    // 3. remove()
    System.out.println("\n[remove]");
    hashSet.remove("바나나");
    System.out.println(hashSet);

    System.out.println();

    // 4. LinkedHashSet - 추가 순서 유지, 중복 불허
    System.out.println("[LinkedHashSet - 추가 순서 유지, 중복 불허]");
    Set<String> linkedSet = new LinkedHashSet<>();
    linkedSet.add("바나나");
    linkedSet.add("사과");
    linkedSet.add("딸기");
    linkedSet.add("사과"); // 중복 → 무시
    System.out.println(linkedSet); // [바나나, 사과, 딸기] - 추가 순서 유지

    System.out.println();

    // 5. TreeSet - 자연 순서(오름차순) 정렬, 중복 불허
    System.out.println("[TreeSet - 오름차순 정렬, 중복 불허]");
    Set<String> treeSet = new TreeSet<>();
    treeSet.add("바나나");
    treeSet.add("사과");
    treeSet.add("딸기");
    treeSet.add("사과"); // 중복 → 무시
    System.out.println(treeSet); // [딸기, 바나나, 사과] - 알파벳 오름차순

    // 6. TreeSet의 정렬된 숫자 예제
    System.out.println("\n[TreeSet - 정수 오름차순 정렬]");
    Set<Integer> numbers = new TreeSet<>();
    numbers.add(30);
    numbers.add(10);
    numbers.add(20);
    numbers.add(10); // 중복 → 무시
    System.out.println(numbers); // [10, 20, 30]
  }
}

package com.eomcs.quickstart.collection.exam05;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

// LinkedHashMap:
// - java.util 패키지에 소속되어 있다.
// - HashMap을 상속하고, 내부적으로 이중 연결 리스트를 추가로 유지한다.
// - 기본 동작: 항목의 추가 순서(insertion order)를 유지한다.
// - 옵션 동작: 생성자에 accessOrder=true를 전달하면 접근 순서(access order)를 유지한다.
//   접근 순서 모드에서는 get() / put()으로 접근한 항목이 맨 뒤로 이동한다.
//   이 특성을 이용하면 LRU(Least Recently Used) 캐시를 쉽게 구현할 수 있다.
// - HashMap보다 메모리를 약간 더 사용한다. (연결 리스트 노드 오버헤드)
// - 검색·삽입·삭제 모두 O(1)이다.
// - 키와 값으로 null을 허용한다.
// - 동기화되지 않는다.
//
// HashMap, LinkedHashMap, TreeMap 비교:
//   HashMap       : 순서 미보장, O(1), null 키 허용
//   LinkedHashMap : 추가/접근 순서 유지, O(1), null 키 허용
//   TreeMap       : 키 기준 정렬, O(log n), null 키 불허
//

public class App5 {

  public static void main(String[] args) {

    // 1. 추가 순서 유지 (기본 동작)
    System.out.println("[LinkedHashMap - 추가 순서 유지]");
    Map<String, Integer> linked = new LinkedHashMap<>();
    linked.put("banana", 500);
    linked.put("apple", 1000);
    linked.put("mango", 700);
    linked.put("strawberry", 800);
    System.out.println("LinkedHashMap: " + linked); // 추가 순서 유지

    Map<String, Integer> hash = new HashMap<>();
    hash.put("banana", 500);
    hash.put("apple", 1000);
    hash.put("mango", 700);
    hash.put("strawberry", 800);
    System.out.println("HashMap:       " + hash); // 순서 미보장

    // 2. 중복 키 추가 시 순서 변화 없음
    System.out.println("\n[중복 키 추가 - 순서 유지]");
    linked.put("apple", 1200); // 이미 있는 키 → 값만 교체, 순서는 그대로
    System.out.println(linked); // apple은 여전히 두 번째 위치

    // 3. remove 후 재추가 시 맨 뒤로 이동
    System.out.println("\n[remove 후 재추가 - 맨 뒤로]");
    linked.remove("apple");
    linked.put("apple", 1000);
    System.out.println(linked); // apple이 맨 뒤로 이동

    System.out.println();

    // 4. 접근 순서 모드 (accessOrder = true)
    System.out.println("[접근 순서 모드 (accessOrder=true)]");
    // 생성자: LinkedHashMap(initialCapacity, loadFactor, accessOrder)
    LinkedHashMap<String, Integer> accessMap =
        new LinkedHashMap<>(16, 0.75f, true);
    accessMap.put("A", 1);
    accessMap.put("B", 2);
    accessMap.put("C", 3);
    accessMap.put("D", 4);
    System.out.println("초기 상태: " + accessMap); // [A, B, C, D]

    accessMap.get("B");        // B 접근 → B가 맨 뒤로 이동
    System.out.println("get(\"B\") 후: " + accessMap); // [A, C, D, B]

    accessMap.get("A");        // A 접근 → A가 맨 뒤로 이동
    System.out.println("get(\"A\") 후: " + accessMap); // [C, D, B, A]

    accessMap.put("C", 30);    // C 접근(put) → C가 맨 뒤로 이동
    System.out.println("put(\"C\") 후: " + accessMap); // [D, B, A, C]

    System.out.println();

    // 5. LRU 캐시 구현 - accessOrder + removeEldestEntry() 재정의
    System.out.println("[LRU 캐시 구현 (최대 3개 유지)]");
    final int CAPACITY = 3;
    LinkedHashMap<String, Integer> lruCache =
        new LinkedHashMap<>(CAPACITY, 0.75f, true) {
          @Override
          protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
            // 항목 수가 최대 용량을 초과하면 가장 오래 전에 접근한 항목(맨 앞)을 자동 제거
            return size() > CAPACITY;
          }
        };

    lruCache.put("page1", 1);
    lruCache.put("page2", 2);
    lruCache.put("page3", 3);
    System.out.println("초기: " + lruCache);     // [page1, page2, page3]

    lruCache.get("page1");                        // page1 최근 접근 → 맨 뒤로
    System.out.println("get(page1): " + lruCache); // [page2, page3, page1]

    lruCache.put("page4", 4);                     // 용량 초과 → 가장 오래된 page2 제거
    System.out.println("put(page4): " + lruCache); // [page3, page1, page4]

    lruCache.put("page5", 5);                     // 용량 초과 → 가장 오래된 page3 제거
    System.out.println("put(page5): " + lruCache); // [page1, page4, page5]
  }
}

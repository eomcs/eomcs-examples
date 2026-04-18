package com.eomcs.advanced.collection.exam05;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

// Hashtable:
// - java.util 패키지에 소속되어 있다.
// - Map 인터페이스를 구현한다.
// - HashMap과 마찬가지로 해시 테이블을 내부 자료구조로 사용한다.
// - Java 1.0부터 제공된 레거시 클래스이다.
//
// HashMap과의 차이점:
// - 모든 메서드가 synchronized(동기화)되어 있어 멀티스레드 환경에서 안전하다.
// - 단일 스레드 환경에서는 동기화 비용으로 인해 HashMap보다 느리다.
// - 키와 값으로 null을 허용하지 않는다. (null 사용 시 NullPointerException)
// - 항목의 순서를 보장하지 않는다.
//
// 실무 권장 사항:
// - 단일 스레드 환경이라면 HashMap을 사용한다.
// - 멀티스레드 환경이라면 Hashtable 대신 ConcurrentHashMap을 사용한다.
//   ConcurrentHashMap은 전체 Map을 잠그지 않고 버킷 단위로 잠그므로 더 효율적이다.
// - Hashtable은 하위 호환성을 위해 유지되고 있으므로 신규 코드에서는 사용하지 않는다.
//

public class App2 {

  public static void main(String[] args) {

    // 1. 기본 사용법 - HashMap과 동일
    System.out.println("[Hashtable - 기본 사용]");
    Hashtable<String, Integer> table = new Hashtable<>();
    table.put("apple", 1000);
    table.put("banana", 500);
    table.put("strawberry", 800);
    System.out.println("크기: " + table.size());
    System.out.println("apple: " + table.get("apple"));
    System.out.println(table);

    // 2. null 키/값 불허
    System.out.println("\n[null 키/값 불허]");
    try {
      table.put(null, 0); // NullPointerException
    } catch (NullPointerException e) {
      System.out.println("null 키 → NullPointerException");
    }
    try {
      table.put("empty", null); // NullPointerException
    } catch (NullPointerException e) {
      System.out.println("null 값 → NullPointerException");
    }

    // 3. Hashtable 레거시 메서드
    System.out.println("\n[Hashtable 레거시 메서드]");
    System.out.println("contains(500): " + table.contains(500));   // containsValue()와 동일
    System.out.println("keys(): " + table.keys().nextElement());    // keySet()과 유사
    System.out.println("elements(): " + table.elements().nextElement()); // values()와 유사

    // 4. HashMap vs Hashtable 비교
    System.out.println("\n[HashMap vs Hashtable 비교]");
    Map<String, Integer> hashMap   = new HashMap<>();
    Map<String, Integer> hashTable = new Hashtable<>();

    hashMap.put("A", 1);
    hashMap.put("B", 2);
    hashTable.put("A", 1);
    hashTable.put("B", 2);

    // null 키 허용 여부
    hashMap.put(null, 0);   // HashMap: 허용
    System.out.println("HashMap null 키: " + hashMap.get(null)); // 0
    try {
      hashTable.put(null, 0); // Hashtable: 불허
    } catch (NullPointerException e) {
      System.out.println("Hashtable null 키: NullPointerException");
    }

    // Map 인터페이스를 공통으로 구현하므로 동일한 방식으로 순회 가능
    System.out.println("\n[Map 타입으로 통일된 순회]");
    for (Map.Entry<String, Integer> entry : hashTable.entrySet()) {
      System.out.println("  " + entry.getKey() + " → " + entry.getValue());
    }
  }
}

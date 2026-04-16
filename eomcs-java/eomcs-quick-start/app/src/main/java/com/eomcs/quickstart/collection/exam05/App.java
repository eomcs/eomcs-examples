package com.eomcs.quickstart.collection.exam05;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// Map 인터페이스:
// - java.util 패키지에 소속되어 있다.
// - 키(key)와 값(value)의 쌍으로 항목을 관리한다.
// - 키는 중복될 수 없고, 각 키는 하나의 값에만 대응된다.
// - Collection 인터페이스를 상속하지 않는 별도의 계층이다.
//
// HashMap:
// - Map 인터페이스의 가장 일반적인 구현체이다.
// - 내부적으로 해시 테이블(hash table)을 사용한다.
// - 키의 hashCode()와 equals()를 이용해 항목을 저장·검색한다.
// - 키와 값으로 null을 허용한다.
// - 동기화되지 않아 단일 스레드 환경에서 빠르다.
// - 항목의 순서를 보장하지 않는다.
//
// Map 인터페이스 주요 메서드:
//   V put(K key, V value)          키-값 쌍 추가. 기존 키면 값 교체 후 이전 값 반환
//   V get(Object key)              키에 대응하는 값 반환. 없으면 null
//   V getOrDefault(Object, V)      키가 없으면 기본값 반환
//   V remove(Object key)           키-값 쌍 제거. 제거된 값 반환
//   boolean containsKey(Object)    키 존재 여부 반환
//   boolean containsValue(Object)  값 존재 여부 반환
//   int size()                     항목 개수 반환
//   boolean isEmpty()              항목이 없으면 true 반환
//   void clear()                   모든 항목 제거
//   Set<K> keySet()                모든 키를 Set으로 반환
//   Collection<V> values()         모든 값을 Collection으로 반환
//   Set<Map.Entry<K,V>> entrySet() 모든 키-값 쌍을 Entry Set으로 반환
//   V putIfAbsent(K, V)            키가 없을 때만 추가. 기존 값 반환(없으면 null)
//

public class App {

  public static void main(String[] args) {

    // 1. put() / get() / size()
    System.out.println("[HashMap - 기본 사용]");
    Map<String, Integer> map = new HashMap<>();
    map.put("apple", 1000);
    map.put("banana", 500);
    map.put("strawberry", 800);
    System.out.println("크기: " + map.size());
    System.out.println("apple: " + map.get("apple"));
    System.out.println("grape: " + map.get("grape")); // 없는 키 → null

    // 2. getOrDefault() - 키가 없을 때 기본값 반환
    System.out.println("\n[getOrDefault]");
    System.out.println("grape (기본값 0): " + map.getOrDefault("grape", 0));

    // 3. 중복 키 - 기존 값을 교체하고 이전 값 반환
    System.out.println("\n[중복 키 - 값 교체]");
    Integer old = map.put("apple", 1200);
    System.out.println("apple 이전 값: " + old);
    System.out.println("apple 새 값: " + map.get("apple"));

    // 4. putIfAbsent() - 키가 없을 때만 추가
    System.out.println("\n[putIfAbsent]");
    map.putIfAbsent("apple", 9999);  // 이미 있으므로 추가 안 함
    map.putIfAbsent("mango", 700);   // 없으므로 추가
    System.out.println("apple: " + map.get("apple")); // 1200 (변경 없음)
    System.out.println("mango: " + map.get("mango")); // 700

    // 5. containsKey() / containsValue()
    System.out.println("\n[containsKey / containsValue]");
    System.out.println("containsKey(\"banana\"): " + map.containsKey("banana"));
    System.out.println("containsKey(\"grape\"): "  + map.containsKey("grape"));
    System.out.println("containsValue(500): "      + map.containsValue(500));

    // 6. remove()
    System.out.println("\n[remove]");
    Integer removed = map.remove("banana");
    System.out.println("제거된 값: " + removed);
    System.out.println("제거 후 크기: " + map.size());

    // 7. keySet() - 키만 순회
    System.out.println("\n[keySet() - 키 순회]");
    Set<String> keys = map.keySet();
    for (String key : keys) {
      System.out.println("  " + key + " → " + map.get(key));
    }

    // 8. values() - 값만 순회
    System.out.println("\n[values() - 값 순회]");
    Collection<Integer> values = map.values();
    for (int value : values) {
      System.out.println("  " + value);
    }

    // 9. entrySet() - 키-값 쌍 순회 (가장 효율적)
    System.out.println("\n[entrySet() - 키-값 쌍 순회]");
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      System.out.println("  " + entry.getKey() + " → " + entry.getValue());
    }

    // 10. null 키와 null 값 허용
    System.out.println("\n[null 키/값 허용]");
    map.put(null, 0);
    map.put("empty", null);
    System.out.println("null 키 값: " + map.get(null));
    System.out.println("\"empty\" 값: " + map.get("empty"));

    // 11. clear() / isEmpty()
    map.clear();
    System.out.println("\nclear() 후 isEmpty: " + map.isEmpty());
  }
}

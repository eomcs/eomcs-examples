package com.eomcs.quickstart.collection.exam05;

import java.util.Map;
import java.util.TreeMap;

// TreeMap:
// - java.util 패키지에 소속되어 있다.
// - Map 인터페이스를 구현한다.
// - 내부적으로 레드-블랙 트리(Red-Black Tree)를 사용한다.
// - 키를 자연 순서(오름차순) 또는 지정한 Comparator 순서로 정렬하여 저장한다.
// - 검색·삽입·삭제의 시간 복잡도가 O(log n)이다. (HashMap의 O(1)보다 느림)
// - 키로 null을 허용하지 않는다.
// - 동기화되지 않는다.
//
// HashMap, LinkedHashMap, TreeMap 비교:
//   HashMap        : 순서 미보장, O(1) 검색, null 키 허용
//   LinkedHashMap  : 추가 순서 유지, O(1) 검색, null 키 허용
//   TreeMap        : 키 기준 정렬, O(log n) 검색, null 키 불허
//
// SortedMap / NavigableMap 인터페이스 주요 메서드 (TreeMap이 구현):
//   K firstKey()                      가장 작은 키 반환
//   K lastKey()                       가장 큰 키 반환
//   SortedMap<K,V> headMap(K toKey)   toKey 미만의 항목 반환
//   SortedMap<K,V> tailMap(K fromKey) fromKey 이상의 항목 반환
//   SortedMap<K,V> subMap(K from, K to) from 이상 to 미만의 항목 반환
//   Map.Entry<K,V> firstEntry()       가장 작은 키의 Entry 반환
//   Map.Entry<K,V> lastEntry()        가장 큰 키의 Entry 반환
//   Map.Entry<K,V> floorEntry(K key)  key 이하의 가장 큰 키의 Entry 반환
//   Map.Entry<K,V> ceilingEntry(K key) key 이상의 가장 작은 키의 Entry 반환
//   NavigableMap<K,V> descendingMap() 역순으로 정렬된 Map 반환
//

public class App4 {

  public static void main(String[] args) {

    // 1. 키 자연 순서(오름차순) 정렬
    System.out.println("[TreeMap - 키 자연 순서(오름차순) 정렬]");
    TreeMap<String, Integer> map = new TreeMap<>();
    map.put("banana", 500);
    map.put("apple", 1000);
    map.put("mango", 700);
    map.put("strawberry", 800);
    map.put("cherry", 300);
    System.out.println(map); // 알파벳 오름차순으로 출력

    // 2. firstKey() / lastKey()
    System.out.println("\n[firstKey / lastKey]");
    System.out.println("firstKey(): " + map.firstKey()); // apple
    System.out.println("lastKey():  " + map.lastKey()); // strawberry

    // 3. firstEntry() / lastEntry()
    System.out.println("\n[firstEntry / lastEntry]");
    Map.Entry<String, Integer> first = map.firstEntry();
    Map.Entry<String, Integer> last = map.lastEntry();
    System.out.println("firstEntry(): " + first.getKey() + " → " + first.getValue());
    System.out.println("lastEntry():  " + last.getKey() + " → " + last.getValue());

    // 4. floorEntry() / ceilingEntry() - 범위 탐색
    System.out.println("\n[floorEntry / ceilingEntry]");
    // floorEntry: key 이하의 가장 큰 키의 Entry
    // 즉 "key 보다 작거나 같은 키 중에서 가장 큰 것을 찾아라"
    System.out.println("floorEntry(\"c\"):   " + map.floorEntry("c")); // cherry
    System.out.println("floorEntry(\"b\"):   " + map.floorEntry("b")); // banana
    // ceilingEntry: key 이상의 가장 작은 키의 Entry
    // 즉 "key 보다 크거나 같은 키 중에서 가장 작은 것을 찾아라"
    System.out.println("ceilingEntry(\"c\"): " + map.ceilingEntry("c")); // cherry
    System.out.println("ceilingEntry(\"d\"): " + map.ceilingEntry("d")); // mango

    // 5. headMap() / tailMap() / subMap() - 범위 뷰
    System.out.println("\n[headMap / tailMap / subMap]");
    System.out.println("headMap(\"mango\"):              " + map.headMap("mango")); // mango 미만
    System.out.println("tailMap(\"mango\"):              " + map.tailMap("mango")); // mango 이상
    System.out.println(
        "subMap(\"cherry\", \"mango\"):   " + map.subMap("cherry", "mango")); // cherry 이상 mango 미만

    // 6. descendingMap() - 역순 정렬
    System.out.println("\n[descendingMap() - 역순(내림차순) 정렬]");
    System.out.println(map.descendingMap());

    // 7. 정수 키 TreeMap - 자연 순서
    System.out.println("\n[정수 키 TreeMap - 자연 순서]");
    TreeMap<Integer, String> scoreMap = new TreeMap<>();
    scoreMap.put(85, "홍길동");
    scoreMap.put(92, "임꺽정");
    scoreMap.put(78, "유관순");
    scoreMap.put(95, "이순신");
    System.out.println(scoreMap); // 점수 오름차순

    System.out.println("firstEntry (최저점): " + scoreMap.firstEntry());
    System.out.println("lastEntry  (최고점): " + scoreMap.lastEntry());

    // 9. null 키 불허
    System.out.println("\n[null 키 불허]");
    try {
      map.put(null, 0);
    } catch (NullPointerException e) {
      System.out.println("null 키 → NullPointerException");
    }
  }
}

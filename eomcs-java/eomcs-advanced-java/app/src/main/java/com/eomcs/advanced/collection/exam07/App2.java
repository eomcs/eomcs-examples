package com.eomcs.advanced.collection.exam07;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// ConcurrentHashMap:
// - java.util.concurrent 패키지에 소속되어 있다.
// - Hashtable처럼 스레드 안전하지만, Map 전체를 잠그지 않고 버킷(세그먼트) 단위로 잠근다.
//   → 서로 다른 버킷에 접근하는 스레드는 동시 실행 가능하여 Hashtable보다 훨씬 빠르다.
// - null 키와 null 값을 허용하지 않는다.
// - 단일 메서드 호출(put/get/remove 등)은 원자적으로 실행된다.
// - 복합 연산을 원자적으로 처리하는 전용 메서드를 제공한다:
//     putIfAbsent(key, value)              키가 없을 때만 추가
//     computeIfAbsent(key, mappingFn)      키가 없을 때 함수로 값 생성 후 추가
//     computeIfPresent(key, remappingFn)   키가 있을 때 함수로 값 갱신
//     compute(key, remappingFn)            키 유무와 관계없이 함수로 값 계산·저장
//     merge(key, value, remappingFn)       키가 없으면 value 저장, 있으면 함수로 병합
// - 순회(iterator) 도중 다른 스레드가 수정해도 ConcurrentModificationException이 발생하지 않는다.
//   단, 순회 시작 이후의 수정은 반영될 수도 있고 반영되지 않을 수도 있다.
//
// 실무 선택 기준:
//   단일 스레드        : HashMap
//   멀티스레드(범용)   : ConcurrentHashMap
//   레거시(비권장)     : Hashtable
//

public class App2 {

  public static void main(String[] args) throws InterruptedException {

    // 1. 기본 사용 - HashMap과 동일한 API
    System.out.println("[ConcurrentHashMap - 기본 사용]");
    ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
    map.put("apple", 1000);
    map.put("banana", 500);
    map.put("mango", 700);
    System.out.println("map: " + map);
    System.out.println("apple: " + map.get("apple"));

    // 2. putIfAbsent() - 원자적 조건부 추가
    System.out.println("\n[putIfAbsent() - 원자적 조건부 추가]");
    Integer prev = map.putIfAbsent("banana", 9999); // 이미 있음 → 무시
    System.out.println("putIfAbsent(\"banana\", 9999) → 이전 값: " + prev); // 500
    System.out.println("banana 현재값: " + map.get("banana")); // 500 (변경 없음)

    Integer prev2 = map.putIfAbsent("cherry", 300); // 없음 → 추가
    System.out.println("putIfAbsent(\"cherry\", 300) → 이전 값: " + prev2); // null
    System.out.println("cherry 현재값: " + map.get("cherry")); // 300

    // 3. computeIfAbsent() - 키가 없을 때 함수로 값 생성
    System.out.println("\n[computeIfAbsent() - 없을 때만 계산·저장]");
    map.computeIfAbsent("strawberry", key -> key.length() * 100); // 없음 → 계산
    System.out.println("strawberry: " + map.get("strawberry")); // 1000 (10글자 × 100)
    map.computeIfAbsent("apple", key -> 9999); // 이미 있음 → 무시
    System.out.println("apple(변경 없음): " + map.get("apple")); // 1000

    // 4. computeIfPresent() - 키가 있을 때만 값 갱신
    System.out.println("\n[computeIfPresent() - 있을 때만 갱신]");
    map.computeIfPresent("apple", (key, val) -> val + 200); // 있음 → 갱신
    System.out.println("apple(+200): " + map.get("apple")); // 1200
    map.computeIfPresent("grape", (key, val) -> val + 200); // 없음 → 무시
    System.out.println("grape(없음): " + map.get("grape")); // null

    // 5. compute() - 키 유무와 관계없이 계산
    System.out.println("\n[compute() - 항상 계산·저장]");
    map.compute("mango", (key, val) -> val == null ? 100 : val + 100);
    System.out.println("mango(+100): " + map.get("mango")); // 800
    map.compute("grape", (key, val) -> val == null ? 100 : val + 100);
    System.out.println("grape(신규): " + map.get("grape")); // 100

    // 6. merge() - 키 없으면 value 저장, 있으면 함수로 병합
    System.out.println("\n[merge() - 카운팅 패턴]");
    ConcurrentHashMap<String, Integer> wordCount = new ConcurrentHashMap<>();
    String[] words = {"java", "python", "java", "go", "java", "python"};
    for (String word : words) {
      wordCount.merge(word, 1, Integer::sum); // 없으면 1 저장, 있으면 기존값 + 1
    }
    System.out.println("단어 카운트: " + wordCount);

    // 7. 멀티스레드 동시 카운팅
    System.out.println("\n[멀티스레드 동시 카운팅 - merge() 활용]");
    ConcurrentHashMap<String, Integer> counter = new ConcurrentHashMap<>();
    List<Thread> threads = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      threads.add(new Thread(() -> {
        for (int j = 0; j < 100; j++) {
          counter.merge("count", 1, Integer::sum); // 원자적 증가
        }
      }));
    }
    for (Thread t : threads) t.start();
    for (Thread t : threads) t.join();
    System.out.println("기대값: 1000, 실제값: " + counter.get("count"));

    // 8. 멀티스레드 동시 쓰기 - 데이터 무결성
    System.out.println("\n[멀티스레드 동시 쓰기 - 데이터 무결성]");
    ConcurrentHashMap<Integer, Integer> safeMap = new ConcurrentHashMap<>();
    List<Thread> writeThreads = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      final int start = i * 100;
      writeThreads.add(new Thread(() -> {
        for (int j = start; j < start + 100; j++) {
          safeMap.put(j, j);
        }
      }));
    }
    for (Thread t : writeThreads) t.start();
    for (Thread t : writeThreads) t.join();
    System.out.println("기대 크기: 500, 실제 크기: " + safeMap.size());

    // 9. entrySet() 순회 - ConcurrentModificationException 없음
    System.out.println("\n[순회 중 수정 - ConcurrentModificationException 없음]");
    ConcurrentHashMap<String, Integer> iterMap = new ConcurrentHashMap<>();
    iterMap.put("A", 1);
    iterMap.put("B", 2);
    iterMap.put("C", 3);
    for (Map.Entry<String, Integer> entry : iterMap.entrySet()) {
      System.out.println("  " + entry.getKey() + " → " + entry.getValue());
      iterMap.put("D", 4); // 순회 중 수정해도 예외 없음
    }
    System.out.println("D 추가 후 크기: " + iterMap.size());
  }
}

package com.eomcs.quickstart.collection.exam07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Collections.synchronizedXxx():
// - java.util.Collections 유틸리티 클래스가 제공하는 동기화 래퍼 메서드이다.
// - 기존 컬렉션 인스턴스를 인수로 받아 모든 메서드가 synchronized된 래퍼 객체를 반환한다.
// - 반환된 래퍼를 통해 접근하는 한 개별 메서드 호출은 스레드 안전하다.
// - 단, 복합 연산(check-then-act, put-if-absent 등)은 래퍼만으로 원자성이 보장되지 않는다.
//   복합 연산은 반환된 래퍼 객체를 락으로 synchronized 블록을 직접 사용해야 한다.
// - 순회(iterator) 도중 다른 스레드가 수정하면 ConcurrentModificationException이 발생할 수 있다.
//   순회 전체를 synchronized 블록으로 감싸야 한다.
//
// 주요 팩토리 메서드:
//   Collections.synchronizedList(List<T> list)
//   Collections.synchronizedSet(Set<T> set)
//   Collections.synchronizedMap(Map<K,V> map)
//   Collections.synchronizedSortedMap(SortedMap<K,V> map)
//

public class App {

  public static void main(String[] args) throws InterruptedException {

    // 1. synchronizedList() - 기본 사용
    System.out.println("[synchronizedList() - 기본 사용]");
    List<Integer> syncList = Collections.synchronizedList(new ArrayList<>());
    syncList.add(1);
    syncList.add(2);
    syncList.add(3);
    System.out.println("syncList: " + syncList);
    System.out.println("크기: " + syncList.size());

    // 2. synchronizedMap() - 기본 사용
    System.out.println("\n[synchronizedMap() - 기본 사용]");
    Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
    syncMap.put("apple", 1000);
    syncMap.put("banana", 500);
    System.out.println("syncMap: " + syncMap);

    // 3. 멀티스레드 환경 - 동기화 없이 발생하는 문제 (비동기화 ArrayList)
    System.out.println("\n[비동기화 ArrayList - 동시 쓰기 시 데이터 손실 가능]");
    List<Integer> unsafeList = new ArrayList<>();
    List<Thread> threads = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      final int start = i * 100;
      threads.add(new Thread(() -> {
        for (int j = start; j < start + 100; j++) {
          unsafeList.add(j); // 동기화 없음 → race condition
        }
      }));
    }
    for (Thread t : threads) t.start();
    for (Thread t : threads) t.join();
    System.out.println("기대 크기: 500, 실제 크기: " + unsafeList.size()
        + (unsafeList.size() < 500 ? " ← 데이터 손실 발생!" : " (우연히 정상)"));

    // 4. 멀티스레드 환경 - synchronizedList() 사용
    System.out.println("\n[synchronizedList() - 멀티스레드 동시 쓰기]");
    List<Integer> safeList = Collections.synchronizedList(new ArrayList<>());
    threads.clear();
    for (int i = 0; i < 5; i++) {
      final int start = i * 100;
      threads.add(new Thread(() -> {
        for (int j = start; j < start + 100; j++) {
          safeList.add(j);
        }
      }));
    }
    for (Thread t : threads) t.start();
    for (Thread t : threads) t.join();
    System.out.println("기대 크기: 500, 실제 크기: " + safeList.size());

    // 5. 복합 연산 - synchronized 블록 필요
    System.out.println("\n[복합 연산 - synchronized 블록으로 원자성 보장]");
    Map<String, Integer> countMap = Collections.synchronizedMap(new HashMap<>());
    countMap.put("count", 0);

    List<Thread> countThreads = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      countThreads.add(new Thread(() -> {
        for (int j = 0; j < 100; j++) {
          // get + put 복합 연산 → synchronized 블록으로 원자성 보장
          synchronized (countMap) {
            countMap.put("count", countMap.get("count") + 1);
          }
        }
      }));
    }
    for (Thread t : countThreads) t.start();
    for (Thread t : countThreads) t.join();
    System.out.println("기대값: 1000, 실제값: " + countMap.get("count"));

    // 6. 순회 - synchronized 블록으로 감싸야 안전
    System.out.println("\n[순회 - synchronized 블록으로 감싸기]");
    List<String> iterList = Collections.synchronizedList(new ArrayList<>());
    iterList.add("apple");
    iterList.add("banana");
    iterList.add("cherry");
    synchronized (iterList) { // 순회 전체를 동기화
      for (String item : iterList) {
        System.out.println("  " + item);
      }
    }
  }
}

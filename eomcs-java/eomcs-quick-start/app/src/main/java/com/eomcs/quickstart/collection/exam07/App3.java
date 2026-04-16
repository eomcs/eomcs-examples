package com.eomcs.quickstart.collection.exam07;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

// CopyOnWriteArrayList:
// - java.util.concurrent 패키지에 소속되어 있다.
// - 쓰기(add/set/remove) 연산 시마다 내부 배열 전체를 복사(copy-on-write)한다.
// - 읽기(get/iterator) 연산은 락 없이 현재 스냅샷을 사용하므로 매우 빠르다.
// - 순회(iterator) 도중 다른 스레드가 수정해도 ConcurrentModificationException이 발생하지 않는다.
//   단, iterator는 생성 시점의 스냅샷을 순회하므로 이후 수정은 반영되지 않는다.
// - 쓰기 비용이 O(n)이므로 쓰기가 빈번한 환경에는 적합하지 않다.
// - 읽기가 압도적으로 많고 쓰기가 드문 환경에 최적이다.
//   예: 이벤트 리스너 목록, 설정 값 캐시
//
// CopyOnWriteArraySet:
// - CopyOnWriteArrayList를 내부적으로 사용하는 Set 구현체이다.
// - 중복 허용 안 함. 순서는 추가 순서 유지.
// - 마찬가지로 읽기 중심 + 쓰기 드문 환경에 적합하다.
//

public class App3 {

  public static void main(String[] args) throws InterruptedException {

    // 1. CopyOnWriteArrayList - 기본 사용
    System.out.println("[CopyOnWriteArrayList - 기본 사용]");
    CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>();
    cowList.add("apple");
    cowList.add("banana");
    cowList.add("cherry");
    System.out.println("cowList: " + cowList);
    System.out.println("get(1): " + cowList.get(1));
    cowList.set(1, "mango");
    System.out.println("set(1, mango): " + cowList);
    cowList.remove("cherry");
    System.out.println("remove(cherry): " + cowList);

    // 2. 순회 중 수정 - ConcurrentModificationException 없음
    System.out.println("\n[순회 중 수정 - ConcurrentModificationException 없음]");
    CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(List.of(1, 2, 3, 4, 5));
    for (Integer item : list) {
      System.out.print(item + " ");
      list.add(item + 10); // 순회 중 수정해도 예외 없음 (스냅샷 순회)
    }
    System.out.println();
    System.out.println("순회 후 리스트: " + list); // 원본에는 추가됨

    // 3. 일반 ArrayList - 순회 중 수정 시 ConcurrentModificationException
    System.out.println("\n[일반 ArrayList - 순회 중 수정 → ConcurrentModificationException]");
    List<Integer> arrayList = new ArrayList<>(List.of(1, 2, 3, 4, 5));
    try {
      for (Integer item : arrayList) {
        arrayList.add(item + 10); // ConcurrentModificationException 발생
      }
    } catch (java.util.ConcurrentModificationException e) {
      System.out.println("ConcurrentModificationException 발생!");
    }

    // 4. iterator() - 스냅샷 기반 순회
    System.out.println("\n[iterator() - 스냅샷 기반 순회]");
    CopyOnWriteArrayList<String> snapList = new CopyOnWriteArrayList<>(List.of("A", "B", "C"));
    Iterator<String> it = snapList.iterator(); // 스냅샷 생성 시점: [A, B, C]
    snapList.add("D"); // iterator 생성 이후 수정
    System.out.print("iterator 순회(스냅샷): ");
    while (it.hasNext()) {
      System.out.print(it.next() + " "); // D는 포함되지 않음
    }
    System.out.println();
    System.out.println("현재 리스트: " + snapList); // [A, B, C, D]

    // 5. 멀티스레드 동시 쓰기
    System.out.println("\n[멀티스레드 동시 쓰기]");
    CopyOnWriteArrayList<Integer> safeList = new CopyOnWriteArrayList<>();
    List<Thread> threads = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      final int start = i * 10;
      threads.add(new Thread(() -> {
        for (int j = start; j < start + 10; j++) {
          safeList.add(j);
        }
      }));
    }
    for (Thread t : threads) t.start();
    for (Thread t : threads) t.join();
    System.out.println("기대 크기: 50, 실제 크기: " + safeList.size());

    // 6. 멀티스레드 동시 읽기+쓰기 - 리스너 목록 패턴
    System.out.println("\n[리스너 목록 패턴 - 읽기 중 리스너 추가/제거]");
    CopyOnWriteArrayList<String> listeners = new CopyOnWriteArrayList<>();
    listeners.add("Listener-1");
    listeners.add("Listener-2");
    listeners.add("Listener-3");

    // 이벤트 발생 스레드: 리스너 순회 (읽기)
    Thread eventThread = new Thread(() -> {
      for (String listener : listeners) {
        System.out.println("  이벤트 전달 → " + listener);
        try { Thread.sleep(10); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
      }
    });

    // 리스너 등록/해제 스레드: 수정
    Thread modifyThread = new Thread(() -> {
      listeners.add("Listener-4");
      listeners.remove("Listener-1");
    });

    eventThread.start();
    modifyThread.start();
    eventThread.join();
    modifyThread.join();
    System.out.println("최종 리스너: " + listeners);

    // 7. CopyOnWriteArraySet - 중복 없는 스냅샷 Set
    System.out.println("\n[CopyOnWriteArraySet - 중복 없는 스냅샷 Set]");
    CopyOnWriteArraySet<String> cowSet = new CopyOnWriteArraySet<>();
    cowSet.add("apple");
    cowSet.add("banana");
    cowSet.add("apple"); // 중복 → 무시
    cowSet.add("cherry");
    System.out.println("cowSet: " + cowSet); // [apple, banana, cherry]
    System.out.println("크기: " + cowSet.size()); // 3

    // 순회 중 수정 - ConcurrentModificationException 없음
    for (String item : cowSet) {
      System.out.print(item + " ");
      cowSet.add("new-" + item); // 스냅샷 순회이므로 예외 없음
    }
    System.out.println();
    System.out.println("수정 후 cowSet: " + cowSet);
  }
}

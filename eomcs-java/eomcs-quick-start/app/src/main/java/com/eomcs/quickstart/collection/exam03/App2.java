package com.eomcs.quickstart.collection.exam03;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// ArrayList vs LinkedList - 성능 비교
//
// 어떤 구현체를 선택해야 하는가?
//
// ArrayList를 선택해야 하는 경우:
// - 인덱스로 항목을 자주 조회할 때 (get(index))
// - 항목을 주로 끝에 추가/삭제할 때
// - 메모리를 효율적으로 사용해야 할 때
// - 대부분의 일반적인 상황
//
// LinkedList를 선택해야 하는 경우:
// - 리스트의 맨 앞/중간에 삽입/삭제가 매우 빈번할 때
// - Queue 또는 Deque(양방향 큐)로 사용할 때
//

public class App2 {

  static final int COUNT = 100_000;

  public static void main(String[] args) {

    List<Integer> arrayList = new ArrayList<>();
    List<Integer> linkedList = new LinkedList<>();

    // 1. 끝에 추가 성능 비교
    System.out.println("[끝에 추가 (add) 성능 비교 - 항목 수: " + COUNT + "]");
    long start = System.nanoTime();
    for (int i = 0; i < COUNT; i++) {
      arrayList.add(i);
    }
    System.out.printf("ArrayList  add: %,d ns%n", System.nanoTime() - start);

    start = System.nanoTime();
    for (int i = 0; i < COUNT; i++) {
      linkedList.add(i);
    }
    System.out.printf("LinkedList add: %,d ns%n", System.nanoTime() - start);

    // 2. 인덱스 접근 성능 비교
    System.out.println("\n[인덱스 접근 (get) 성능 비교 - 항목 수: " + COUNT + "]");
    start = System.nanoTime();
    for (int i = 0; i < COUNT; i++) {
      arrayList.get(i);
    }
    System.out.printf("ArrayList  get: %,d ns%n", System.nanoTime() - start);

    start = System.nanoTime();
    for (int i = 0; i < COUNT; i++) {
      linkedList.get(i);
    }
    System.out.printf("LinkedList get: %,d ns%n", System.nanoTime() - start);
    System.out.println("→ ArrayList가 훨씬 빠르다. LinkedList는 노드를 앞에서부터 순회하기 때문이다.");

    // 3. 맨 앞 삽입 성능 비교
    System.out.println("\n[맨 앞 삽입 (add(0, e)) 성능 비교 - 1,000회]");
    int insertCount = 1_000;
    start = System.nanoTime();
    for (int i = 0; i < insertCount; i++) {
      arrayList.add(0, i); // 모든 항목을 오른쪽으로 이동
    }
    System.out.printf("ArrayList  add(0, e): %,d ns%n", System.nanoTime() - start);

    start = System.nanoTime();
    for (int i = 0; i < insertCount; i++) {
      linkedList.add(0, i); // head 노드 참조만 변경
    }
    System.out.printf("LinkedList add(0, e): %,d ns%n", System.nanoTime() - start);
    System.out.println("→ LinkedList가 훨씬 빠르다. ArrayList는 모든 항목을 이동해야 하기 때문이다.");

    // 4. 맨 앞 삭제 성능 비교
    System.out.println("\n[맨 앞 삭제 (remove(0)) 성능 비교 - 1,000회]");
    start = System.nanoTime();
    for (int i = 0; i < insertCount; i++) {
      arrayList.remove(0); // 모든 항목을 왼쪽으로 이동
    }
    System.out.printf("ArrayList  remove(0): %,d ns%n", System.nanoTime() - start);

    start = System.nanoTime();
    for (int i = 0; i < insertCount; i++) {
      linkedList.remove(0); // head 노드 참조만 변경
    }
    System.out.printf("LinkedList remove(0): %,d ns%n", System.nanoTime() - start);
    System.out.println("→ LinkedList가 훨씬 빠르다. ArrayList는 모든 항목을 이동해야 하기 때문이다.");
  }
}

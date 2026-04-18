package com.eomcs.advanced.collection.exam02;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

// Queue 인터페이스:
// - java.util 패키지에 소속되어 있다.
// - Collection을 상속한다.
// - FIFO(First-In-First-Out, 선입선출) 방식으로 항목을 관리한다.
// - 먼저 추가된 항목이 먼저 꺼내진다.
//
// Collection에서 추가된 주요 메서드:
//   boolean offer(E e)   항목을 큐의 끝에 추가. 실패 시 false 반환 (add()는 예외 발생)
//   E poll()             큐의 앞에서 항목을 꺼내고 제거. 비어있으면 null 반환
//   E peek()             큐의 앞 항목을 제거하지 않고 반환. 비어있으면 null 반환
//
// 주요 구현체:
//   LinkedList     : 가장 일반적인 Queue 구현체
//   ArrayDeque     : 배열 기반. LinkedList보다 빠르며 null 불허
//   PriorityQueue  : 우선순위 기반. 자연 순서(오름차순)로 항목을 꺼냄
//

public class App3 {

  public static void main(String[] args) {

    // 1. LinkedList로 Queue 사용 - FIFO
    System.out.println("[LinkedList - FIFO Queue]");
    Queue<String> queue = new LinkedList<>();
    queue.offer("첫 번째");
    queue.offer("두 번째");
    queue.offer("세 번째");
    System.out.println("peek(): " + queue.peek()); // 첫 번째 (제거 안 함)
    System.out.println("크기: " + queue.size());   // 3

    System.out.println("\n[poll()로 순서대로 꺼내기]");
    while (!queue.isEmpty()) {
      System.out.println(queue.poll()); // 첫 번째 → 두 번째 → 세 번째
    }
    System.out.println("비어있음: " + queue.isEmpty()); // true
    System.out.println("poll() (빈 큐): " + queue.poll()); // null

    System.out.println();

    // 2. ArrayDeque - LinkedList보다 빠른 Queue 구현체
    System.out.println("[ArrayDeque - Queue]");
    Queue<Integer> deque = new ArrayDeque<>();
    deque.offer(100);
    deque.offer(200);
    deque.offer(300);
    System.out.println("peek(): " + deque.peek()); // 100
    System.out.println("poll(): " + deque.poll()); // 100
    System.out.println("poll(): " + deque.poll()); // 200
    System.out.println("나머지: " + deque);         // [300]

    System.out.println();

    // 3. PriorityQueue - 우선순위(오름차순) 기반 꺼내기
    System.out.println("[PriorityQueue - 우선순위(오름차순) 기반]");
    Queue<Integer> pq = new PriorityQueue<>();
    pq.offer(30);
    pq.offer(10);
    pq.offer(20);
    System.out.println("peek(): " + pq.peek()); // 10 (가장 작은 값)

    System.out.println("\n[poll()로 오름차순으로 꺼내기]");
    while (!pq.isEmpty()) {
      System.out.println(pq.poll()); // 10 → 20 → 30
    }
  }
}

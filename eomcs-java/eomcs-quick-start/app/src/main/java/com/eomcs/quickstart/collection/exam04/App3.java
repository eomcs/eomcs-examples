package com.eomcs.quickstart.collection.exam04;

import java.util.ArrayDeque;
import java.util.Deque;

// ArrayDeque:
// - java.util 패키지에 소속되어 있다.
// - Deque(Double-Ended Queue) 인터페이스를 구현한다.
// - 내부적으로 순환 배열(circular array)을 사용하여 양쪽 끝에서 O(1)로 추가/삭제한다.
// - null을 저장할 수 없다.
// - 동기화되지 않아 단일 스레드 환경에서 Stack보다 빠르다.
//
// Deque 인터페이스 주요 메서드:
//   void push(E e)      맨 앞에 추가 (스택의 push). addFirst()와 동일
//   E pop()             맨 앞에서 꺼내고 제거. 비어있으면 NoSuchElementException
//   E peek()            맨 앞 항목을 제거하지 않고 반환. 비어있으면 null
//   void addFirst(E e)  맨 앞에 추가
//   void addLast(E e)   맨 뒤에 추가. add()와 동일
//   E removeFirst()     맨 앞에서 꺼내고 제거. 비어있으면 NoSuchElementException
//   E removeLast()      맨 뒤에서 꺼내고 제거. 비어있으면 NoSuchElementException
//   E pollFirst()       맨 앞에서 꺼내고 제거. 비어있으면 null
//   E pollLast()        맨 뒤에서 꺼내고 제거. 비어있으면 null
//   E peekFirst()       맨 앞 항목 조회. 비어있으면 null
//   E peekLast()        맨 뒤 항목 조회. 비어있으면 null
//
// 사용 패턴:
//   스택(LIFO) : push() / pop() / peek()
//   큐(FIFO)   : addLast() / pollFirst() 또는 offer() / poll()
//

public class App3 {

  public static void main(String[] args) {

    // 1. 스택(LIFO)으로 사용 - Stack 클래스의 권장 대안
    System.out.println("[ArrayDeque를 스택(LIFO)으로 사용]");
    Deque<String> stack = new ArrayDeque<>();
    stack.push("첫 번째"); // addFirst()와 동일 - 맨 앞에 추가
    stack.push("두 번째");
    stack.push("세 번째"); // top
    System.out.println("push 후: " + stack); // [세 번째, 두 번째, 첫 번째]

    System.out.println("peek():  " + stack.peek()); // 세 번째 (제거 안 함)
    System.out.println("pop():   " + stack.pop());  // 세 번째
    System.out.println("pop():   " + stack.pop());  // 두 번째
    System.out.println("pop():   " + stack.pop());  // 첫 번째
    System.out.println("빈 스택 peek(): " + stack.peek()); // null (예외 없음)

    System.out.println();

    // 2. 큐(FIFO)로 사용 - LinkedList보다 빠른 Queue 구현체
    System.out.println("[ArrayDeque를 큐(FIFO)로 사용]");
    Deque<String> queue = new ArrayDeque<>();
    queue.addLast("첫 번째"); // offer()와 동일 - 맨 뒤에 추가
    queue.addLast("두 번째");
    queue.addLast("세 번째");
    System.out.println("addLast 후: " + queue); // [첫 번째, 두 번째, 세 번째]

    System.out.println("peekFirst():  " + queue.peekFirst()); // 첫 번째 (제거 안 함)
    System.out.println("pollFirst():  " + queue.pollFirst()); // 첫 번째
    System.out.println("pollFirst():  " + queue.pollFirst()); // 두 번째
    System.out.println("남은 항목:   " + queue);              // [세 번째]
    System.out.println("빈 큐 pollFirst(): " + queue.pollFirst()); // 세 번째
    System.out.println("빈 큐 pollFirst(): " + queue.pollFirst()); // null (예외 없음)

    System.out.println();

    // 3. 양방향 덱(Deque)으로 사용
    System.out.println("[ArrayDeque를 양방향 덱(Deque)으로 사용]");
    Deque<Integer> deque = new ArrayDeque<>();
    deque.addFirst(1); // [1]
    deque.addLast(2);  // [1, 2]
    deque.addFirst(0); // [0, 1, 2]
    deque.addLast(3);  // [0, 1, 2, 3]
    System.out.println("덱 상태: " + deque);

    System.out.println("peekFirst(): " + deque.peekFirst()); // 0
    System.out.println("peekLast():  " + deque.peekLast());  // 3
    System.out.println("pollFirst(): " + deque.pollFirst()); // 0
    System.out.println("pollLast():  " + deque.pollLast());  // 3
    System.out.println("남은 항목:  " + deque);              // [1, 2]

    System.out.println();

    // 4. null 저장 불가
    System.out.println("[null 저장 불가]");
    Deque<String> d = new ArrayDeque<>();
    try {
      d.push(null); // NullPointerException
    } catch (NullPointerException e) {
      System.out.println("null 추가 시 NullPointerException 발생");
    }
  }
}

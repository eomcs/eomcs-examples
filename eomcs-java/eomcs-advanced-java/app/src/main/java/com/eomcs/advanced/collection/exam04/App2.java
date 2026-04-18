package com.eomcs.advanced.collection.exam04;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

// Stack:
// - java.util 패키지에 소속되어 있다.
// - Vector를 상속하므로 List의 모든 메서드를 사용할 수 있다.
// - LIFO(Last-In-First-Out, 후입선출) 방식으로 항목을 관리한다.
// - 마지막에 추가된 항목이 먼저 꺼내진다.
// - Java 1.0부터 제공된 레거시 클래스이다.
//
// Stack 고유 메서드:
//   E push(E item)     항목을 스택의 맨 위에 추가하고 반환
//   E pop()            맨 위 항목을 꺼내고 제거. 비어있으면 EmptyStackException
//   E peek()           맨 위 항목을 제거하지 않고 반환. 비어있으면 EmptyStackException
//   boolean empty()    스택이 비어있으면 true 반환
//   int search(Object) 맨 위에서부터의 거리(1-based)를 반환. 없으면 -1
//
// 상속 구조:
//   Iterable → Collection → List → Vector → Stack
//
// 실무 권장 사항:
// - Stack은 Vector를 상속하기 때문에 스택 의도와 맞지 않는 인덱스 접근 메서드까지
//   외부로 노출된다. 또한 모든 메서드가 synchronized이므로 단일 스레드에서는 비효율적이다.
// - Java 공식 문서는 스택이 필요할 때 Stack 대신 ArrayDeque 사용을 권장한다.
//

public class App2 {

  public static void main(String[] args) {

    // 1. push() / pop() / peek() - 기본 LIFO 동작
    System.out.println("[Stack 기본 LIFO 동작]");
    Stack<String> stack = new Stack<>();
    stack.push("첫 번째");
    stack.push("두 번째");
    stack.push("세 번째");
    System.out.println("push 후: " + stack);

    System.out.println("peek(): " + stack.peek()); // 제거하지 않고 반환
    System.out.println("peek() 후 크기: " + stack.size()); // 그대로 3

    System.out.println("pop(): " + stack.pop()); // 세 번째
    System.out.println("pop(): " + stack.pop()); // 두 번째
    System.out.println("pop(): " + stack.pop()); // 첫 번째
    System.out.println("pop() 후 크기: " + stack.size()); // 0

    // 2. empty() / EmptyStackException
    System.out.println("\n[empty() 확인]");
    System.out.println("empty(): " + stack.empty()); // true
    try {
      stack.pop(); // 빈 스택에서 pop → EmptyStackException
    } catch (Exception e) {
      System.out.println("빈 스택 pop 예외: " + e.getClass().getSimpleName());
    }

    // 3. search() - 맨 위에서부터의 거리(1-based)
    System.out.println("\n[search() - 맨 위에서부터 거리]");
    stack.push("A");
    stack.push("B");
    stack.push("C");
    stack.push("B");
    System.out.println("스택 상태(오른쪽이 top): " + stack);
    System.out.println("search(\"B\"): " + stack.search("B")); // 1 (맨 위 B)
    System.out.println("search(\"C\"): " + stack.search("C")); // 2
    System.out.println("search(\"A\"): " + stack.search("A")); // 4
    System.out.println("search(\"Z\"): " + stack.search("Z")); // -1 (없음)

    // 4. Vector를 상속하므로 인덱스 접근도 가능하지만 사용하지 않는 것이 좋다
    System.out.println("\n[Vector 상속으로 인한 인덱스 접근 (사용 비권장)]");
    System.out.println("get(0): " + stack.get(0)); // 스택 의도에 맞지 않는 접근
    System.out.println("size(): " + stack.size());

    // 5. 권장 대안: ArrayDeque를 스택으로 사용
    System.out.println("\n[권장 대안 - ArrayDeque를 스택으로 사용]");
    Deque<String> dequeStack = new ArrayDeque<>();
    dequeStack.push("첫 번째"); // addFirst()와 동일
    dequeStack.push("두 번째");
    dequeStack.push("세 번째");
    System.out.println("peek(): " + dequeStack.peek()); // 세 번째
    System.out.println("pop():  " + dequeStack.pop());  // 세 번째
    System.out.println("pop():  " + dequeStack.pop());  // 두 번째
    System.out.println("남은 항목: " + dequeStack);
  }
}

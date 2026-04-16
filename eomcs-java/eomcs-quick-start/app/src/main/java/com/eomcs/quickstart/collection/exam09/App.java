package com.eomcs.quickstart.collection.exam09;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.SequencedCollection;

// SequencedCollection<E> (Java 21, JEP 431):
// - java.util 패키지에 소속되어 있다.
// - Collection<E>를 확장한 인터페이스이다.
// - 요소에 명확한 순서(encounter order)가 있는 컬렉션을 표현한다.
// - 첫 번째·마지막 요소에 직접 접근하고, 역순 뷰를 얻는 메서드를 추가한다.
//
// SequencedCollection 주요 메서드:
//   E getFirst()          첫 번째 요소 반환. 비어 있으면 NoSuchElementException
//   E getLast()           마지막 요소 반환. 비어 있으면 NoSuchElementException
//   void addFirst(E e)    맨 앞에 요소 추가
//   void addLast(E e)     맨 뒤에 요소 추가
//   E removeFirst()       첫 번째 요소 제거 후 반환. 비어 있으면 NoSuchElementException
//   E removeLast()        마지막 요소 제거 후 반환. 비어 있으면 NoSuchElementException
//   SequencedCollection<E> reversed()  역순 뷰 반환 (원본의 뷰 - 수정 시 원본에도 반영)
//
// SequencedCollection을 구현하는 주요 클래스:
//   List 계열  : ArrayList, LinkedList, Vector, Stack
//   Deque 계열 : ArrayDeque, LinkedList
//

public class App {

  public static void main(String[] args) {

    // 1. ArrayList - SequencedCollection 메서드 사용
    System.out.println("[ArrayList - SequencedCollection 메서드]");
    List<String> list = new ArrayList<>();
    list.add("banana");
    list.add("cherry");
    list.add("mango");

    System.out.println("list: " + list);
    System.out.println("getFirst(): " + list.getFirst()); // banana
    System.out.println("getLast():  " + list.getLast());  // mango

    list.addFirst("apple");      // 맨 앞에 추가
    list.addLast("strawberry");  // 맨 뒤에 추가
    System.out.println("addFirst(apple), addLast(strawberry): " + list);

    String first = list.removeFirst(); // 첫 번째 제거
    String last  = list.removeLast();  // 마지막 제거
    System.out.println("removeFirst(): " + first + ", removeLast(): " + last);
    System.out.println("제거 후 list: " + list);

    // 2. reversed() - 역순 뷰
    System.out.println("\n[reversed() - 역순 뷰]");
    List<String> original = new ArrayList<>(List.of("A", "B", "C", "D", "E"));
    System.out.println("original: " + original);

    List<String> reversed = original.reversed();
    System.out.println("reversed: " + reversed); // [E, D, C, B, A]

    // reversed()는 원본의 뷰 - 원본 수정 시 함께 반영
    original.add("F");
    System.out.println("원본 add(F) 후 reversed: " + reversed); // [F, E, D, C, B, A]

    // reversed() 뷰에서의 addFirst/addLast는 원본의 반대 방향
    reversed.addFirst("Z"); // reversed 뷰의 맨 앞 = 원본의 맨 뒤
    System.out.println("reversed.addFirst(Z) 후 original: " + original); // [..., Z]

    // 3. SequencedCollection 타입으로 다형성
    System.out.println("\n[SequencedCollection 타입으로 다형성]");
    SequencedCollection<Integer> sc = new ArrayList<>(List.of(10, 20, 30, 40, 50));
    System.out.println("getFirst(): " + sc.getFirst()); // 10
    System.out.println("getLast():  " + sc.getLast());  // 50
    sc.addFirst(5);
    sc.addLast(55);
    System.out.println("addFirst(5), addLast(55): " + sc);
    System.out.println("reversed(): " + sc.reversed());

    // 4. LinkedList - List이자 Deque (SequencedCollection 구현)
    System.out.println("\n[LinkedList - SequencedCollection 메서드]");
    LinkedList<String> linkedList = new LinkedList<>(List.of("X", "Y", "Z"));
    System.out.println("getFirst(): " + linkedList.getFirst());
    System.out.println("getLast():  " + linkedList.getLast());
    linkedList.addFirst("W");
    linkedList.addLast("V");
    System.out.println("addFirst(W), addLast(V): " + linkedList);
    System.out.println("reversed(): " + linkedList.reversed());

    // 5. ArrayDeque - SequencedCollection 구현
    System.out.println("\n[ArrayDeque - SequencedCollection 메서드]");
    Deque<String> deque = new ArrayDeque<>(List.of("1st", "2nd", "3rd"));
    System.out.println("deque: " + deque);
    System.out.println("getFirst(): " + deque.getFirst());
    System.out.println("getLast():  " + deque.getLast());
    deque.addFirst("0th");
    deque.addLast("4th");
    System.out.println("addFirst(0th), addLast(4th): " + deque);
    System.out.println("removeFirst(): " + deque.removeFirst());
    System.out.println("removeLast():  " + deque.removeLast());
    System.out.println("남은 deque: " + deque);
    System.out.println("reversed(): " + deque.reversed());

    // 6. 빈 컬렉션에서 getFirst() / getLast() → NoSuchElementException
    System.out.println("\n[빈 컬렉션 - NoSuchElementException]");
    List<String> empty = new ArrayList<>();
    try {
      empty.getFirst();
    } catch (java.util.NoSuchElementException e) {
      System.out.println("getFirst() on empty → NoSuchElementException");
    }
    try {
      empty.getLast();
    } catch (java.util.NoSuchElementException e) {
      System.out.println("getLast() on empty → NoSuchElementException");
    }

    // 7. Java 21 이전 vs 이후 비교
    System.out.println("\n[Java 21 이전 vs 이후 비교]");
    List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5));

    // Java 21 이전: 첫/마지막 요소 접근
    int oldFirst = numbers.get(0);               // 인덱스 사용
    int oldLast  = numbers.get(numbers.size() - 1); // size()-1 사용
    System.out.println("이전 방식 - first: " + oldFirst + ", last: " + oldLast);

    // Java 21 이후: getFirst() / getLast()
    int newFirst = numbers.getFirst();
    int newLast  = numbers.getLast();
    System.out.println("이후 방식 - first: " + newFirst + ", last: " + newLast);
  }
}

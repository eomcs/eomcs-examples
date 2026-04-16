package com.eomcs.quickstart.collection.exam09;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.SequencedSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

// SequencedSet<E> (Java 21):
// - SequencedCollection<E>와 Set<E>를 모두 확장한 인터페이스이다.
// - 순서가 있고 중복을 허용하지 않는 Set을 표현한다.
// - reversed()가 SequencedSet<E>를 반환한다. (SequencedCollection과의 차이)
//
// SequencedSet을 구현하는 주요 클래스:
//   LinkedHashSet : 추가 순서 유지
//   TreeSet       : 키 기준 정렬 순서 유지
//   SortedSet     : TreeSet의 상위 인터페이스
//
// SequencedSet 주요 메서드 (SequencedCollection 상속):
//   E getFirst()        첫 번째 요소 반환
//   E getLast()         마지막 요소 반환
//   void addFirst(E e)  맨 앞에 요소 추가 (LinkedHashSet: 이미 있으면 맨 앞으로 이동)
//   void addLast(E e)   맨 뒤에 요소 추가 (LinkedHashSet: 이미 있으면 맨 뒤로 이동)
//   E removeFirst()     첫 번째 요소 제거 후 반환
//   E removeLast()      마지막 요소 제거 후 반환
//   SequencedSet<E> reversed()  역순 뷰 반환
//

public class App2 {

  public static void main(String[] args) {

    // 1. LinkedHashSet - 추가 순서 유지 + SequencedSet
    System.out.println("[LinkedHashSet - SequencedSet 메서드]");
    LinkedHashSet<String> lhs = new LinkedHashSet<>();
    lhs.add("banana");
    lhs.add("cherry");
    lhs.add("mango");
    lhs.add("apple");
    System.out.println("lhs: " + lhs); // [banana, cherry, mango, apple]

    System.out.println("getFirst(): " + lhs.getFirst()); // banana
    System.out.println("getLast():  " + lhs.getLast());  // apple

    lhs.addFirst("aaa"); // 맨 앞에 추가
    lhs.addLast("zzz");  // 맨 뒤에 추가
    System.out.println("addFirst(aaa), addLast(zzz): " + lhs);

    String first = lhs.removeFirst();
    String last  = lhs.removeLast();
    System.out.println("removeFirst(): " + first + ", removeLast(): " + last);
    System.out.println("제거 후 lhs: " + lhs);

    // 2. LinkedHashSet.addFirst() / addLast() - 이미 있는 요소는 위치 이동
    System.out.println("\n[LinkedHashSet - addFirst/addLast 중복 요소 위치 이동]");
    LinkedHashSet<String> set = new LinkedHashSet<>();
    set.add("A");
    set.add("B");
    set.add("C");
    set.add("D");
    System.out.println("초기: " + set); // [A, B, C, D]

    set.addFirst("C"); // 이미 있는 C → 맨 앞으로 이동
    System.out.println("addFirst(C): " + set); // [C, A, B, D]

    set.addLast("A"); // 이미 있는 A → 맨 뒤로 이동
    System.out.println("addLast(A): " + set); // [C, B, D, A]

    // 3. LinkedHashSet.reversed() - 역순 뷰
    System.out.println("\n[LinkedHashSet - reversed() 역순 뷰]");
    LinkedHashSet<Integer> nums = new LinkedHashSet<>();
    nums.add(10);
    nums.add(30);
    nums.add(20);
    nums.add(50);
    nums.add(40);
    System.out.println("원본: " + nums);                // [10, 30, 20, 50, 40]
    System.out.println("reversed(): " + nums.reversed()); // [40, 50, 20, 30, 10]

    // reversed()는 원본의 뷰 - 원본 수정 시 함께 반영
    SequencedSet<Integer> rev = nums.reversed();
    nums.add(60);
    System.out.println("원본 add(60) 후 reversed: " + rev); // 60이 반영됨

    // 4. SequencedSet 타입으로 다형성
    System.out.println("\n[SequencedSet 타입으로 다형성 - LinkedHashSet / TreeSet]");
    SequencedSet<String> ss1 = new LinkedHashSet<>();
    ss1.add("banana");
    ss1.add("apple");
    ss1.add("cherry");
    System.out.println("LinkedHashSet getFirst(): " + ss1.getFirst()); // banana (추가 순서)
    System.out.println("LinkedHashSet getLast():  " + ss1.getLast());  // cherry

    SequencedSet<String> ss2 = new TreeSet<>();
    ss2.add("banana");
    ss2.add("apple");
    ss2.add("cherry");
    System.out.println("TreeSet     getFirst(): " + ss2.getFirst()); // apple (정렬 순서)
    System.out.println("TreeSet     getLast():  " + ss2.getLast());  // cherry

    // 5. TreeSet - 정렬 순서 + SequencedSet
    System.out.println("\n[TreeSet - SequencedSet 메서드]");
    TreeSet<Integer> treeSet = new TreeSet<>();
    treeSet.add(50);
    treeSet.add(20);
    treeSet.add(80);
    treeSet.add(10);
    treeSet.add(60);
    System.out.println("treeSet (정렬): " + treeSet); // [10, 20, 50, 60, 80]

    System.out.println("getFirst(): " + treeSet.getFirst()); // 10 (최솟값)
    System.out.println("getLast():  " + treeSet.getLast());  // 80 (최댓값)
    System.out.println("removeFirst(): " + treeSet.removeFirst()); // 10 제거
    System.out.println("removeLast():  " + treeSet.removeLast());  // 80 제거
    System.out.println("제거 후 treeSet: " + treeSet);
    System.out.println("reversed(): " + treeSet.reversed()); // [60, 50, 20]

    // 6. TreeSet - Comparator와 함께 (역순 정렬)
    System.out.println("\n[TreeSet + Comparator - reversed() 비교]");
    TreeSet<Integer> descSet = new TreeSet<>(Comparator.reverseOrder());
    descSet.add(50);
    descSet.add(20);
    descSet.add(80);
    descSet.add(10);
    System.out.println("내림차순 TreeSet: " + descSet);          // [80, 50, 20, 10]
    System.out.println("getFirst(): " + descSet.getFirst());    // 80 (정렬 기준 첫 번째)
    System.out.println("getLast():  " + descSet.getLast());     // 10
    System.out.println("reversed(): " + descSet.reversed());    // [10, 20, 50, 80]

    // 7. SortedSet - SequencedSet 상위 인터페이스
    System.out.println("\n[SortedSet - SequencedSet 상위 인터페이스]");
    SortedSet<String> sortedSet = new TreeSet<>(Set.of("dog", "cat", "bird", "ant"));
    System.out.println("sortedSet: " + sortedSet);
    System.out.println("getFirst(): " + sortedSet.getFirst()); // ant (알파벳 첫 번째)
    System.out.println("getLast():  " + sortedSet.getLast());  // dog
  }
}

package com.eomcs.quickstart.collection.exam03;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// ArrayList vs LinkedList
//
// ArrayList:
// - 내부적으로 배열(Object[])을 사용하여 항목을 관리한다.
// - 인덱스로 항목에 O(1) 시간에 바로 접근할 수 있다.
// - 중간 삽입/삭제 시 뒤쪽 항목을 전부 이동해야 하므로 O(n) 시간이 걸린다.
// - 배열이 꽉 차면 더 큰 배열을 새로 만들어 복사한다(동적 크기 조정).
//
// LinkedList:
// - 내부적으로 이중 연결 리스트(doubly linked list)를 사용한다.
// - 각 노드는 이전/다음 노드의 참조를 가진다.
// - 인덱스로 접근하려면 앞에서부터 순서대로 따라가야 하므로 O(n) 시간이 걸린다.
// - 중간 삽입/삭제는 노드 참조만 바꾸면 되므로 위치를 찾은 후 O(1) 시간에 가능하다.
// - Queue/Deque 인터페이스도 구현하므로 양쪽 끝 조작에 유리하다.
//
//             | ArrayList  | LinkedList |
// ------------|------------|------------|
// 인덱스 접근 | O(1)       | O(n)       |
// 끝에 추가   | O(1)*      | O(1)       |
// 중간 삽입   | O(n)       | O(n)**     |
// 중간 삭제   | O(n)       | O(n)**     |
// 메모리      | 효율적     | 노드 오버헤드 있음 |
//
// * 배열 확장이 필요하면 O(n), 평균적으로 O(1)
// ** 위치를 찾는 데 O(n), 참조 변경은 O(1)
//

public class App {

  public static void main(String[] args) {

    // 1. 기본 사용법은 동일하다 (List 인터페이스를 공통으로 구현)
    System.out.println("[기본 사용법 - ArrayList와 LinkedList는 List 인터페이스를 공통으로 구현]");
    List<String> arrayList = new ArrayList<>();
    List<String> linkedList = new LinkedList<>();

    arrayList.add("사과"); arrayList.add("바나나"); arrayList.add("딸기");
    linkedList.add("사과"); linkedList.add("바나나"); linkedList.add("딸기");
    System.out.println("ArrayList:  " + arrayList);
    System.out.println("LinkedList: " + linkedList);

    // 2. 인덱스 접근 - ArrayList가 유리
    System.out.println("\n[인덱스 접근 - ArrayList O(1) vs LinkedList O(n)]");
    // ArrayList: 배열의 인덱스로 바로 접근
    System.out.println("ArrayList  get(1): " + arrayList.get(1));
    // LinkedList: 앞에서부터 노드를 하나씩 따라감
    System.out.println("LinkedList get(1): " + linkedList.get(1));

    // 3. 중간 삽입 - 동작 방식이 다르다
    System.out.println("\n[중간 삽입 - ArrayList는 항목 이동, LinkedList는 참조 변경]");
    arrayList.add(1, "망고");  // 인덱스 1 이후 항목을 전부 오른쪽으로 이동
    linkedList.add(1, "망고"); // 이전/다음 노드 참조만 변경
    System.out.println("ArrayList:  " + arrayList);
    System.out.println("LinkedList: " + linkedList);

    // 4. 중간 삭제 - 동작 방식이 다르다
    System.out.println("\n[중간 삭제 - ArrayList는 항목 이동, LinkedList는 참조 변경]");
    arrayList.remove(1);  // 인덱스 1 이후 항목을 전부 왼쪽으로 이동
    linkedList.remove(1); // 이전/다음 노드 참조만 변경
    System.out.println("ArrayList:  " + arrayList);
    System.out.println("LinkedList: " + linkedList);

    // 5. LinkedList는 Queue/Deque로도 사용할 수 있다
    System.out.println("\n[LinkedList - Queue처럼 양쪽 끝 조작]");
    LinkedList<String> deque = new LinkedList<>();
    deque.addFirst("앞에 추가");
    deque.addLast("뒤에 추가");
    deque.addFirst("맨 앞에 추가");
    System.out.println(deque);
    System.out.println("peekFirst(): " + deque.peekFirst());
    System.out.println("peekLast():  " + deque.peekLast());
    System.out.println("pollFirst(): " + deque.pollFirst());
    System.out.println("남은 항목:  " + deque);
  }
}

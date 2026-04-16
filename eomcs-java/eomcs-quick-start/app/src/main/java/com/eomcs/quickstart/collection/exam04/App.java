package com.eomcs.quickstart.collection.exam04;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

// Vector:
// - java.util 패키지에 소속되어 있다.
// - ArrayList와 마찬가지로 내부적으로 배열을 사용하여 항목을 관리한다.
// - List 인터페이스를 구현하므로 ArrayList와 사용 방법이 동일하다.
// - Java 1.0부터 제공된 레거시 클래스이다.
//
// ArrayList와의 차이점:
// - Vector의 모든 메서드는 synchronized(동기화)되어 있어 멀티스레드 환경에서 안전하다.
// - 단일 스레드 환경에서는 동기화 비용으로 인해 ArrayList보다 느리다.
// - 초기 용량이 꽉 차면 배열을 현재 크기의 2배로 늘린다.
//   (ArrayList는 1.5배로 늘린다.)
//
// 실무 권장 사항:
// - 단일 스레드 환경이라면 ArrayList를 사용한다.
// - 멀티스레드 환경이라면 Vector 대신 Collections.synchronizedList() 또는
//   CopyOnWriteArrayList를 사용한다.
// - Vector는 하위 호환성을 위해 유지되고 있으므로 신규 코드에서는 사용하지 않는다.
//

public class App {

  public static void main(String[] args) {

    // 1. 기본 사용법 - ArrayList와 동일하다
    System.out.println("[Vector 기본 사용법]");
    Vector<String> vector = new Vector<>();
    vector.add("사과");
    vector.add("바나나");
    vector.add("딸기");
    System.out.println("크기: " + vector.size());
    System.out.println(vector);

    // 2. 인덱스 접근
    System.out.println("\n[인덱스 접근]");
    System.out.println("get(1): " + vector.get(1));
    vector.set(1, "포도");
    System.out.println("set(1, \"포도\") 후: " + vector);

    // 3. Vector 고유 메서드 (레거시)
    // - addElement(), elementAt(), removeElement() 등은 Java 1.0 시절의 메서드이다.
    // - List 인터페이스 메서드(add, get, remove)와 기능이 같으므로 레거시 메서드는 사용하지 않는다.
    System.out.println("\n[Vector 레거시 메서드]");
    vector.addElement("망고");           // add()와 동일
    System.out.println("elementAt(0): " + vector.elementAt(0)); // get()과 동일
    System.out.println("firstElement(): " + vector.firstElement());
    System.out.println("lastElement():  " + vector.lastElement());
    vector.removeElement("망고");        // remove(Object)와 동일
    System.out.println("removeElement(\"망고\") 후: " + vector);

    // 4. 초기 용량 지정
    System.out.println("\n[초기 용량 지정]");
    Vector<Integer> v2 = new Vector<>(5); // 초기 용량 5
    for (int i = 1; i <= 5; i++) {
      v2.add(i * 10);
    }
    System.out.println("capacity()=5일 때: " + v2);
    v2.add(60); // 용량 초과 → 내부 배열을 2배(10)로 확장
    System.out.println("항목 추가 후: " + v2);
    System.out.println("현재 capacity: " + v2.capacity()); // 10

    // 5. List 인터페이스를 통한 다형성 활용
    System.out.println("\n[List 타입으로 선언 - ArrayList와 교체 가능]");
    List<String> list1 = new Vector<>();
    List<String> list2 = new ArrayList<>();
    list1.add("A"); list1.add("B"); list1.add("C");
    list2.add("A"); list2.add("B"); list2.add("C");
    System.out.println("Vector   as List: " + list1);
    System.out.println("ArrayList as List: " + list2);
  }
}

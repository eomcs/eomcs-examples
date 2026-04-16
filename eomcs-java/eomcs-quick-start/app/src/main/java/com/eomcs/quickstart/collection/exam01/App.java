package com.eomcs.quickstart.collection.exam01;

import java.util.Iterator;

// Iterable 인터페이스:
// - java.lang 패키지에 소속되어 있어 import 없이 사용 가능하다.
// - for-each 문(향상된 for 문)에서 사용할 수 있는 객체의 규격을 정의한다.
// - iterator() 메서드 한 개를 갖는다.
//   => Iterator<T> iterator();
//
// Iterator 인터페이스:
// - java.util 패키지에 소속되어 있다.
// - 컬렉션의 각 항목을 순서대로 꺼내는 역할을 담당한다.
// - 세 개의 추상 메서드를 가진다.
//   => boolean hasNext() : 꺼낼 항목이 남아있으면 true 반환
//   => T next()          : 다음 항목을 반환하고 커서를 이동
//   => default void remove() : next()로 마지막에 반환한 항목을 제거 (선택적 구현)
//

// 예제: 정수 배열을 보관하는 커스텀 컬렉션
// - Iterable<Integer>를 구현하면 for-each 문으로 순회할 수 있다.
class IntList implements Iterable<Integer> {

  private int[] data;
  private int size;

  public IntList(int capacity) {
    data = new int[capacity];
  }

  // 항목 추가
  public void add(int value) {
    if (size < data.length) {
      data[size++] = value;
    }
  }

  // Iterable 인터페이스의 iterator() 구현
  // - 이 메서드가 반환하는 Iterator 객체를 이용해 for-each 문이 동작한다.
  @Override
  public Iterator<Integer> iterator() {
    // 익명 클래스로 Iterator를 구현한다.
    return new Iterator<Integer>() {
      int cursor = 0; // 현재 순회 위치

      // 아직 꺼낼 항목이 남아있는지 확인
      @Override
      public boolean hasNext() {
        return cursor < size;
      }

      // 현재 위치의 항목을 반환하고 커서를 다음으로 이동
      @Override
      public Integer next() {
        return data[cursor++];
      }
    };
  }
}

public class App {

  public static void main(String[] args) {

    IntList list = new IntList(5);
    list.add(10);
    list.add(20);
    list.add(30);
    list.add(40);
    list.add(50);

    System.out.println("[Iterator를 직접 사용하여 순회]");
    // iterator()로 Iterator 객체를 꺼낸 뒤 hasNext()/next()로 순회한다.
    Iterator<Integer> it = list.iterator();
    while (it.hasNext()) {
      int value = it.next();
      System.out.println(value);
    }

    System.out.println();

    System.out.println("[for-each 문으로 순회]");
    // Iterable을 구현한 객체는 for-each 문을 사용할 수 있다.
    // 컴파일러가 아래 코드를 내부적으로 iterator()/hasNext()/next() 호출하는 while 문으로 변환한다.
    for (int value : list) {
      System.out.println(value);
    }
  }
}

// 중첩 클래스 사용 예 - 패키지 멤버 클래스가 불편한 경우
package com.eomcs.oop.ex11.h.before;

public class LinkedList {

  public static final int FORWARD = 1;
  public static final int REVERSE = 2;

  protected Node head;
  protected Node tail;
  protected int size;

  public LinkedList() {
    head = new Node();
    tail = head;
    size = 0;
  }

  public void add(Object value) {
    tail.value = value;
    Node node = new Node();
    tail.next = node;
    node.prev = tail;
    tail = node;
    size++;
  }

  public int size() {
    return size;
  }

  public Object get(int index) {
    if (index < 0 || index >= size)
      return null;
    Node cursor = head;
    for (int i = 1; i <= index; i++) {
      cursor = cursor.next;
    }
    return cursor.value;
  }

  public Object[] toArray(int direction) {

    // Array 클래스는 LinkedList 인스턴스에 들어 있는 값을 사용하여,
    // 배열을 만들어 주는 도우미 클래스이다.
    //
    // Array의 도움을 받으려면 다음과 같이 항상 Array 객체에게 LinkedList 객체를 넘겨야 한다.
    Array helper = new Array(this); // <== LinkedList 객체 주소를 넘긴다.

    if (direction == FORWARD) {
      // 그래야만 Array 객체는 생성자에서 넘겨 받은 LinkedList의 데이터를 꺼내
      // 배열로 만든다.
      return helper.copy();
    } else {
      return helper.reverseCopy();
    }
  }

  public Object set(int index, Object value) {
    if (index < 0 || index >= size)
      return null;
    Node cursor = head;
    for (int i = 1; i <= index; i++) {
      cursor = cursor.next;
    }
    Object old = cursor.value;
    cursor.value = value;
    return old;
  }

  public int insert(int index, Object value) {
    if (index < 0 || index >= size)
      return -1;
    Node node = new Node(value);
    Node cursor = head;
    for (int i = 1; i <= index; i++) {
      cursor = cursor.next;
    }
    node.next = cursor;
    node.prev = cursor.prev;
    cursor.prev = node;
    if (node.prev != null) {
      node.prev.next = node;
    } else {
      head = node;
    }
    size++;
    return 0;
  }

  public Object remove(int index) {
    if (index < 0 || index >= size)
      return null;
    Node cursor = head;
    for (int i = 1; i <= index; i++) {
      cursor = cursor.next;
    }
    if (cursor.prev != null) {
      cursor.prev.next = cursor.next;
    } else {
      head = cursor.next;
    }
    cursor.next.prev = cursor.prev;
    Object old = cursor.value;
    cursor.value = null;
    cursor.prev = null;
    cursor.next = null;
    size--;
    return old;
  }
}
















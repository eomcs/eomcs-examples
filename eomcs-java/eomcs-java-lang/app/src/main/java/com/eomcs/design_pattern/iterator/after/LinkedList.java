// 제네릭 적용하기
package com.eomcs.design_pattern.iterator.after;

// LinkedList에 보관되는 값의 타입을 E 라고 가정한다.
// => E 타입이라고 가정하고 코드를 작성한다.
// => E가 무슨 타입인지는 LinkedList를 사용할 때 결정된다.
//
public class LinkedList<E> {
  
  protected Node<E> head;
  protected Node<E> tail;
  protected int size;
  
  public LinkedList() {
    head = new Node<>();
    tail = head;
    size = 0;
  }
  
  // LinkedList는 add() 할 때 마다 노드를 만들어 값을 저장하기 때문에 
  // ArrayList 처럼 한 번에 큰 메모리를 준비할 필요가 없다.
  // => 그러나 값 이외에 다음 노드와 이전 노드의 주소를 담기 위해 추가로 메모리를 사용한다.
  //
  public void add(E value) {
    tail.value = value;
    
    // 새 노드를 준비한다.
    Node<E> node = new Node<>();
    
    // 마지막 노드의 다음으로 새 노드를 가리키게 한다.
    tail.next = node;
    
    // 새 노드의 이전으로 마지막 노드를 가리키게 한다.
    node.prev = tail;
    
    // tail이 새로 추가된 노드를 가리키게 한다.
    tail = node;
    
    // 항목 개수를 증가시킨다.
    size++;
  }
  
  public int size() {
    return size;
  }
  
  // ArrayList와 달리 해당 인덱스를 찾아 가려면 링크를 따라가야 하기 때문에 
  // 조회 속도가 느리다.
  public E get(int index) {
    if (index < 0 || index >= size)
      return null;
    
    Node<E> cursor = head;
    
    // 해당 인덱스로 이동한다.
    for (int i = 1; i <= index; i++) {
      cursor = cursor.next;
    }
    
    // cursor가 가리키는 노드의 주소를 리턴?
    // => 노드의 값을 리턴
    return cursor.value;
  }
  
  public Object[] toArray() {
    Object[] arr = new Object[size()];
    
    Node<E> cursor = head;
    
    int i = 0; 
    while (cursor != tail) {
      arr[i++] = cursor.value;
      cursor = cursor.next;
    }
    
    return arr;
  }
  
  public E set(int index, E value) {
    if (index < 0 || index >= size) 
      return null;
    
    Node<E> cursor = head;
    
    // 교체할 값이 들어 있는 노드로 이동한다.
    for (int i = 1; i <= index; i++) {
      cursor = cursor.next;
    }
    
    // 변경 전에 이전 값을 보관한다.
    E old = cursor.value;
    
    // 값을 변경한다.
    cursor.value = value;
    
    // 이전 값을 리턴한다. 쓰든 안쓰든 호출하는 사람이 알아서 할 일이다.
    // 다만 변경 전 값을 활용할 경우를 대비해 리턴하는 것이다.
    return old;
  }
  
  // 값을 삽입하는 경우에는 ArrayList 방식 보다 효율적이다.
  // 사입 위치에 있는 노드를 찾은 후에 새 노드를 앞, 뒤 노드에 연결하면 된다.
  public int insert(int index, E value) {
    if (index < 0 || index >= size)
      return -1;
    
    // 새 노드를 만들어 값을 담는다.
    Node<E> node = new Node<>(value);
    
    // 삽입할 위치에 있는 원래 노드를 찾는다.
    Node<E> cursor = head;
    for (int i = 1; i <= index; i++) {
      cursor = cursor.next;
    }
    
    // 새 노드가 찾은 노드를 가리키게 한다.
    node.next = cursor;
    
    // 찾은 노드의 이전 노드 주소를 새 노드의 이전 노드 주소로 설정한다.
    node.prev = cursor.prev;
    
    // 찾은 노드의 이전 노드로 새 노드를 가리키게 한다.
    cursor.prev = node;
    
    if (node.prev != null) {
      // 이전 노드의 다음 노드로 새 노드를 가리키게 한다.
      node.prev.next = node;
    } else {
      // 맨 앞에 노드를 추가할 때는 head를 변경해야 한다.
      head = node;
    }
    
    // 크기를 늘린다.
    size++;
    
    return 0;
  }
  
  public E remove(int index) {
    if (index < 0 || index >= size)
      return null;
    
    // index 위치에 있는 노드를 찾는다.
    Node<E> cursor = head;
    for (int i = 1; i <= index; i++) {
      cursor = cursor.next;
    }
    
    if (cursor.prev != null) {
      // 찾은 노드의 이전 노드가 다음 노드를 가리키게 한다.
      cursor.prev.next = cursor.next;
    } else {
      // 맨 처음 노드를 삭제할 때
      head = cursor.next;
    }
    
    // 찾은 노드의 다음 노드가 이전 노드를 가리키게 한다.
    cursor.next.prev = cursor.prev;
    
    // JVM(Garbage Collection)이 가비지를 효과적으로 계산할 수 있도록 
    // 가비지가 된 객체는 다른 객체를 가리키지 않도록 한다.
    E old = cursor.value;
    cursor.value = null;
    cursor.prev = null;
    cursor.next = null;
    
    // 크기를 줄인다.
    size--;
    
    // 호출한 쪽에서 필요하면 사용하라고 삭제된 값을 리턴해 준다.
    return old;
  }
  
  // Node가 다루는 값의 타입을 제네릭(generic)으로 선언한다.
  // => 즉 Node가 다루는 데이터의 타입을 E라고 명명하고 코드를 작성한다.
  // => Node 클래스를 사용하는 시점에 E가 무슨 타입인지 결정될 것이다.
  private static class Node<E> {
    
    E value;
    Node<E> prev;
    Node<E> next;
    
    Node() {
    }
    
    Node(E value) {
      this.value = value;
    }
  }
  
  // 자신이 보유한 데이터를 대신 꺼내주는 일을 하는 객체를 리턴한다.
  public Iterator<E> iterator() {
    return new Iterator<E>() {
      // 이 클래스는 LinkedList에서 값을 꺼내주는 일을 전문적으로 한다.
      // => 이런 일을 하는 객체를 "Iterator"라 부른다.
      //
      int index = 0;
      
      @Override
      public boolean hasNext() {
        return index < size();
      }

      @Override
      public E next() {
        return (E) get(index++);
      }
    };
  }
  
  
}
















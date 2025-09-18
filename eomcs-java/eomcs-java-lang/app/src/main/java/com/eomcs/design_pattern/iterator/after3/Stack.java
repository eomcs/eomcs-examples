package com.eomcs.design_pattern.iterator.after3;

public class Stack<E> {

  public static final int DEFAULT_SIZE = 5;

  Object[] list;
  int size;

  public Stack() {
    list = new Object[DEFAULT_SIZE];
  }

  public void push(E value) {
    if (size == list.length) {
      Object[] arr = new Object[list.length + (list.length >> 1)];
      for (int i = 0; i < list.length; i++) {
        arr[i] = list[i];
      }
      list = arr;
    }

    list[size++] = value;
  }

  @SuppressWarnings("unchecked")
  public E pop() {
    if (size == 0)
      return null;

    return (E) list[--size];
  }

  public boolean empty() {
    return size == 0;
  }

  public int size() {
    return this.size;
  }

  // Iterator 구현체를 제공한다.
  public Iterator<E> iterator() {
    return new StackIterator<E>();
  }

  // 논스태틱 중첩 클래스로 정의하면 
  // 바깥 클래스의 인스턴스 주소를 받는 필드가 자동으로 추가된다.
  // 또한 바깥 클래스의 인스턴스 주소를 받는 파라미터가 가각의 생성자에 자동으로 추가된다.
  // 
  class StackIterator<T> implements Iterator<T> {
    @Override
    public boolean hasNext() {
      return !Stack.this.empty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T next() {
      return (T) Stack.this.pop();
    }
  }
}











package com.eomcs.design_pattern.iterator.after;

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
  
  // 자신이 보유한 데이터를 대신 꺼내주는 일을 하는 객체를 리턴한다.
  public Iterator<E> iterator() {
    return new Iterator<E>() {
      // 이 클래스는 Stack의 값을 꺼내는 일을 전문적으로 한다.
      // => 이런 일을 하는 객체를 "Iterator"라 부른다.
      //
      int index = 0;
      
      @Override
      public boolean hasNext() {
        return index < size();
      }

      @SuppressWarnings("unchecked")
      @Override
      public E next() {
        int lastIndex = size - 1;
        return (E) list[lastIndex - (index++)];
      }
    };
  }
}







package com.eomcs.design_pattern.iterator.after1;

// ListList에서 데이터를 꺼내줄 객체
//
public class LinkedListIterator<E> implements Iterator<E> {

  LinkedList<E> list;
  int index = 0;

  public LinkedListIterator(LinkedList<E> list) {
    this.list = list;
  }

  @Override
  public boolean hasNext() {
    return index < list.size();
  }

  @Override
  public E next() {
    return list.get(index++);
  }
}

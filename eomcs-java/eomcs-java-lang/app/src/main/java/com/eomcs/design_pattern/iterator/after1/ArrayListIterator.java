package com.eomcs.design_pattern.iterator.after1;

// ArrayList에서 데이터를 꺼내줄 객체
//
public class ArrayListIterator<E> implements Iterator<E> {

  ArrayList<E> list;
  int index = 0;

  public ArrayListIterator(ArrayList<E> list) {
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

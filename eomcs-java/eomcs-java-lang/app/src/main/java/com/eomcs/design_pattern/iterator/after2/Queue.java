package com.eomcs.design_pattern.iterator.after2;

public class Queue<E> extends LinkedList<E> {

  public void offer(E value) {
    this.add(value);
  }

  public E poll() {
    return this.remove(0);
  }

  public boolean empty() {
    return this.size == 0;
  }

  // Iterator 구현체를 제공한다.
  @Override
  public Iterator<E> iterator() {
    return new QueueIterator<E>(this);
  }

  // 스태틱 중첩 클래스로 정의한다.
  static class QueueIterator<E> implements Iterator<E> {

    Queue<E> list;

    public QueueIterator(Queue<E> list) {
      this.list = list;
    }

    @Override
    public boolean hasNext() {
      return !list.empty();
    }

    @Override
    public E next() {
      return list.poll();
    }
  }
}

package com.eomcs.design_pattern.iterator.after3;

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
    return new QueueIterator<E>();
  }

  // 논스태틱 중첩 클래스로 정의하여 바깥 클래스이 인스턴스 주소를 받는 필드와 
  // 생성자 파라미터를 자동으로 추가하게 한다.
  class QueueIterator<T> implements Iterator<T> {

    @Override
    public boolean hasNext() {
      return !Queue.this.empty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T next() {
      return (T) Queue.this.poll();
    }
  }
}





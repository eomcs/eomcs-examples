package com.eomcs.design_pattern.iterator.after5;

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
    return new Iterator<E>() {
      @Override
      public boolean hasNext() {
        return !Queue.this.empty();
      }

      @Override
      public E next() {
        return Queue.this.poll();
      }
    };
  }


}





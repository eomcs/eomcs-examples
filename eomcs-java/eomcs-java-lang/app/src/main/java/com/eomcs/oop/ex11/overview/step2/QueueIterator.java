package com.eomcs.oop.ex11.overview.step2;

// MyQueue 컬렉션에서 데이터를 꺼내는 일을 한다.
// 
public class QueueIterator implements Iterator {

  MyQueue queue;

  public QueueIterator(MyQueue queue) {
    this.queue = queue;
  }

  @Override
  public boolean hasNext() {
    return queue.size() > 0;
  }

  @Override
  public Object next() {
    return queue.poll();
  }
}






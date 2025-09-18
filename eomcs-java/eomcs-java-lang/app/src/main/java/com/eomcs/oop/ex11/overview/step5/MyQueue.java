package com.eomcs.oop.ex11.overview.step5;

public class MyQueue extends MyList {

  public void offer(Object obj) {
    this.add(obj);
  }

  public Object poll() {
    if (size == 0) {
      return null;
    }
    return remove(0);
  }

  @Override
  public Iterator iterator() {
    return new QueueIterator();
  }

  // non-static nested class(논스태틱 중첩 클래스)
  // - inner class 라고도 부른다.
  // - QueueIterator가 사용할 바깥 클래스 MyQueue의 인스턴스를 주소를 저장할 필드와 생성자가 자동으로 추가된다.
  // - 기존의 static nested class 처럼 개발자가 직접 추가할 필요가 없다.
  // - 바깥 클래스의 인스턴스를 사용하는 경우, static nested class로 만드는 것 보다 더 편리하다.
  //
  class QueueIterator implements Iterator {

    @Override
    public boolean hasNext() {
      return MyQueue.this.size() > 0;
    }

    @Override
    public Object next() {
      return MyQueue.this.poll();
    }
  }
}












package com.eomcs.oop.ex11.overview.step7;

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
    // anonymous class(익명 클래스)
    // - 클래스의 이름이 없다. 
    // - 그래서 클래스를 정의한 후 따로 인스턴스를 생성할 수 없다.
    // - 클래스 정의와 인스턴스 생성 문장이 합쳐져 있다.
    //
    return new Iterator() {

      @Override
      public boolean hasNext() {
        return MyQueue.this.size() > 0;
      }

      @Override
      public Object next() {
        return MyQueue.this.poll();
      }
    };
  }
}












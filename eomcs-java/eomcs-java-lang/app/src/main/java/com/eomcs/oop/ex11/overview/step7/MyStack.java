package com.eomcs.oop.ex11.overview.step7;

import java.util.EmptyStackException;

public class MyStack extends MyList {

  public void push(Object obj) {
    this.add(obj);
  }

  public Object pop() {
    if (size == 0) {
      throw new EmptyStackException();
    }
    return remove(size - 1);
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
        return MyStack.this.size() > 0;
      }

      @Override
      public Object next() {
        return MyStack.this.pop();
      }
    };
  }
}

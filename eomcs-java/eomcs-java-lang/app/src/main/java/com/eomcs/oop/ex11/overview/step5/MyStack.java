package com.eomcs.oop.ex11.overview.step5;

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
    return new StackIterator();
  }

  // non-static nested class(논스태틱 중첩 클래스)
  // - inner class 라고도 부른다.
  // - StackIterator가 사용할 바깥 클래스 MyStack의 인스턴스를 주소를 저장할 필드와 생성자가 자동으로 추가된다.
  // - 기존의 static nested class 처럼 개발자가 직접 추가할 필요가 없다.
  // - 바깥 클래스의 인스턴스를 사용하는 경우, static nested class로 만드는 것 보다 더 편리하다.
  //
  class StackIterator implements Iterator {

    @Override
    public boolean hasNext() {
      return MyStack.this.size() > 0;
    }

    @Override
    public Object next() {
      return MyStack.this.pop();
    }
  }
}

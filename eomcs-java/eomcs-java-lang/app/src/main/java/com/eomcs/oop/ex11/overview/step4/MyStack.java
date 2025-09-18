package com.eomcs.oop.ex11.overview.step4;

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
    return new StackIterator(this);
  }

  // static nested class(스태틱 중첩 클래스)
  // - StackIterator는 MyStack 클래스에서만 직접 사용된다.
  // - 중첩 클래스 문법을 사용하여 명학하게 StackIterator의 사용범위를 제한한다.
  //
  static class StackIterator implements Iterator {

    MyStack stack;

    public StackIterator(MyStack stack) {
      this.stack = stack;
    }

    @Override
    public boolean hasNext() {
      return stack.size() > 0;
    }

    @Override
    public Object next() {
      return stack.pop();
    }
  }
}

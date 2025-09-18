package com.eomcs.oop.ex11.overview.step2;

// MyStack 컬렉션에서 데이터를 꺼내는 일을 한다.
// 
public class StackIterator implements Iterator {

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






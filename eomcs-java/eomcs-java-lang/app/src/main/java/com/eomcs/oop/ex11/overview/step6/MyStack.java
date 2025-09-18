package com.eomcs.oop.ex11.overview.step6;

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
    // local class(로컬 클래스)
    // - 메서드나 특정 블록 안에서만 사용될 클래스라면 
    //   그 메서드나 블록에서 클래스를 정의함으로써 
    //   명시적으로 사용 범위를 더 제한할 수 있다.
    // - 단지 사용 범위를 더 제한한 것에 불과하다.
    // - 로컬 클래스에도 바깥 클래스의 인스턴스 주소를 저장할 필드와 생성자가 자동으로 추가된다.
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

    return new StackIterator();
  }
}

// anonymous class - 익명 클래스가 놓이는 장소: 인스턴스 필드
package com.eomcs.oop.ex11.e;

public class Exam0420 {
  // 인터페이스의 경우 static으로 선언하지 않아도 스태틱 멤버에서 사용할 수 있다.
  interface A {
    void print();
  }

  // 인스턴스 필드의 값을 준비할 때 익명 클래스를 사용할 수 있다.
  A obj = new A() {
    @Override
    public void print() {
      System.out.println("Hello!");
    }
  };
  int v1 = 100;

  // 컴파일 할 때 위의 문장은 다음의 문장으로 변환한다.
  //  class Exam0420$1 extends A {
  //    Exam0410 this$0;
  //    public Exam0420$1(Exam0420 obj) {
  //      this$0 = obj;
  //    }
  //    @Override
  //    public void print() {
  //      System.out.println("Hello!");
  //    }
  //  }
  //  
  //  A obj;
  //  int v1;
  //  
  //  public Exam0420() {
  //    this.obj = new Exam0420$1(this);
  //    this.v1 = 100;
  //  }
}

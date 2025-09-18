// anonymous class - 익명 클래스가 놓이는 장소: 리턴
package com.eomcs.oop.ex11.e;

class My {
  static void m1() {
    System.out.println("오호라!!!!");
  }

  void m2() {
    System.out.println("와우~~~~!");
  }
}

public class Exam0450 {
  // 인터페이스의 경우 static으로 선언하지 않아도 스태틱 멤버에서 사용할 수 있다.
  interface A {
    void print();
  }

  static A create0() {
    class X implements A {
      @Override
      public void print() {
        System.out.println("Hello0!");
      }
    }
    return new X();
  }

  static A create1() {
    A a = new A() {
      @Override
      public void print() {
        System.out.println("Hello1!");
      }
    };
    return a;
  }

  static A create2() {
    return new A() {
      @Override
      public void print() {
        System.out.println("Hello2!");
      }
    };
  }

  static A create3() {
    return () -> System.out.println("Hello3!");

    // 컴파일러는 위의 문장을 다음과 같이 바꾼다.
    //    return new A() {
    //      @Override
    //      public void print() {
    //        System.out.println("Hello3!");
    //      }
    //    };
  }

  static A create4() {
    return My::m1;

    // 위의 문장은 다음과 같다.
    //    return () -> My.m1();

    // 컴파일러는 위의 문장을 다음과 같이 바꾼다.
    //    return new A() {
    //      public void print() {
    //        My.m1();
    //      }
    //    };

  }

  static A create5() {
    return new My()::m2;

    // 위의 코드는 다음과 같다.
    //    return () -> new My().m2();

    // 컴파일러는 위의 문장을 다음과 같이 바꾼다.
    //    return new A() {
    //      @Override
    //      public void print() {
    //        new My().m2();
    //      }
    //    };

    //    class X implements A {
    //      @Override
    //      public void print() {
    //        new My().m2();
    //      }
    //    }
    //    return new X();
  }

  public static void main(String[] args) {
    A obj0 = create0();
    obj0.print();

    A obj1 = create1();
    obj1.print();

    A obj2 = create2();
    obj2.print();

    A obj3 = create3();
    obj3.print();

    A obj4 = create4();
    obj4.print();

    A obj5 = create5();
    obj5.print();
  }
}

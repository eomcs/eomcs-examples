// local class : .class 파일명
package com.eomcs.oop.ex11.d;

class B2 {
  void m1() {
    class X {
      // 로컬 클래스의 .class 파일명
      // => [바깥클래스명]$[정의된순서][로컬클래스명].class
      // => 예) B2$1X.class
    }
    class Y {
      // 로컬 클래스의 .class 파일명
      // => [바깥클래스명]$[정의된순서][로컬클래스명].class
      // => 예) B2$1Y.class
    }
  }

  static void m2() {
    class Y {
      // 로컬 클래스의 .class 파일명
      // => [바깥클래스명]$[정의된순서][로컬클래스명].class
      // => 예) B2$2Y.class
    }
    class X {
      // 로컬 클래스의 .class 파일명
      // => [바깥클래스명]$[정의된순서][로컬클래스명].class
      // => 예) B2$2X.class
    }
    class Z {
      // 로컬 클래스의 .class 파일명
      // => [바깥클래스명]$[정의된순서][로컬클래스명].class
      // => 예) B2$1Z.class
    }
  }
}

public class Exam0130 {

  public static void main(String[] args) {
  }

}

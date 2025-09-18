// 상속과 메서드 호출
package com.eomcs.oop.ex05.e;

public class Exam03 {

  public static void main(String[] args) {
    B obj = new D();

    //    obj.m4(); // obj 레퍼런스의 클래스(B)에서 m4()를 찾아보고 없다면 수퍼 클래스에서 찾는다.
    //    obj.m3(); // obj 레퍼런스의 클래스(B)에서 m3()를 찾아보고 없다면 수퍼 클래스에서 찾는다.
    obj.m2();
    obj.m1();

  }

}

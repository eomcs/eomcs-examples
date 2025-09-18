// Reflection API : 클래스 로딩과 인스턴스 생성
package com.eomcs.reflect.ex01;


public class Exam0310 {

  static class A {
    void m() {
      System.out.println("Hello!");
    }
  }

  public static void main(String[] args) throws Exception {
    // 1) 일반적인 인스턴스 생성
    //    A obj1 = new A();

    // 2) 클래스 정보를 가지고 인스턴스 생성
    // => 클래스 정보를 로딩한다.
    Class clazz = Class.forName("com.eomcs.reflect.ex01.Exam0310$A");

    // 타입(클래스) 정보만 있다면 인스턴스 생성할 수 있다.
    A obj = (A) clazz.newInstance();
    obj.m();

    // deprecated 메서드이다.
    // 자바는 생성자를 이용하여 인스턴스를 생성할 것을 권고하고 있다.
  }

}



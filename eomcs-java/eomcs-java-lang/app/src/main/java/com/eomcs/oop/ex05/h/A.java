package com.eomcs.oop.ex05.h;

public class A /*extends Object*/ {
  int v1;

  // A 클래스는 기본 생성자가 없다.
  // => int 값을 받는 생성자만 있다.
  // 
  A(int value) {
    this.v1 = value;
    System.out.println("A(int) 생성자!");
  }
}



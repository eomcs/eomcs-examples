package com.eomcs.spring.ioc.ex13.e;

import org.springframework.stereotype.Component;

@Component 
public class X {
  public X() {
    System.out.println("X 객체 생성됨");
  }
  public int m1(int a, int b) {
    System.out.println("X.m1()");
    int result = a / b;

    return result; 
  }
}

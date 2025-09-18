package com.eomcs.design_pattern.proxy.after.server;

// 실제 일을 하는 객체는 인터페이스의 규칙에 따라 동작하도록 구현되어야 한다.
public class CalculatorImpl implements Calculator {
  
  public int plus(int a, int b) {
    return a + b;
  }
  
  public int minus(int a, int b) {
    return a - b;
  }
}

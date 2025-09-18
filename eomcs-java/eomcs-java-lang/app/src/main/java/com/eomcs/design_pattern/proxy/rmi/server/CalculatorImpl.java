// RMI 기술 사용하기 - 원격 인터페이스 구현하기 : 원격 객체 정의하기
package com.eomcs.design_pattern.proxy.rmi.server;

import java.rmi.RemoteException;

// 원격 객체(remote object)
// => remote interface 구현체이다.
// => 인터페이스 규칙에 따라 실제 일을 하는 객체이다.
// => 클라이언트는 이 객체의 stub을 이용하여 이 객체를 사용한다.
//
public class CalculatorImpl implements Calculator {
  
  protected CalculatorImpl() throws RemoteException {
    super();
  }

  public int plus(int a, int b) {
    return a + b;
  }
  
  public int minus(int a, int b) {
    return a - b;
  }
}







package com.eomcs.spring.ioc.ex13.a.step3;

import org.springframework.stereotype.Component;

@Component
public class X {
  public X() {
    System.out.println("X 객체 생성됨");
  }

  public void m1() {
    System.out.println("X.m1()");

    //시간 측정에 유효한 결과를 출력하기 위해 작업 실행 시간을 좀 늘린다.
    for (int i = 0; i < 1000000; i++) {
      double temp = 3.14159 / Math.random();
    }
  }
}

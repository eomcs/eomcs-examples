package com.eomcs.spring.ioc.ex13.a.step2;

import org.springframework.stereotype.Component;

@Component
public class X {
  public X() {
    System.out.println("X 객체 생성됨");
  }

  public void m1() {
    // m1() 메서드를 실행하기 전에 기능을 추가하기
    // => 다음과 같이 직접 해당 기능을 수행하는 코드를 삽입한다.
    long startTime = System.currentTimeMillis();
    System.out.println("시간 측정 시작!");

    // 이렇게 어떤 메서드를 실행하기 전이나 후에 기능을 추가하고 싶다면 
    // 해당 메서드를 찾아가서 코드를 편집해야 한다.
    // 
    // 이 고전적인 방식의 문제점?
    // 1) 원래 코드에 손을 대야 하기 때문에 유지보수에 좋지 않다.
    //    변경하면 또 기존 코드를 손대야 한다.
    // 2) 유사한 일을 하는 메서드에 같은 기능을 삽입할 때 여러 곳을 편집해야 하기 때문에 유지보수가 힘들다.
    // 3) 추가한 기능을 걷어 낼 때도 매우 번거롭다.
    // 
    // 예) DAO 객체의 메서드들을 실행하는데 걸리는 시간을 측정하기
    // 예) 특정 메서드가 실행될 때 로그를 남기기
    // 예) 모든 메서드의 리턴 값을 특정 타입으로 변환하기
    // 

    System.out.println("X.m1()");

    //시간 측정에 유효한 결과를 출력하기 위해 작업 실행 시간을 좀 늘린다.
    for (int i = 0; i < 1000000; i++) {
      double temp = 3.14159 / Math.random();
    }

    // => 다음과 같이 직접 해당 기능을 수행하는 코드를 삽입한다.
    long endTime = System.currentTimeMillis();
    System.out.println("시간 측정 종료!");
    System.out.println("걸린 시간: " + (endTime - startTime));
  }
}

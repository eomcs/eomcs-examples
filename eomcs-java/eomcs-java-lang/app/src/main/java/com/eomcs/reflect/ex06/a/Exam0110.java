package com.eomcs.reflect.ex06.a;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

// Proxy 객체를 만드는 방법
public class Exam0110 {
  public static void main(String[] args) {

    Class<?> clazz = Exam0110.class;
    ClassLoader classLoader = clazz.getClassLoader();
    Class<?>[] interfaceTypes = new Class<?>[] {MyInterface.class};
    InvocationHandler invocationHandler = new MyInvocationHandler();

    MyInterface obj = (MyInterface) Proxy.newProxyInstance(
        classLoader,
        interfaceTypes,
        invocationHandler);

    //    MyInterface obj = (MyInterface) Proxy.newProxyInstance(
    //        Exam0110.class.getClassLoader(), // 클래스를 메모리에 로딩하는 일을 할 객체
    //        new Class[] {MyInterface.class}, // 자동 생성할 클래스가 구현해야 하는 인터페이스 목록
    //        new MyInvocationHandler());

    // 자동 생성된 인터페이스 구현체의 메서드 호출하기
    obj.m1();
    obj.m2();
  }
}

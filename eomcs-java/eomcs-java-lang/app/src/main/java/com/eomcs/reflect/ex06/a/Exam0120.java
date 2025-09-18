package com.eomcs.reflect.ex06.a;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Exam0120 {
  public static void main(String[] args) {

    Class<?> clazz = Exam0120.class;
    ClassLoader classLoader = clazz.getClassLoader();

    Class<?> aType = A.class; // A 인터페이스의 정보 = A의 타입 정보  = A의 타입
    Class<?> bType = B.class;
    Class<?> cType = C.class;

    Class<?>[] interfaceTypes = new Class<?>[] {aType, bType, cType};

    InvocationHandler invocationHandler = new InvocationHandler() {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("===> " + method.getName());
        return null;
      }
    };

    // newProxyInstance()가 리턴한 객체는
    // A, B, C 인터페이스를 모두 구현한 객체(클래스의 인스턴스)다.
    Object obj = Proxy.newProxyInstance(classLoader, interfaceTypes, invocationHandler);

    ((A) obj).m1();
    ((B) obj).m2();
    ((C) obj).m3();

  }
}

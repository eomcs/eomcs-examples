package com.eomcs.reflect.ex06.b;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    System.out.printf("%s()가 호출됨!\n", method.getName());
    System.out.println(args);
    return null;
  }
}

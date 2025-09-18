package com.eomcs.reflect.ex06.c;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    switch (method.getName()) {
      case "m1":
        return 100; // 다음과 같이 auto-boxing => Integer.valueOf(100)
      case "m2":
        return "Hello!";
    }
    return null;
  }
}

package com.eomcs.reflect.ex06.d;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    switch (method.getName()) {
      case "m1":
        int p1 = (int) args[0];
        int p2 = (int) args[1];
        return p1 + p2;
      case "m2":
        String name = (String) args[0];
        int age = (int) args[1];
        return name + "님은 나이는 " + age + "살 입니다.";
    }
    return null;
  }
}

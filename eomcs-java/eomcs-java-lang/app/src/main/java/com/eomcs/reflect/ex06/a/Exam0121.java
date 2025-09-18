package com.eomcs.reflect.ex06.a;

import java.lang.reflect.Proxy;

public class Exam0121 {
  public static void main(String[] args) {

    Object obj = Proxy.newProxyInstance(
        Exam0121.class.getClassLoader(), 
        new Class<?>[] {A.class, B.class, C.class}, 
        (proxy, method, params)  -> {
          System.out.println("===> " + method.getName());
          return null;
        });

    ((A) obj).m1();
    ((B) obj).m2();
    ((C) obj).m3();

  }
}

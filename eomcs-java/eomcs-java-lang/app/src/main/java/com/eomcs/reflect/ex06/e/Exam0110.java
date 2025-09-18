// 인터페이스 구현체를 자동으로 생성하기
package com.eomcs.reflect.ex06.e;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Exam0110 {

  public static void main(String[] args) {
    // java.lang.reflect.Proxy
    // => 인터페이스 구현체를 만드는 역할을 한다.
    // 
    // newProxyInstance(
    //    만든 인터페이스 구현체를 메모리에 로딩하는 일을 도와줄 클래스로더,
    //    구현할 인터페이스 정보 목록,
    //    실제 작업을 수행하는 객체)
    // => 파라미터로 넘겨 받은 인터페이스를 모두 구현한 클래스를 만들고 인스턴스를 생성해 리턴한다.
    // 
    // 클래스 로더
    // => 클래스 정보를 로딩하는 역할을 수행한다.
    // => 클래스 로더를 얻는 방법
    //    - 클래스정보.getClassLoader()
    // => 클래스 정보
    //    - 인스턴스.getClass()
    //    - 클래스명.class
    //    - Class.forName("패키지명을 포함한 클래스명")
    //
    // 클래스 정보를 얻는 방법 예: 
    /*
    String s = "hello"; 
    Class<?> c1 = String.class; // 클래스의 static 변수인 class의 값을 사용할 수 있다.
    Class<?> c2 = s.getClass(); // 인스턴스로 알아낼 수 있다.
    Class<?> c3 = Class.forName("java.lang.String"); // 클래스 정보를 리턴하는 도구를 이용.
     */
    //
    // 실제 작업을 수행하는 객체
    // => java.lang.reflect.InvocationHandler 인터페이스에 따라 동작하는 객체
    // => 즉 InvocationHandler 구현체
    // 

    // 1) 로컬 클래스
    class MyHandler implements InvocationHandler {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke()...호출됨!");
        // newProxyInstance()가 생성한 객체에 대해 메서드를 호출할 때마다 이 메서드가 호출된다.
        int a = (int) args[0]; // auto-unboxing => ((Integer)args[0]).intValue();
        int b = (int) args[1]; // auto-unboxing => ((Integer)args[1]).intValue();

        switch (method.getName()) {
          case "plus":
            return a + b;
          case "minus":
            return a - b;
        }
        return 0;
      }
    }

    // Calculator 인터페이스를 구현한 클래스를 만들고 그 인스턴스를 생성하여 리턴한다.
    Calculator c1 = (Calculator) Proxy.newProxyInstance(
        Calculator.class.getClassLoader(), 
        new Class[] {Calculator.class}, 
        new MyHandler());

    System.out.println("++++");
    System.out.println(c1.plus(10, 20));
    System.out.println("----");
    System.out.println(c1.minus(10, 20));
  }

}







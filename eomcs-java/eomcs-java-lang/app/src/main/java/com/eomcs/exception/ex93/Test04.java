// 애플리케이션 예외의 종류: Exception 계열의 예외 처리 III
package com.eomcs.exception.ex93;

import java.lang.reflect.Constructor;
import java.util.Scanner;

public class Test04 {

  public static void main(String[] args) 
      // throws 문장도 catch 블록처럼 공통 수퍼 클래스 한 개로 보고할 수 있다.
      // => "이 메서드에서는 Exception 예외가 발생합니다"라는 의미다. 
      // => throws 절에는 java.lang.Throwable 클래스만 선언할 수 있다.
      //    Object 클래스와 같은 것은 선언할 수 없다.
      throws Exception { // 여러 예외를 퉁쳐서 하나로 표현할 수 있다.
      /*
      throws ClassNotFoundException, // 이 메서드에서 발생하는 예외의 목록을 지정한다.
             NoSuchMethodException,  // 만약 메서드를 실행하는 중에 이런 예외가 발생한다면,
             InstantiationException, // 즉시 이 메서드의 실행을 멈추고 
             IllegalAccessException, // 이 메서드를 호출한 쪽에 예외 정보를 보고한다.
             InvocationTargetException { // main()의 호출자? JVM이다.
       */
    Scanner keyboard = new Scanner(System.in);
    Class<?> clazz = Class.forName("ch21.c.PlusCommand");
    Constructor<?> constructor = clazz.getConstructor(Scanner.class);
    Command command = (Command) constructor.newInstance(keyboard);
    command.execute();
  }

}

// 애플리케이션 예외의 종류: Exception 계열의 예외 처리 II
package com.eomcs.exception.ex93;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Test03_2 {

  public static void main(String[] args) 
      throws ClassNotFoundException, // 이 메서드에서 발생하는 예외의 목록을 지정한다.
             NoSuchMethodException,  // 만약 메서드를 실행하는 중에 이런 예외가 발생한다면,
             InstantiationException, // 즉시 이 메서드의 실행을 멈추고 
             IllegalAccessException, // 이 메서드를 호출한 쪽에 예외 정보를 보고한다.
             InvocationTargetException { // main()의 호출자? JVM이다.
    // Exception 계열의 예외 처리 방법
    // 1) try ~ catch로 예외 받기
    //    try {
    //      예외 발생 코드
    //    } catch (예외 파라미터) {
    //      예외처리 코드
    //    }
    // 2) 호출자에게 예외 처리를 떠넘기기
    //    void 메서드() throws 예외클래스명, 예외클래스명, ... {
    //      예외가 발생할 수 있는 코드 
    //    }
    // 
    
    // '방법2' 적용
    // => 메서드 선언부에 throws 절 추가하기
    // => 이 방식을 사용해야 하는 경우? 
    //    이런 예외를 한 곳에서 처리하고 싶을 때 이 방식을 사용한다.
    // => "어이~~ 여기에 예외 처리하지 마! 호출자에게 전달해." 라는 의미! 
    
    Scanner keyboard = new Scanner(System.in);
    Class<?> clazz = Class.forName("ch21.c.PlusCommand");
    Constructor<?> constructor = clazz.getConstructor(Scanner.class);
    Command command = (Command) constructor.newInstance(keyboard);
    command.execute();
  }

}

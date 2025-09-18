// 예외 던지기 - throw 명령을 사용하여 예외를 보고하기
package com.eomcs.exception.ex94;

import java.util.ArrayList;

public class Test03 {
  static ArrayList<String> list = new ArrayList<>();
  
  public static void main(String[] args) {
    addName("홍길동");
    addName("임꺽정");
    addName(null); // 전학간 학생인 경우 해당 번호의 이름을 null로 설정한다고 가정하자!
    addName(null); // 전학간 학생인 경우 해당 번호의 이름을 null로 설정한다고 가정하자!
    addName("유관순");
    
    for (int i = 0; i <= list.size(); i++) {
      
      // 리턴 값이 아닌 별개의 경로로 전달되는 예외는 
      // try ~ catch 로 처리한다.
      // 
      try {
        String name = getName(i);
        System.out.println(getName(i));
      } catch (Exception e) {
        System.out.println("목록에서 데이터를 꺼내는 중에 오류 발생!");
      }
    }
   
    System.out.printf("입력된 이름 개수는 %d 입니다.\n", list.size());
    
    try {
      // throw 명령으로 전달된 예외 정보는 catch 블록을 통해 별개의 경로로 받을 수 있다.
      // 따라서 예전처럼 리턴 값으로 받을 때의 문제점이 없다.
      // 
      int result = divide(1919191919, -1); 
      System.out.println(result);
      
    } catch (Exception e) {
      System.out.println("나누기 오류 입니다.");
    }
  }
  
  static void addName(String name) {
    list.add(name);
  }
  
  //3) throw 명령으로 예외 보고하기
  //=> throw 명령을 사용하여 예외 객체를 호출자에게 던진다.
  //     throw [java.lang.Throwable 객체];
  //=> 메서드 선언부에 던지는 예외 클래스의 이름을 기술한다.
  //     리턴타입 메서드명(파라미터, 파라미터, ...) throws 예외클래스, 예외클래스, ... {...}
  // 
  static String getName(int index) throws Exception { // 어떤 예외 던지는지 알려주기
    if (index < 0 || index >= list.size())
      throw new Exception("무효한 인덱스입니다."); // 예외 던지기
      //throw new Object(); // 컴파일 오류!
      // throw 명령으로 던질 수 있는 예외는 java.lang.Throwable 타입이어야 한다.
      // 다른 타입의 객체는 던질 수 없다.
      // 애플리케이션에서 발생하는 예외는 가능한 java.lang.Exception을 사용하라!
      // java.lang.Error 클래스는 JVM에서 발생하는 시스템 예외를 위한 클래스이다. 사용하지 말라!
      // 그리고 Throwable 클래스의 객체로 가능한 사용하지 말라!
      // 
    
    return list.get(index);
  }
  
  static int divide(int a, int b) throws Exception {
    if (b == 0) 
      throw new Exception("0으로 나눌 수 없습니다.");
   
    return a / b;
  }
 
 
}







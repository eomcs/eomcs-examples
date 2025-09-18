// 예외 던지기 - 리턴 값으로 예외를 보고하는 경우
package com.eomcs.exception.ex94;

import java.util.ArrayList;

public class Test02 {
  static ArrayList<String> list = new ArrayList<>();
  
  public static void main(String[] args) {
    addName("홍길동");
    addName("임꺽정");
    addName(null); // 전학간 학생인 경우 해당 번호의 이름을 null로 설정한다고 가정하자!
    addName(null); // 전학간 학생인 경우 해당 번호의 이름을 null로 설정한다고 가정하자!
    addName("유관순");
    
    // Exception 계열(RuntimeException 제외)의 예외가 발생하는 메서드를 호출할 때는
    // 반드시 예외처리를 해야 한다.
    // try ~ catch로 처리하든, throws 절을 메서드 선언부에 붙이든 둘 중 하나는 해야 한다.
    // 
    for (int i = 0; i < list.size(); i++) {
      String name = getName(i);
      
      // 예외 상황을 검사하기 위해 getName()을 리턴 값을 확인한다.
      // null이면 예외가 발생했다는 것이다.
      // => 문제는 예외 상황일 때 멧서드가 리턴하는 것이 정상 값일 수 있다는 것이다.
      // 
      if (name == null) {
        System.out.println("목록에서 데이터를 꺼낸 중에 오류 발생!");
      } else {
        System.out.println(getName(i));
      }
    }
   
    System.out.printf("입력된 이름 개수는 %d 입니다.\n", list.size());
    
    
    // int 값을 리턴하는 메서드가 오류를 알리는 방법
    // => 이상한 값을 리턴한다.
    // 
    //int result = divide(1000, 7); // OK! 
    //int result = divide(1000, 0); // OK! 예외임을 알 수 있다.
    int result = divide(1919191919, -1); // 정상적인 나누기임에도 불구하고 
    if (result == -1919191919) { // 리턴 값이 -1919191919 이기 때문에 오류로 판단한다. 
      System.out.println("나누기 오류 입니다."); // 이것이 리턴값으로 오류를 전달할 때의 문제이다.
    } else {
      System.out.println(result);
    }
  }
  
  static void addName(String name) {
    list.add(name);
  }
  
  //2) 리턴 값으로 예외를 보고하기
  //=> C 프로그래밍 시절에는 주로 리턴 값으로 예외 상황을 보고하였다.
  //=> 문제는 return 값으로 예외 상황을 완전히 표현할 수 없다는 것이다.
  // 
  static String getName(int index) {
    if (index < 0 || index >= list.size())
      return null;
    
    // 예외 상황일 때 이 메서드가 리턴하는 null은 
    // 일부러 목록에 입력한 null일 수 있다.
    // 즉 예외 상황일 때 리턴하는 값과 정상적인 결과로 리턴하는 값이 같을 수 있다.
    // 이렇게 return 값으로 예외 상황을 알리는 방식은 
    // 완전하게 동작하지 못한다.
    // => 그래서 예외 처리 문법이 별도로 등장한 것이다.
    // => 즉 어떤 상황에서 예외가 발생하면 그것을 호출자에게 리턴 값으로 알리지 말고,
    //    별개의 경로로 알려주자!
    
    
    return list.get(index);
  }
  
  static int divide(int a, int b) {
    if (b == 0) {
      // 호출자에게 어떤 값으로 알려줘야 오류를 전달할 수 있을까?
      // => 옛날에는 이런 경우 이상한 값을 리턴하였다.
      // => 예를 들어 divide() 계산 결과로 잘 나오지 않는 값을 리턴하였다.
      return -1919191919;
    }
    return a / b;
  }
 
 
}







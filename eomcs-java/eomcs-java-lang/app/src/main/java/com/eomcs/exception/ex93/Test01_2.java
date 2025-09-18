// 애플리케이션 예외의 종류: Exception 계열의 예외와 RuntimeException 계열의 예외
package com.eomcs.exception.ex93;

public class Test01_2 {

  public static void main(String[] args) {
    // 2) RuntimeException 계열의 예외
    // => Exception 클래스의 서브 클래스이다.
    // => 이 계열의 예외가 발생하는 경우에는 "예외 처리"가 필수가 아니다.
    //    선택이다.
    //    즉 try ~ catch를 쓰지 않아도 컴파일 오류가 발생하지 않는다.
    // => 그러나 예외를 처리하지 않으면 메서드 호출자에게 예외가 전달된다.
    //    메서드 호출자가 예외를 처리하지 않으면 그 상위 호출자에게 전달된다.
    //    그 상위 호출자가 예외를 처리하지 않으면 그 상위의 상위 호출자에게 전달된다.
    //    이런 식으로 계속 전달되다보면 main() 메서드까지 전달되고,
    //    main()에서도 예외를 처리하지 않으면 최종적으로 JVM에게 전달된다.
    //    JVM이 예외를 받으면 그 즉시 프로그램을 멈춘다.
    //    따라서 try ~ catch 사용을 강요받지 않더라도 
    //    RuntimeException 예외를 처리하는 것이 JVM을 멈추지 않게 하는 것이다. 
    //

    int result = divide(100, 2); // OK!
    System.out.println(result);
    
    // RuntimeException 예외가 발생할 수 있는 메서드를 호출할 경우
    // 컴파일러가 try ~ catch ~ 로 예외를 처리하라고 요구하지 않는다.
    // 그렇다고 특정 예외 조건(0으로 나누는 것)에서 예외가 발생되지 않는 것은 아니다.
    // 다만 이 메서드에서 처리하지 않으면
    // 이 메서드를 호출한 쪽에 예외를 자동으로 전달한다.
    // main()을 호출한 것은 JVM이기 때문에 
    // main()에서 예외를 처리하지 않으면 JVM에게 전달되고 
    // JVM은 예외를 받는 즉시 실행을 멈춘다.
    
    result = divide(100, 0); // 예외 발생! main() 호출자에게 전달. 즉 JVM에 전달. 즉시 종료!
    System.out.println(result);
    
    System.out.println("실행 종료!");
  }
  
  static int divide(int a, int b) throws RuntimeException {
    if (b == 0)
      throw new RuntimeException("0으로 나눌 수 없습니다.");
    return a / b;
  }

}










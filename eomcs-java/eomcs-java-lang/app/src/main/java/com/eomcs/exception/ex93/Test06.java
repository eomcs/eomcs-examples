// 애플리케이션 예외의 종류: RuntimeException 계열의 예외 처리 II
package com.eomcs.exception.ex93;

import java.util.ArrayList;

public class Test06 {

  public static void main(String[] args) { 
    
    ArrayList<String> list = new ArrayList<>();
    
    list.add("홍길동");
    list.add("임꺽정");
    list.add("유관순");
    
    // get() 메서드
    // => 목록에서 인덱스 지정한 위치의 값을 꺼내준다.
    // => 만약 인덱스가 무효하다면 IndexOutOfBoundsException 예외가 발생한다.
    // => IndexOutOfBoundsException은 RuntimeException 계열의 예외 클래스이다.
    //
    
    // RuntimeException 계열의 예외 처리하기
    // 1) try ~ catch를 사용하지 않으면 자동으로 호출자에게 보고된다.
    // 2) try ~ catch를 사용할 수 있다.
    // 3) throws 문장을 선언할 수 있다.
    // 
    // '방법2' 적용
    // => try ~ catch 적용
    try {
      System.out.println(list.get(0));
      System.out.println(list.get(1));
      System.out.println(list.get(2));
      System.out.println(list.get(3)); // 예외가 발생하면 catch 블록에서 예외를 받는다.
      
    } catch (RuntimeException e) {
      // 여기서 예외를 처리했기 때문에 main() 호출자에게 따로 보고하지 않는다.
      System.out.println("실행 중 오류 밸생!");
    }
    
    System.out.println("종료!");
  }

}








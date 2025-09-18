// 제네릭(generic) 적용 전 - Object를 이용한 다형적 변수 사용
package com.eomcs.generic.ex01;

import java.util.Calendar;
import java.util.Date;

public class Exam0110 {

  public static Object echo(Object obj) {
    return obj;
  }

  public static void main(String[] args) {

    // echo() 리턴 타입이 Object이기 때문에 String 레퍼런스로 바로 받을 수 없다.
    // => 해결책? 형변환 해야 한다.
    //
    Object obj = echo(new String("Hello")); // OK!

    //    String obj1 = echo(new String("Hello")); // 컴파일 오류!
    String obj2 = (String) echo(new String("Hello"));

    // 잘못된 형변환은 컴파일러는 속일 수 있을 지라도, runtime 에서는 오류를 발생시킨다.
    //    Integer obj3 = (Integer) echo(new String("Hello")); // 실행 오류!

    Date obj4 = (Date) echo(new Date());

    Calendar obj5 = (Calendar) echo(Calendar.getInstance());
  }

}

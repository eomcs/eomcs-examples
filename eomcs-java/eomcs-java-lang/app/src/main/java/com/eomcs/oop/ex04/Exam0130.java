// 생성자 활용 예 - java.util.Calendar 클래스의 생성자
package com.eomcs.oop.ex04;

import java.util.Calendar;

public class Exam0130 {

  public static void main(String[] args) throws Exception {
    // 생성자가 있다하더라도 접근 권한이 없다면,
    // 생성자를 호출할 수 없다.
    // 이런 경우 new 명령으로 인스턴스를 생성할 수 없다.
    //    Calendar c = new Calendar(); // 컴파일 오류!

    // Calendar 클래스의 경우도 생성자를 protected 로 막고 있다.
    // 즉 new 명령을 통해 바로 인스턴스를 생성할 수 없다.
    // 대신 클래스 메서드를 통해 생성하도록 유도하고 있다.
    // 
    Calendar c1 = Calendar.getInstance();
    Calendar c2 = Calendar.getInstance();
    System.out.println(c1 == c2);

    System.out.println(c1.get(1));
    System.out.println(c2.get(Calendar.YEAR));

    // getInstance() 호출 시점의 시각이 다르기 때문에 두 객체의 주소는 다르다.

    // 이렇게 자바에서 생성자의 사용 권한을 막고 메서드를 호출하여
    // 객체를 생성하도록 유도하는 경우는 다음과 같다.
    //
    // 1) 같은 값을 갖는 객체를 쓸데없이 여러 개 생성하지 못하도록 하고 싶을 때,
    //    => 메모리를 절약할 수 있다.
    //    => 이런 방법을 사용하는 설계 기법: Singleton
    // 2) 객체 생성과정이 복잡할 때,
    //    => 객체 생성 코드를 단순하게 만들 수 있다.
    //    => 생성된 객체를 재활용할 수 있다.
    //    => 이런 방법을 사용하는 설계 기법: Factory Method
    // 
  }
}















// 캡슐화(encapsulation) 응용 - 생성자 접근이 금지된 또 다른 예
package com.eomcs.oop.ex08.b;

import java.util.Calendar;

public class Exam0230 {

  public static void main(String[] args) {
    // java.util.Calendar 객체 만들기
    // => 생성자가 protected로 되어 있다.
    // => 의미?
    //    - 보통 개발자가 클래스를 만들 때는 자신 만의 패키지에 넣어서 만든다.
    //    - java.util 패키지를 자신의 클래스를 두기 위해 사용하지는 않을 것이다.
    //    - 따라서 Calendar 생성자가 protected로 되어 있다는 것은
    //      개발자가 직접 생성자를 호출하지는 말라는 의미다.
    //      물론 Calendar를 만든 자신들은 나중에 Calendar의 서브 클래스를 만들 때
    //      이 생성자를 직접 사용하겠다는 의도로 생성자를 protected 했음을 알 수 있다.
    // => 그럼 이 클래스를 개발자가 사용하지 말라는 것인가?
    //    개발자들이 이 클래스의 인스턴스를 만들 수 있도록 스태틱 메서드를 제공한다.
    //    당연히 그 메서드는 외부에서 호출할 수 있도록 public으로 공개되었다.
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    System.out.println(cal1 == cal2);

    // getInstance()의 리턴 값을 확인해 보면 다르다는 것을 알 수 있다.
    // 즉 getInstance() 메서드는 singleton 기능을 수행하는 메서드가 아니라,
    // 복잡한 Calendar 객체를 대신 생성해주는 factory method로서 역할을 수행한다.
    //
    // 생성자를 protected로 감추고, getInstance()를 public 으로 공개하였다.
    // 캡슐화를 응용한 예이다.
  }
}








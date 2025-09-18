// java.util.Calendar 클래스 - 생성자 활용
package com.eomcs.basic.ex02;

import java.util.Calendar;

public class Exam0410 {
  public static void main(String[] args) {

    Calendar c1;

    // 생성자가 있다하더라도 접근 권한이 없으면 호출할 수 없다.
    //    c1 = new Calendar(); // 컴파일 오류!

    // Calendar는 인스턴스 생성을 도와주는 별도의 클래스 메서드(스태틱 메서드)를 제공한다.
    c1 = Calendar.getInstance();
    System.out.println(c1.get(1)); // year
    System.out.println(c1.get(2) + 1); // month
    System.out.println(c1.get(5)); // date
    System.out.println(c1.get(10)); // hour
    System.out.println(c1.get(9)); // am/pm
    System.out.println(c1.get(12)); // minute
    System.out.println(c1.get(13)); //seconds

    System.out.println(c1.get(Calendar.YEAR));
    System.out.println(c1.get(Calendar.MONTH) + 1);
    System.out.println(c1.get(Calendar.DATE));
    System.out.println(c1.get(Calendar.HOUR));
    System.out.println(c1.get(Calendar.AM_PM));
    System.out.println(c1.get(Calendar.MINUTE));
    System.out.println(c1.get(Calendar.SECOND));

  }
}

// 객체 생성 디자인 패턴 중 일부 소개
//
// 1) 팩토리 메서드(factory method)
// - GoF(Gang of Four)의 23가지 디자인 패턴(design pattern) 중 하나이다.
// - 인스턴스를 생성해주는 메서드이다.
// - 인스턴스 생성 과정이 복잡할 경우에 인스턴스를 생성해주는 메서드를 미리 정의해 둔다.
// - 그래서 인스턴스가 필요할 때 마다 메서드를 호출하여 인스턴스를 리턴 받는다.
//
// 2) 싱글톤(singleton)
// - GoF(Gang of Four)의 23가지 디자인 패턴(design pattern) 중 하나이다.
// - 인스턴스를 한 개만 생성하도록 제한할 때 사용한다.
// - 생성자를 private으로 처리하여 직접 인스턴스를 생성하지 못하도록 만든다.
// - 메서드를 통해 인스턴스를 생성하도록 유도한다.



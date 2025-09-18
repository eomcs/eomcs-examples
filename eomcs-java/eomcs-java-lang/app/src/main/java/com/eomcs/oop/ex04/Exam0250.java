// 인스턴스 메서드와 클래스 메서드의 활용 - Calendar 클래스
package com.eomcs.oop.ex04;

import java.util.Calendar;

public class Exam0250 {
  public static void main(String[] args) throws Exception {
    // Calendar 클래스의 생성자는 protected로 접근이 제한되어 있기 때문에
    // 다른 패키지에서 직접 생성자를 호출할 수 없다.
    //Calendar c = new Calendar(); // 컴파일 오류!

    // 오늘 날짜 및 시간 정보를 저장한 객체를 만들어 리턴한다.
    // 달력은 그레고리안 달력을 사용한다.
    Calendar c = Calendar.getInstance();


    System.out.println(c.get(5));

    // 인스턴스 메서드 활용
    System.out.println(c.get(1)); // 년도
    System.out.println(c.get(2) + 1); // 월(0 ~ 11)
    System.out.println(c.get(5)); // 일
    System.out.println(c.get(7)); // 요일(1 ~ 7)
    System.out.println(c.get(4)); // 그 달의 몇 번째 주
    System.out.println(c.get(10)); // 시(0 ~ 11)
    System.out.println(c.get(11)); // 시(24시)
    System.out.println(c.get(12)); // 분
    System.out.println(c.get(13)); // 초

    System.out.println("-----------------------");

    // 상수의 활용
    System.out.println(c.get(Calendar.YEAR)); // 년도
    System.out.println(c.get(Calendar.MONTH) + 1); // 월(0 ~ 11)
    System.out.println(c.get(Calendar.DATE)); // 일
    System.out.println(c.get(Calendar.DAY_OF_WEEK)); // 요일(1 ~ 7)
    System.out.println(c.get(Calendar.WEEK_OF_MONTH)); // 그 달의 몇 번째 주
    System.out.println(c.get(Calendar.HOUR)); // 시(0 ~ 11)
    System.out.println(c.get(Calendar.HOUR_OF_DAY)); // 시(24시)
    System.out.println(c.get(Calendar.MINUTE)); // 분
    System.out.println(c.get(Calendar.SECOND)); // 초
  }
}






// java.util.Date 클래스 - 생성자 활용
package com.eomcs.basic.ex02;

import java.util.Date;

public class Exam0310 {
  public static void main(String[] args) {

    // Date() 기본 생성자
    Date d1 = new Date(); // 현재 시간을 저장한다.
    System.out.println(d1);

    // Date(long) : 1970-01-01 00:00:00 부터 지금까지 경과된 밀리초
    Date d2 = new Date(1000);
    System.out.println(d2);

    Date d3 = new Date(System.currentTimeMillis());
    System.out.println(d3);

    Date d4 = new Date(123, 0, 19);
    System.out.println(d4);

    // java.sql.Date
    java.sql.Date d5 = new java.sql.Date(System.currentTimeMillis());
    System.out.println(d5);

    // 간접적으로 객체를 생성하기
    java.sql.Date d6 = java.sql.Date.valueOf("2023-1-19");
    System.out.println(d6);
  }
}



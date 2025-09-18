// 변수 자동 초기화 - 로컬 변수
package com.eomcs.oop.ex03;

import java.sql.Date;

public class Exam0530 {

  public static void main(String[] args) {

    // 로컬 변수는 자동으로 초기화되지 않는다.
    // 반드시 변수를 사용하기 전에 초기화 시켜야 한다.
    // 초기화하지 않은 로컬 변수는 사용할 수 없다.
    // 초기화?
    // - 값을 한 번이라도 저장하는 것!
    int i;
    String str;
    Date date;

    // 다음과 같이 초기화시키지 않은 로컬 변수를 사용하려고 하면 컴파일 오류가 발생한다!
    //    System.out.println(i); // 컴파일 오류!
    //    System.out.println(str); // 컴파일 오류!
    //    System.out.println(date); // 컴파일 오류!
  }
}



package com.eomcs.lang.ex03;

//# 문자열 리터럴
//- 자바의 기본 타입이 아니라 객체이다.
//- 객체란? 여러 데이터들의 덩어리이다.
//- 표기법
//  큰 따옴표(double quote) 안에 문자들(문자열)을 작성한다.
//  예) "홍길동", "임꺽정", "hello"
//
public class Exam0610 {
  public static void main(String[] args) {
    // 문자열 리터럴 기본 예
    System.out.println("ABC가나다");

    // 문자열에 유니코드를 포함할 수 있다.
    System.out.println("\u0041BC\uac00나다");
  }
}


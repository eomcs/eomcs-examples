package com.eomcs.lang.ex05;

//# 비트 연산자 : 응용 III
//
public class Exam0460 {
  public static void main(String[] args) {
    // 특정 비트의 값을 설정할 때
    // 0x01, 0x02, 0x04, 0x08 처럼 직접 숫자를 사용하면
    // 코드를 읽고 이해하기가 쉽지 않다.
    // 해결책?
    // - 각각의 값을 의미있는 이름을 가진 변수에 저장한 후 사용하라.
    // - 또한 조회용으로 사용할 변수이므로 상수로 선언하라.
    //
    //
    final int CSS           = 0x01; // 0000 0001
    final int HTML          = 0x02; // 0000 0010
    final int PHP           = 0x04; // 0000 0100
    final int PYTHON        = 0x08; // 0000 1000
    final int JAVASCRIPT    = 0x10; // 0001 0000
    final int JAVA          = 0x20; // 0010 0000
    final int CPP           = 0x40; // 0100 0000
    final int C             = 0x80; // 1000 0000

    // Java와 C, C++, JavaScript를 할 줄 아는 개발자의 정보를 설정하라!
    int lang = C | JAVA | PYTHON | HTML;
    //   1000 0000 (C)
    // | 0010 0000 (JAVA)
    // | 0000 1000 (PYTHON)
    // | 0000 0010 (HTML)
    // -------------------
    //   1010 1010
    //

    System.out.println(Integer.toBinaryString(lang));
  }
}

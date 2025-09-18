// Character Stream - 문자 배열 출력하기
package com.eomcs.io.ex03;

import java.io.FileWriter;

public class Exam0210 {

  public static void main(String[] args) throws Exception {
    FileWriter out = new FileWriter("temp/test2.txt");

    char[] chars = new char[] {'A', 'B', 'C', '0', '1', '2', '가', '각', '간', '똘', '똥'};

    // FileOutputStream 은 byte[] 을 출력하지만,
    // FileWriter 는 char[] 을 출력한다.
    out.write(chars); // 문자 배열 전체를 출력한다.

    // 당연히 UCS2를 JVM 환경 변수 'file.encoding'에 설정된 문자 코드표에 따라 변환하여 출력한다.
    // JVM이 입출력 문자 코드표로 UTF-8을 사용한다면
    // 영어는 1바이트로 변환되어 출력될 것이고,
    // 한글은 3바이트로 변환되어 출력될 것이다.
    // JVM(UCS2)    File(UTF-8)
    // 00 41    ==> 41
    // 00 42    ==> 42
    // 00 43    ==> 43
    // 00 30    ==> 30
    // 00 31    ==> 31
    // 00 32    ==> 32
    // ac 00    ==> ea b0 80
    // ac 01    ==> ea b0 81
    // ac 04    ==> ea b0 84
    // b6 18    ==> eb 98 98
    // b6 25    ==> eb 98 a5
    out.close();

    System.out.println("데이터 출력 완료!");

  }

}

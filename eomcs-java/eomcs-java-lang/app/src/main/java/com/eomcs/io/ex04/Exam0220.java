// 데이터 읽기 - int 값 읽기 II
package com.eomcs.io.ex04;

import java.io.FileInputStream;

public class Exam0220 {

  public static void main(String[] args) throws Exception {
    FileInputStream in = new FileInputStream("temp/test3.data");

    // Exam02_1을 실행하여 출력한 데이터를 read()로 읽는다.
    // => 파일에서 4바이트를 읽어 4바이트 int 변수에 저장하라!
    // => 읽은 바이트를 비트이동 연산자를 값을 이동 시킨 후 변수에 저장해야 한다.
    // => 파일에 들어 있는 값 예: 080557d2
    int value = in.read() << 24;   // 00000008 =>   08000000
    value += (in.read() << 16);    // 00000005 => + 00050000
    value += (in.read() << 8);     // 00000057 => + 00005700
    value += in.read();            // 000000d2 => + 000000d2
    //==========================================>   080557d2

    in.close();

    System.out.printf("%08x(%1$d)\n", value);
  }
}







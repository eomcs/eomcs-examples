// 데이터 읽기 - long 값 읽기 
package com.eomcs.io.ex04;

import java.io.FileInputStream;

public class Exam0320 {

  public static void main(String[] args) throws Exception {
    FileInputStream in = new FileInputStream("temp/test3.data");

    // Exam0310을 실행하여 출력한 데이터를 read()로 읽는다. 
    // => 파일에서 8바이트를 읽어 8바이트 long 변수에 저장하라!
    // => 읽은 바이트를 비트이동 연산자를 값을 이동 시킨 후 변수에 저장해야 한다.
    // => 파일에 들어 있는 값 예: 00016bcc41e90000
    long value = (long)in.read() << 56;   
    value += (long)in.read() << 48;
    value += (long)in.read() << 40;
    value += (long)in.read() << 32;
    value += (long)in.read() << 24;
    value += (long)in.read() << 16;
    value += (long)in.read() << 8;     
    value += in.read();            


    in.close();

    System.out.printf("%016x\n", value);
  }
}







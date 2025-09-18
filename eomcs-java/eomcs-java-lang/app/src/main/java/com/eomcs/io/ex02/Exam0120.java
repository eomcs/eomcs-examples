// Byte Stream - 바이트 단위로 읽기
package com.eomcs.io.ex02;

import java.io.FileInputStream;

public class Exam0120 {

  public static void main(String[] args) throws Exception {
    // 1) 파일 데이터 읽기를 담당할 객체를 준비한다.
    // - new FileInputStream(파일경로)
    // - 해당 경로에 파일이 존재하지 않으면 예외가 발생한다.
    //
    FileInputStream in = new FileInputStream("temp/test1.data");

    // 2) 1바이트를 읽는다.
    // => read() 메서드의 리턴 타입이 int 라 하더라도 1바이트를 읽어 리턴한다.
    // => 이유?
    //    0 ~ 255까지의 값을 읽기 때문이다.
    //    byte는 -128 ~ 127까지의 값만 저장한다.
    int b = in.read(); // 읽은 값은 0x4d 이다.
    System.out.printf("%02x\n", b);

    System.out.printf("%02x\n", in.read());
    System.out.printf("%02x\n", in.read());
    System.out.printf("%02x\n", in.read());
    // read() 를 호출할 때마다 이전에 읽은 바이트의 다음 바이트를 읽는다. 

    // 3) 읽기 도구를 닫는다.
    in.close();


  }

}

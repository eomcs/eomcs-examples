// Byte Stream - 텍스트 출력 하기
package com.eomcs.io.ex02;

import java.io.FileOutputStream;

public class Exam0512 {

  public static void main(String[] args) throws Exception {

    String str = new String("AB가각");

    // String 객체의 데이터를 출력하려면
    // 문자열을 담은 byte[] 배열을 리턴 받아야 한다.

    // => UTF-16BE로 인코딩 하기
    System.out.printf("file.encoding=%s\n", System.getProperty("file.encoding"));
    byte[] bytes = str.getBytes("UTF-16BE"); // UCS2 ==> UTF-16BE

    for (byte b : bytes) {
      System.out.printf("%02x ", b);
    }
    System.out.println();

    // 바이트 배열 전체를 그대로 출력한다.
    FileOutputStream out = new FileOutputStream("temp/utf16be.txt");
    out.write(bytes);
    out.close();
    System.out.println("데이터 출력 완료!");
  }

}


// 데이터 읽기 - String 값 읽기 
package com.eomcs.io.ex04;

import java.io.FileInputStream;

public class Exam0420 {

  public static void main(String[] args) throws Exception {
    FileInputStream in = new FileInputStream("temp/test3.data");

    byte[] buf = new byte[100];

    int count = in.read(buf);

    // Exam0410의 실행 결과로 만들어진 파일을 읽어라!
    // 바이트 배열에 들어있는 값을 사용하여 String 인스턴스를 만든다.
    // new String(바이트배열, 시작번호, 개수, 문자코드표)
    // => 예) new String(buf, 0, 10, "UTF-8")
    //    buf 배열에서 0번부터 10개의 바이트를 꺼낸다.
    //    그 바이트는 UTF-8 코드로 되어 있다.
    //    이 UTF-8 코드 배열을 UCS2 문자 배열로 만들어 String 객체를 리턴한다.
    String str = new String(buf, 0, count, "UTF-8");

    in.close();

    System.out.printf("%s\n", str);
  }
}







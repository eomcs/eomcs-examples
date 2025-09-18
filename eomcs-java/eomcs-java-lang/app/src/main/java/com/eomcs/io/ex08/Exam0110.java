// 포함 관계로 기능 확장하기 - FileInputStream + BufferedInputStream
package com.eomcs.io.ex08;

import java.io.FileInputStream;

public class Exam0110 {

  public static void main(String[] args) throws Exception {

    // 실제 파일을 읽는 일을 수행하는 객체를 준비한다.
    FileInputStream in = new FileInputStream("temp/jls11.pdf");

    // 기존의 FileInputStream에 버퍼 기능을 덧붙이기 위해서
    // 상속 대신 포함하는 방식을 사용한다.
    // 다음 BufferedInputStream 은 FileInputStream을 포함한다.
    // 즉 파일에서 데이터를 읽을 때 FileInputStream을 사용한다.
    BufferedInputStream in2 = new BufferedInputStream(in);

    int b;

    long startTime = System.currentTimeMillis(); // 밀리초

    int callCount = 0;
    while ((b = in2.read()) != -1)
      callCount++; // 파일을 끝까지 읽는다.

    long endTime = System.currentTimeMillis();

    System.out.println(endTime - startTime);
    System.out.println(callCount);

    in2.close();
  }

}

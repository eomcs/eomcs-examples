// 버퍼 사용 전 - 파일 복사 및 시간 측정
package com.eomcs.io.ex06;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Exam0310 {

  public static void main(String[] args) throws Exception {
    FileInputStream in = new FileInputStream("temp/jls.pdf");
    FileOutputStream out = new FileOutputStream("temp/jls_2.pdf");

    int b;

    long startTime = System.currentTimeMillis(); // 밀리초

    int count = 0;
    while ((b = in.read()) != -1) {
      out.write(b);
      count++;
    }

    long endTime = System.currentTimeMillis();

    System.out.println(endTime - startTime);
    System.out.println(count);

    in.close();
    out.close();
  }

}

// BufferedInputStream과 BufferedOutputStream을 사용하여 파일 복사 및 시간 측정
package com.eomcs.io.ex09.step3;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Exam0220 {

  public static void main(String[] args) throws Exception {
    BufferedInputStream in = new BufferedInputStream(
        new FileInputStream("temp/jls11.pdf"));

    BufferedOutputStream out = new BufferedOutputStream(
        new FileOutputStream("temp/jls11_5.pdf"));

    int b;

    long startTime = System.currentTimeMillis(); // 밀리초

    while ((b = in.read()) != -1)
      out.write(b);

    out.flush(); // 버퍼에 남아 있는 데이터를 마저 출력한다.

    long endTime = System.currentTimeMillis();

    System.out.println(endTime - startTime);

    in.close();
    out.close();
  }

}

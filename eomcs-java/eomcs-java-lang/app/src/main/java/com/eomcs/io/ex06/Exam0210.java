package com.eomcs.io.ex06;

import java.io.FileOutputStream;

public class Exam0210 {
  public static void main(String[] args) throws Exception {
    FileOutputStream out = new FileOutputStream("temp/data.bin");

    System.out.println("데이터 쓰는 중...");

    long start = System.currentTimeMillis();

    for (int i = 0; i < 1000000; i++) {
      out.write(0x55);
    }

    long end = System.currentTimeMillis();

    out.close();

    System.out.println("출력 완료!");
    System.out.printf("경과된 시간: %d\n", end - start);
  }
}

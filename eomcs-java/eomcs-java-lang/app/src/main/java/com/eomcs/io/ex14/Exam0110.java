// 파일 입출력과 예외처리 - 예외 처리 전
package com.eomcs.io.ex14;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Exam0110 {
  public static void main(String[] args) throws FileNotFoundException, IOException {
    FileOutputStream out = new FileOutputStream("temp3/data.bin");

    out.write(0x55);

    out.close();

    System.out.println("출력 완료!");
  }
}









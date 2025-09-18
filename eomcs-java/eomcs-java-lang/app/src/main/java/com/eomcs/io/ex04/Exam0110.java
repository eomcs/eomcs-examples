// 데이터 출력 - int 값 출력
package com.eomcs.io.ex04;

import java.io.FileOutputStream;

public class Exam0110 {

  public static void main(String[] args) throws Exception {
    FileOutputStream out = new FileOutputStream("temp/test3.data");

    int money = 1_3456_7890; // = 0x080557d2

    out.write(money); //항상 출력할 때는 맨 끝 1바이트만 출력한다.

    out.close();

    System.out.println("데이터 출력 완료!");

  }

}

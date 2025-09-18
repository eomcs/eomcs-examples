// Byte Stream - 바이트 배열 출력하기
package com.eomcs.io.ex02;

import java.io.FileOutputStream;

public class Exam0210 {

  public static void main(String[] args) throws Exception {

    // 현재 디렉토리는 프로젝트 폴더이다.
    //
    FileOutputStream out = new FileOutputStream("temp/test1.data");

    byte[] bytes = {0x7a, 0x6b, 0x5c, 0x4d, 0x3e, 0x2f, 0x30};

    // write(byte[]) : 배열의 값 전체를 출력한다.
    // write(byte[], 시작인덱스, 출력개수) : 시작 위치부터 지정된 개수를 출력한다.
    //
    out.write(bytes); // 바이트 배열 전체를 출력한다.

    out.close();

    System.out.println("데이터 출력 완료!");

  }

}

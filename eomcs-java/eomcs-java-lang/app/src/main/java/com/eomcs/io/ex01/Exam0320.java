// 디렉토리 생성
package com.eomcs.io.ex01;

import java.io.File;

public class Exam0320 {

  public static void main(String[] args) throws Exception {

    File dir = new File("temp/a");

    // 하위 디렉토리 생성하기
    //
    if (dir.mkdir()) {
      System.out.println("temp/a 디렉토리를 생성하였습니다.");
    } else {
      System.out.println("temp/a 디렉토리를 생성할 수 없습니다.");
    }
  }

}

// 디렉토리 생성
package com.eomcs.io.ex01;

import java.io.File;

public class Exam0321 {

  public static void main(String[] args) throws Exception {

    File dir = new File("temp3/a");

    // 존재하지 않는 폴더 아래에 새 폴더를 만들 때
    // => 존재하지 않는 폴더가 자동 생성되지 않는다.
    // => 따라서 그 하위 폴더를 생성할 수 없다.
    //
    if (dir.mkdir()) {
      System.out.println("temp2/a 디렉토리를 생성하였습니다.");
    } else {
      System.out.println("temp2/a 디렉토리를 생성할 수 없습니다.");
    }
  }

}

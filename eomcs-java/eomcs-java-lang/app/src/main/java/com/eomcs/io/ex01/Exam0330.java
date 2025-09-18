// 디렉토리 삭제
package com.eomcs.io.ex01;

import java.io.File;

public class Exam0330 {

  public static void main(String[] args) throws Exception {

    File dir = new File("temp2");

    if (dir.delete()) {
      System.out.println("temp 디렉토리를 삭제하였습니다.");
    } else {
      // 디렉토리 안에 파일이나 다른 하위 디렉토리가 있다면 삭제할 수 없다.
      // 또한 존재하지 않는 디렉토리도 삭제할 수 없다.
      System.out.println("temp 디렉토리를 삭제할 수 없습니다.");
    }
  }

}

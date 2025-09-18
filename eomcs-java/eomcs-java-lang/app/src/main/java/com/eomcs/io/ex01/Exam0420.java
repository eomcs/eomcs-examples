// 파일 삭제
package com.eomcs.io.ex01;

import java.io.File;

public class Exam0420 {

  public static void main(String[] args) throws Exception {
    // 삭제할 파일의 경로 설정
    File file = new File("temp2/a/test.txt");

    if (file.delete()) { // 파일 삭제
      System.out.println("test.txt 파일을 삭제하였습니다.");
    } else {
      // 존재하지 않는 파일은 삭제할 수 없다.
      // 경로가 존재하지 않으면 당연히 그 경로에 파일이 없으니까 삭제할 수 없다.
      System.out.println("test.txt 파일을 삭제할 수 없습니다.");
    }


  }

}

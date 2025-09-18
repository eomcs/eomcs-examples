// 활용 - 지정한 폴더를 삭제하라.
package com.eomcs.io.ex01;

import java.io.File;

public class Exam0720_03 {

  public static void main(String[] args) throws Exception {

    // temp 디렉토리를 삭제하기
    File dir = new File("temp");

    deleteFile(dir);
  }

  static void deleteFile(File dir) {
    dir.delete(); // 현재 파일이나 폴더를 지우기
  }

}



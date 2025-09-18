// 익명 클래스로 FileFilter 만들기
package com.eomcs.io.ex01;

import java.io.File;
import java.io.FileFilter;

public class Exam0630 {


  public static void main(String[] args) throws Exception {

    // 필터 객체를 한 개만 만들 것이라면
    // 익명 클래스로 정의하는 것이 낫다.
    //
    FileFilter filter = new FileFilter() {
      @Override
      public boolean accept(File file) {
        if (file.isFile() && file.getName().endsWith(".java"))
          return true;
        return false;
      }
    };

    File dir = new File(".");

    File[] files = dir.listFiles(filter);

    for (File file : files) {
      System.out.printf("%s %12d %s\n", file.isDirectory() ? "d" : "-", file.length(),
          file.getName());
    }

  }

}



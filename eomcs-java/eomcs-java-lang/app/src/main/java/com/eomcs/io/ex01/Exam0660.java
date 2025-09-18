// 활용: 필터와 Lambda 표현식을 사용하여 디렉토리 이름만 추출하라.
package com.eomcs.io.ex01;

import java.io.File;

public class Exam0660 {


  public static void main(String[] args) throws Exception {

    File file = new File(".");

    File[] files = file.listFiles(f -> f.isDirectory());

    for (File f : files) {
      System.out.printf("%s %12d %s\n",
          f.isDirectory() ? "d" : "-",
              f.length(),
              f.getName());
    }

  }

}



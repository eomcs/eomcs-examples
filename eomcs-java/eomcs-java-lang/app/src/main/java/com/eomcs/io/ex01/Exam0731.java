// 활용 - 클래스 파일 이름을 출력할 때 패키지 이름을 포함하라.
package com.eomcs.io.ex01;

import java.io.File;
import java.io.FileFilter;

public class Exam0731 {

  public static void main(String[] args) throws Exception {

    // 클래스 이름을 출력할 때 패키지 이름을 포함해야 한다.
    // 예) ch01.Test01
    // 예) ch22.a.Test14
    //
    File dir = new File("bin/main");
    System.out.println(dir.getCanonicalPath());

    printClasses(dir, "");
  }

  static void printClasses(File dir, String packageName) {

    File[] files = dir.listFiles(new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        return pathname.isDirectory() || (pathname.isFile() && pathname.getName().endsWith(".class"));
      }
    });

    if (packageName.length() > 0) {
      packageName += ".";
    }

    for (File file : files) {
      if (file.isDirectory()) {
        printClasses(file, packageName + file.getName());

      } else {
        // path가 파일이면 패키지 이름과 파일 이름을 합쳐 출력하고 리턴한다.
        // => 단 파일 이름에서 .class 확장자 명은 제외한다.
        // => 파일 명이 Hello.class 이고 패키지명이 aaa.bbb 라면
        //    출력할 이름은 aaa.bbb.Hello 이다.
        System.out.println(packageName + file.getName().replace(".class", ""));
      }
    }
  }
}



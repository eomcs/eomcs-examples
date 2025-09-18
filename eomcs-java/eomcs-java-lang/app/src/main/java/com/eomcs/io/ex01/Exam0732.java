// 활용 - 클래스 파일 이름을 출력할 때 패키지 이름을 포함하라. (람다 문법 활용)
package com.eomcs.io.ex01;

import java.io.File;

public class Exam0732 {

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

    // 기존의 익명 클래스를 람다 문법으로 교체한다.
    File[] files = dir.listFiles(f -> f.isDirectory() || (f.isFile() && f.getName().endsWith(".class")));

    if (packageName.length() > 0) {
      packageName += ".";
    }

    for (File file : files) {
      if (file.isDirectory()) {
        printClasses(file, packageName + file.getName());

      } else {
        System.out.println(packageName + file.getName().replace(".class", ""));
      }
    }
  }
}



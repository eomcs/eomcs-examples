// 람다로 FileFilter 만들기
package com.eomcs.io.ex01;

import java.io.File;

public class Exam0651 {


  public static void main(String[] args) throws Exception {

    File dir = new File(".");

    // 메서드 한 개짜리 인터페이스인 경우
    // 람다(lambda) 문법을 사용하면 훨씬 더 간결하게 코드를 작성할 수 있다.
    // => expression 은 return 문장을 생략할 수 있다.
    File[] files = dir.listFiles(file -> file.isFile() && file.getName().endsWith(".java"));

    for (File file : files) {
      System.out.printf("%s %12d %s\n", file.isDirectory() ? "d" : "-", file.length(),
          file.getName());
    }

  }

}



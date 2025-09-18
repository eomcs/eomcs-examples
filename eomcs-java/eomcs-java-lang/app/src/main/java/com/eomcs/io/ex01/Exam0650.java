// 람다로 FileFilter 만들기
package com.eomcs.io.ex01;

import java.io.File;

public class Exam0650 {


  public static void main(String[] args) throws Exception {

    File dir = new File(".");

    // 메서드 한 개짜리 인터페이스인 경우
    // 람다(lambda) 문법을 사용하면 훨씬 더 간결하게 코드를 작성할 수 있다.
    //
    // lambda class
    // => 메서드가 한 개짜리인 경우 lambda 표현식을 사용할 수 있다.
    // => 문법:
    //      (파라미터, 파라미터, ...) -> 문장 한개
    //      (파라미터, 파라미터, ...) -> { 문장1; 문장2; 문장3;}
    //      () -> 문장 한개
    //      () -> {문장1; 문장2; 문장3;}
    //
    File[] files = dir.listFiles(file -> {
      return file.isFile() && file.getName().endsWith(".java");
    });

    for (File file : files) {
      System.out.printf("%s %12d %s\n", file.isDirectory() ? "d" : "-", file.length(),
          file.getName());
    }

  }

}



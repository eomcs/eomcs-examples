// 디렉토리에 들어있는 파일(디렉토리) 목록을 꺼낼 때 필터 적용하기
package com.eomcs.io.ex01;

import java.io.File;
import java.io.FilenameFilter;

public class Exam0611 {


  public static void main(String[] args) throws Exception {

    class JavaFilter implements FilenameFilter {
      @Override
      public boolean accept(File dir, String name) {

        // 해당 이름이 디렉토리 이름인지 파일 이름인지 알아내려면
        // File 객체를 생성해야 한다.
        //
        File file = new File(dir, name);
        // 디렉토리 정보와 이름을 합쳐 파일 객체를 생성할 수 있다.

        return file.isFile() && name.endsWith(".java");
      }
    }

    File dir = new File(".");

    // => 확장자가 .java 인 파일의 이름만 추출하기
    // 1) 필터 준비
    JavaFilter javaFilter = new JavaFilter();

    // 2) 필터를 사용하여 디렉토리의 목록을 가져오기
    String[] names = dir.list(javaFilter);

    for (String name : names) {
      System.out.println(name);
    }

  }

}



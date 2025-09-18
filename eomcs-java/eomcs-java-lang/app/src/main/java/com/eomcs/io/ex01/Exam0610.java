// 디렉토리에 들어있는 파일(디렉토리) 목록을 꺼낼 때 필터 적용하기
package com.eomcs.io.ex01;

import java.io.File;
import java.io.FilenameFilter;

public class Exam0610 {


  public static void main(String[] args) throws Exception {

    class JavaFilter implements FilenameFilter {
      @Override
      public boolean accept(File dir/* 부모 경로 */, String name /* 파일,디렉토리 이름 */) {
        // 이 메서드는 list()에서 호출한다.
        // 해당 폴더에 들어 있는 파일이나 디렉토리를 찾을 때 마다 호출한다.
        // (하위 폴더 아래는 뒤지지 않는다)
        // 이 메서드에서 해야 할 일은 찾은 파일이나 디렉토리를
        // 리턴할 배열에 포함시킬지 여부이다.
        // true를 리턴하면 배열에 포함되고,
        // false를 리턴하면 배열에 포함되지 않는다.

        // 파일,디렉토리 이름이 .java 로 끝나는 경우만 리턴 배열에 포함시키다.
        return name.endsWith(".java");
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

    // 문제점:
    // - temp.java 는 디렉토리이다.
    // - 현재의 필터는 파일 이름으로만 검사한다.
    // - 파일인지 디렉토리인지 여부는 검사하지 않는다.
    // - 해결책?
    //   다음 예제를 보라!
  }

}



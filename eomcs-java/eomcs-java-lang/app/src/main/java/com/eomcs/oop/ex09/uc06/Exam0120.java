// 인터페이스 : callee를 만드는 입장
package com.eomcs.oop.ex09.uc06;

import java.io.File;
import java.io.FilenameFilter;

public class Exam0120 {
  public static void main(String[] args) {
    File file = new File(".");

    // 인터페이스를 기준으로 한 개발자 입장:
    // => File의 list() 메서드가 사용할 필터 객체를 만드는 입장이다.
    // => 개발자는 FilenameFilter 인터페이스 규칙 따라 클래스를 작성한다.
    // => 이렇게 작성한 클래스는  list() 메서드에서 사용할 것이다.
    class MyFilter implements FilenameFilter {
      @Override
      public boolean accept(File dir, String name) {
        return name.endsWith(".java") ? true : false;
      }
    }

    String[] filenames = file.list(new MyFilter());

    for (String filename : filenames) {
      System.out.println(filename);
    }
  }
}

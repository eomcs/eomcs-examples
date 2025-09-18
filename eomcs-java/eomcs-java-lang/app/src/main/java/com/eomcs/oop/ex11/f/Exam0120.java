// FilenameFilter 를 정의하는 다양한 방법 : 중첩 클래스(nested class) 사용
package com.eomcs.oop.ex11.f;

import java.io.File;
import java.io.FilenameFilter;

public class Exam0120 {

  // 클래스 안에 정의된 클래스를 "중첩 클래스(nested class)"라 한다.
  // => 특정 클래스 안에서만 사용될 클래스라면 그 클래스 내부에 선언함으로써
  //    유지보수를 편리하게 만든다.
  // => 패키지 멤버 클래스로 선언하면 같은 이름으로 패키지 멤버를 중복해서 만들 수 없지만,
  //    클래스 안에 선언하면, 같은 이름으로 자신만의 기능을 수행하는
  //    중첩 클래스를 여러 클래스 안에 만들 수 있다.
  // 1) static nested class
  // 2) non-static nested class = inner class

  // static nested class
  // => 바깥 클래스의 인스턴스를 사용하지 않는다면 static nested class 로 정의하라!
  // => 인스턴스 필드를 사용하지 않는 메서드를 스태틱으로 선언하는 것과 같다.
  //
  static class MyFilenameFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String name) {
      if (name.endsWith(".java"))
        return true;
      return false;
    }
  }

  public static void main(String[] args) throws Exception {
    File dir = new File(".");

    // 스태틱 중첩 클래스의 인스턴스를 생성한다.
    MyFilenameFilter filter = new MyFilenameFilter();

    // 스태틱 중첩 클래스의 인스턴스를 필터로 사용하여 디렉토리 안의 목록을 추출한다.
    String[] names = dir.list(filter);
    for (String name : names) {
      System.out.println(name);
    }

    // 만약 main() 메서드와 필터를 정의한 클래스 사이에 많은 코드가 있다면
    // 이전과 마찬가지로 역시 소스 코드를 살펴보기가 불편하다.

  }

}








// FilenameFilter 를 정의하는 다양한 방법 : non-static 중첩 클래스(nested class) 사용
package com.eomcs.oop.ex11.f;

import java.io.File;
import java.io.FilenameFilter;

public class Exam0130 {

  // non-static nested class = inner class
  // => 스태틱이 붙지 않은 중첩 클래스. 결국 인스턴스 멤버이다.
  // => 이 클래스는 인스턴스가 있어야만 사용할 수 있다.
  // => 보통 인스턴스 멤버를 사용하는 중첩 클래스를 만들 때 inner class로 정의한다.
  // => 당연히 inner 클래스는 인스턴스 메서드에서 주로 사용할 것이다.
  //

  // inner class = non-static nested class
  // => 바깥 클래스의 인스턴스를 사용하는 경우 inner class로 정의하라.
  // => 인스턴스 필드를 사용하는 메서드에 대해 인스턴스 메서드로 선언하는 것과 같다.
  //
  class MyFilenameFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String name) {
      if (name.endsWith(".java"))
        return true;
      return false;
    }
  }

  public static void main(String[] args) throws Exception {

    File dir = new File(".");

    // inner 클래스의 인스턴스를 생성한다.
    // - 바깥 클래스의 인스턴스가 있어야만
    //   inner 클래스의 인스턴스를 생성할 수 있다.
    Exam0130 outer = new Exam0130();

    MyFilenameFilter filter = outer.new MyFilenameFilter();

    String[] names = dir.list(filter);
    for (String name : names) {
      System.out.println(name);
    }

    // 스태틱 중첩 클래스와 마찬가지로
    // main() 메서드와 필터를 정의한 클래스 사이에 많은 코드가 있다면
    // 이전과 마찬가지로 역시 소스 코드를 살펴보기가 불편하다.
  }

}








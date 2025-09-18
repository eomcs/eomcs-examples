// FilenameFilter 를 정의하는 다양한 방법
package com.eomcs.oop.ex11.f;

import java.io.File;

// 물론 Exam0110 또한 패키지 멤버 클래스이다.
public class Exam0100 {

  public static void main(String[] args) throws Exception {
    // 현재 폴더에 들어있는 하위 폴더 및 파일들의 이름을 출력하라!
    // 현재 폴더?
    // => 이클립스에서 소스를 실행할 경우 현재 폴더는 프로젝트 폴더이다.

    // => File 클래스를 이용하여 현재 폴더의 정보를 알아낸다.
    //    File 클래스는 OS의 기능을 이용하여 폴더 및 파일 정보를 다루는 일을 한다.
    // => File 클래스는 java.io 패키지에 소속된 멤버 클래스이다.
    //
    File dir = new File(".");

    // => File 객체를 통해 지정된 위치에 있는 폴더나 파일의 이름을 알아낸다.
    String[] names = dir.list();
    for (String name : names) {
      System.out.println(name);
    }

  }

}








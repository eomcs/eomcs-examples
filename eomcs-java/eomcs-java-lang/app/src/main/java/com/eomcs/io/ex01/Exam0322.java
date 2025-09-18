// 디렉토리 생성
package com.eomcs.io.ex01;

import java.io.File;

public class Exam0322 {

  public static void main(String[] args) throws Exception {

    File dir = new File("temp3/a/b");

    // 디렉토리를 생성할 때 존재하지 않는 중간 디렉토리도 만들고 싶다면,
    // mkdirs()를 호출하라.
    //
    // mkdirs()
    // - 지정된 경로에 디렉토리가 존재하지 않으면 그 디렉토리도 만든다.
    //
    if (dir.mkdirs()) {
      System.out.println("temp2/a/b 디렉토리를 생성하였습니다.");
    } else {
      System.out.println("temp2/a/b 디렉토리를 생성할 수 없습니다.");
    }


  }

}

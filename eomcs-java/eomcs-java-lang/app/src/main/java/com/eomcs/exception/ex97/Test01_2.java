// 예외 처리 연습 - try ~ catch ~ 로 예외 처리하기
package com.eomcs.exception.ex97;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Test01_2 {

  public static void main(String[] args) {
    
    FileReader in;
    
    try {
      in = new FileReader("build.gradle");
    } catch (FileNotFoundException e) {
      System.out.println("해당 파일을 찾을 수 없습니다!");
    }

  }

}

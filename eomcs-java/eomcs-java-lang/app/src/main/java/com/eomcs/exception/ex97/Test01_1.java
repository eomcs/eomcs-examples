// 예외 처리 연습 - 예외 처리 안했을 때
package com.eomcs.exception.ex97;

import java.io.FileReader;

public class Test01_1 {

  public static void main(String[] args) {
    
    FileReader in;
    
    in = new FileReader("build.gradle"); // 컴파일 오류!
    // Exception 예외인 경우 반드시 예외 처리를 요구한다.
    // (RuntimeException은 제외)

  }

}

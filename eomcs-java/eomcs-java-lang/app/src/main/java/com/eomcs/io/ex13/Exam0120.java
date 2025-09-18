// 파일에 출력하기 - Stream API를 사용하여 데이터 출력하기
package com.eomcs.io.ex13;

import java.io.FileOutputStream;

public class Exam0120 {
  public static void main(String[] args) {
    // 1 ~ 100 중에서 짝수만 파일에 출력해 보자!
    
    //1) 값을 출력할 파일 출력 객체 준비
    try (FileOutputStream out = new FileOutputStream("temp/data.bin")) {
      
      //2) 짝수를 파일에 출력한다.
      for (int i = 1; i <= 100; i++) {
        if (i % 2 == 0)
          out.write(i);
      }
      
      System.out.println("출력 완료!");
      
    } catch (Exception e) {
      System.out.println("파일 출력 예외 발생!");
      e.printStackTrace();
    }
  }
}









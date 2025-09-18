// 파일에 출력하기 - Stream API를 사용하여 데이터 출력하기
package com.eomcs.io.ex13;

import java.io.FileWriter;

public class Exam0220 {
  public static void main(String[] args) {
    // 문자를 '가'에서 부터 100자 출력한다.
    
    //1) 문자를 출력할 파일 출력 객체 준비
    try (FileWriter out = new FileWriter("temp/data.txt")) {
      
      //2) '가'에서 시작하여 다음 문자를 100자 출력한다.
      for (int i = 0, ch = '가'; i < 100; i++, ch++) {
        out.write((char)(ch));
      }
      
      System.out.println("출력 완료!");
      
    } catch (Exception e) {
      System.out.println("파일 출력 예외 발생!");
      e.printStackTrace();
    }
  }
}









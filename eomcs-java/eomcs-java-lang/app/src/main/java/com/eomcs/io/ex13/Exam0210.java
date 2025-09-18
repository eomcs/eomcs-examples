// 스트링 버퍼 객체에 출력하기 - 직접 스트링 버퍼를 다루는 경우
package com.eomcs.io.ex13;

public class Exam0210 {
  public static void main(String[] args) throws Exception {
    // 문자를 '가'에서 부터 100자 출력한다.
    
    //1) 값을 저장할 스트링 버퍼를 준비한다.
    StringBuffer sb = new StringBuffer();
    
    //2) '가'에서 시작하여 다음 문자를 100자 출력한다.
    for (int i = 0, ch = '가'; i < 100; i++, ch++) {
      sb.append((char)(ch));
    }
    
    //3) 스트링 버퍼에 저장된 값을 확인해보자!
    System.out.println(sb.toString());
    
    System.out.println();
  }
}









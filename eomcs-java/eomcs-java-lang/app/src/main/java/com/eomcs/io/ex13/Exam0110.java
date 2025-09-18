// 바이트 배열 객체에 출력하기 - 직접 바이트 배열을 다루는 경우
package com.eomcs.io.ex13;

public class Exam0110 {
  public static void main(String[] args) throws Exception {
    // 1 ~ 100 중에서 짝수만 바이트 배열에 넣어 보자!
    
    //1) 값을 저장할 바이트 배열을 준비한다.
    byte[] bytes = new byte[1000];
    
    //2) 바이트 배열에 저장된 개수를 관리할 변수를 준비한다.
    int size = 0;
    
    //3) 짝수를 바이트 배열에 출력한다.
    for (int i = 1; i <= 100; i++) {
      if (i % 2 == 0)
        bytes[size++] = (byte) i;
    }
    
    //4) 바이트 배열에 저장된 값을 확인해보자!
    for (int i = 0; i < size; i++) {
      System.out.print(bytes[i] + ",");
    }
    System.out.println();
  }
}









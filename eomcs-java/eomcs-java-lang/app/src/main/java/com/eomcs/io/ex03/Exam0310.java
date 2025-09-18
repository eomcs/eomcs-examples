// Character Stream - 문자 배열의 특정 부분을 출력하기 
package com.eomcs.io.ex03;

import java.io.FileWriter;

public class Exam0310 {

  public static void main(String[] args) throws Exception {
    FileWriter out = new FileWriter("temp/test2.txt");

    char[] chars = new char[] {'A','B','C','가','각','간','똘','똥'}; 

    out.write(chars, 2, 3); // 2번 문자부터 3 개의 문자를 출력한다.

    out.close();

    System.out.println("데이터 출력 완료!");

  }

}

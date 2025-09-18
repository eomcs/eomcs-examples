// Character Stream - char[] ==> String 변환하기
package com.eomcs.io.ex03;

import java.io.FileReader;

public class Exam0221 {

  public static void main(String[] args) throws Exception {
    FileReader in = new FileReader("temp/test2.txt");

    char[] buf = new char[100];

    int count = in.read(buf); // char 배열에 담을 때 UTF-16BE 코드 값으로 변환한다.
    String str = new String(buf, 0, count); // 그래서 String 객체를 만들 때 문자집합을 지정할 필요가 없다.

    in.close();

    System.out.printf("[%s]\n", str);
  }

}





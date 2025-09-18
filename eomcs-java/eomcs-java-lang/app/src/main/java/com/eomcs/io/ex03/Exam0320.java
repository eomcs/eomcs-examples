// Character Stream - 읽은 데이터를 문자 배열의 특정 위치에 저장하기
package com.eomcs.io.ex03;

import java.io.FileReader;

public class Exam0320 {

  public static void main(String[] args) throws Exception {
    FileReader in = new FileReader("temp/test2.txt");

    char[] buf = new char[100];

    // read(버퍼의주소, 저장할위치, 읽을바이트개수)
    // => 리턴 값은 실제 읽은 문자의 개수이다.
    int count = in.read(buf, 10, 40); // 40개의 문자를 읽어 10번 방부터 저장한다.

    in.close();

    System.out.printf("%d\n", count);

    for (int i = 0; i < 20; i++)
      System.out.printf("%c(%04x) \n", buf[i], (int) buf[i]);

    System.out.println();

  }

}



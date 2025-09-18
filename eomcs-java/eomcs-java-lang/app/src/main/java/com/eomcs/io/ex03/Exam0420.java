// Character Stream - 텍스트 읽기
package com.eomcs.io.ex03;

import java.io.FileReader;
import java.nio.CharBuffer;

public class Exam0420 {

  public static void main(String[] args) throws Exception {
    FileReader in = new FileReader("temp/test2.txt");

    // FileReader 객체가 읽을 데이터를 저장할 메모리를 준비한다.
    CharBuffer charBuf = CharBuffer.allocate(100);

    // 읽은 데이터를 CharBuffer 에 저장한다.
    int count = in.read(charBuf);

    in.close();

    System.out.printf("%d\n", count);

    // 버퍼의 데이터를 꺼내기 전에 읽은 위치를 0으로 초기화시킨다.
    // - read() 메서드가 파일에서 데이터를 읽어서 버퍼에 채울 때 마다 커서의 위치는 다음으로 이동한다.
    // - 버퍼의 데이터를 읽으려면 커서의 위치를 처음으로 되돌려야 한다.(flip)
    // - flip() 메서드를 호출하여 커서를 처음으로 옮긴다. 그런 후에 버퍼의 텍스를 읽어야 한다.
    charBuf.flip();

    System.out.printf("[%s]\n", charBuf.toString());
  }

}



// Byte Stream - 바이트 배열 읽기
package com.eomcs.io.ex02;

import java.io.FileInputStream;

public class Exam0220 {

  public static void main(String[] args) throws Exception {

    FileInputStream in = new FileInputStream("temp/test1.data");

    // 바이트들을 저장할 배열을 넉넉히 준비한다.
    // => 이렇게 임시 데이터를 저장하기 위해 만든 바이트 배열을 보통 "버퍼(buffer)"라 한다.
    //
    byte[] buf = new byte[100];

    // read(byte[])
    // - 버퍼가 꽉 찰 때까지 읽는다.
    // - 물론 버퍼 크기보다 파일의 데이터가 적으면 파일을 모두 읽어 버퍼에 저장한다.
    // - 리턴 값은 읽은 바이트의 개수이다.
    // read(byte[], 저장할 위치, 저장하기를 희망하는 개수)
    // - 읽은 데이터를 "저장할 위치"에 지정된 방부터 개수만큼 저장한다.
    // - 리턴 값은 실제 읽은 바이트 개수이다.
    int count = in.read(buf);

    in.close();

    System.out.printf("읽은 바이트 수: %d\n", count);

    for (int i = 0; i < count; i++)
      System.out.printf("%02x ", buf[i]);

    System.out.println();
  }

}



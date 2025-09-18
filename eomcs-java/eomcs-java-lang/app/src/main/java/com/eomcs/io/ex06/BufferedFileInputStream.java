package com.eomcs.io.ex06;

import java.io.FileInputStream;
import java.io.IOException;

public class BufferedFileInputStream extends FileInputStream {

  byte[] buf = new byte[8192];
  int size; // 배열에 저장되어 있는 바이트의 수
  int cursor; // 바이트 읽은 배열의 위치

  public BufferedFileInputStream(String filename) throws Exception {
    super(filename);
  }

  // 파일에서 버퍼로 왕창 읽어 온 횟수
  int readCount = 0;

  // 버퍼를 사용하는 서브 클래스의 특징에 맞춰서
  // 상속 받은 메서드를 재정의 한다.
  @Override
  public int read() throws IOException {
    if (size == -1 || cursor == size) { // 바이트 배열에 저장되어 있는 데이터를 모두 읽었다면,
      if ((size = super.read(buf)) == -1) { // 다시 파일에서 바이트 배열로 데이터를 왕창 읽어 온다.
        return -1;
      }
      readCount++;
      System.out.printf("==>버퍼로 왕창 읽었음! - %d 번째\n", readCount);
      cursor = 0;
    }

    return buf[cursor++] & 0x000000ff;

    // 위의 리턴 문장은 컴파일 할 때 아래의 문장으로 바뀐다.
    //    int temp;
    //    temp = buf[cursor];
    //    cursor++;
    //    return temp & 0x000000ff;

  }
}



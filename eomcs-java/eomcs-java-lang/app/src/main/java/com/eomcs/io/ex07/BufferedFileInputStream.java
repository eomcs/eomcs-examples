package com.eomcs.io.ex07;

import java.io.FileInputStream;
import java.io.IOException;

public class BufferedFileInputStream extends FileInputStream {
  byte[] buf = new byte[8192];
  int size; // 배열에 저장되어 있는 바이트의 수
  int cursor; // 바이트 읽은 배열의 위치

  public BufferedFileInputStream(String filename) throws Exception {
    super(filename);
  }

  @Override
  public int read() throws IOException {
    if (cursor == size) { // 버퍼에 저장되어 있는 데이터를 모두 읽었다는 의미
      if ((size = super.read(buf)) == -1) { // 파일에서 데이터를 읽으려 했는데 데이터가 없다.
        return -1;
      }
      cursor = 0;
    }
    return buf[cursor++] & 0x000000ff;
  }

  @Override
  public int read(byte[] buf) throws IOException {
    int i = 0;
    for (; i < buf.length; i++) {
      // 1바이트를 읽어서 파라미터로 받은 바이트 배열에 채운다.
      int b = this.read();
      if (b == -1) {
        // 바이트 배열을 다 채우기도 전에 읽을 데이터가 없다면 읽기를 멈춘다.
        break;
      }
      buf[i] = (byte) b;
    }
    return i; // 지금까지 읽은 데이터의 수를 리턴한다.
  }
}



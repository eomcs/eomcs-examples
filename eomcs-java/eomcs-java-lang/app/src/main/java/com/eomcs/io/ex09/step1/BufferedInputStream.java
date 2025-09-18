// 버퍼 기능을 입출력 플러그인/장신구(decorator)로 전환한다.
package com.eomcs.io.ex09.step1;

import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStream extends InputStream {

  InputStream 연결부품;

  byte[] buf = new byte[8196];
  int size; // 배열에 저장되어 있는 바이트의 수
  int cursor; // 바이트 읽은 배열의 위치

  public BufferedInputStream(InputStream in) {
    연결부품 = in; // 이 객체와 연결될 부품을 파라미터로 받는다.
  }

  @Override
  public int read() throws IOException {
    if (cursor == size) { // 버퍼에 저장되어 있는 데이터를 모두 읽었다는 의미
      if ((size = 연결부품.read(buf)) == -1) { // 파일에서 데이터를 읽으려 했는데 데이터가 없다.
        return -1;
      }
      cursor = 0;
    }
    return buf[cursor++] & 0x000000ff;
  }
}



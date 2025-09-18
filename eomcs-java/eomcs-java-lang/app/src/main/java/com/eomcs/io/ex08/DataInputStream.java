package com.eomcs.io.ex08;

import java.io.IOException;
import java.io.InputStream;

public class DataInputStream {
  // FileInputStream 이나 ByteArrayInputStream 을 포함하는 레퍼런스
  InputStream in;

  public DataInputStream(InputStream in) {
    this.in = in;
  }

  public String readUTF() throws Exception {
    // 생성자에서 받은 InputStream 객체의 read() 메서드를 사용하여 문자열  값을 읽어서 리턴한다.
    int size = in.read();
    byte[] bytes = new byte[size];
    in.read(bytes); // 배열 개수 만큼 바이트를 읽어 배열에 저장한다.
    return new String(bytes, "UTF-8");
  }

  public int readInt() throws Exception {
    // 생성자에서 받은 InputStream 객체의 read() 메서드를 사용하여 int 값을 읽어서 리턴한다.
    int value = 0;

    value = in.read() << 24;
    value += in.read() << 16;
    value += in.read() << 8;
    value += in.read();
    return value;
  }

  public long readLong() throws Exception {
    // 생성자에서 받은 InputStream 객체의 read() 메서드를 사용하여 long 값을 읽어서 리턴한다.
    long value = 0;
    value += (long) in.read() << 56;
    value += (long) in.read() << 48;
    value += (long) in.read() << 40;
    value += (long) in.read() << 32;
    value += (long) in.read() << 24;
    value += (long) in.read() << 16;
    value += (long) in.read() << 8;
    value += in.read();
    return value;
  }

  public boolean readBoolean() throws Exception {
    // 생성자에서 받은 InputStream 객체의 read() 메서드를 사용하여 boolean 값을 읽어서 리턴한다.
    if (in.read() == 1)
      return true;
    else
      return false;
  }

  public void close() throws IOException {
    // 생성자에서 받은 InputStream 객체의 close() 메서드를 사용하여 close() 한다.
    in.close();
  }
}

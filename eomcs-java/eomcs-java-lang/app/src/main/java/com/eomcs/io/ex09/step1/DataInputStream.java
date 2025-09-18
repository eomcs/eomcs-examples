// DataInputStream을 FileInputStream이나 ByteArrayInputStream에 붙일 수 있도록
// 기능을 변경한다. 즉 DataInputStream을 장신구(decorator)로 만든다.
package com.eomcs.io.ex09.step1;

import java.io.IOException;
import java.io.InputStream;

public class DataInputStream extends InputStream {

  InputStream 연결부품;

  public DataInputStream(InputStream in) {
    연결부품 = in; 
  }

  @Override
  public int read() throws IOException {
    // InputStream 의 read() 는 추상 메서드이다.
    // 따라서 서브 클래스에서 반드시 구현해야 한다.
    // 물론 이 클래스는 저장소에서 데이터를 읽는 일을 하지 않기 때문에 
    // 그 일은 다음과 같이 연결부품에게 위임한다.
    // 
    return 연결부품.read();
  }

  public String readUTF() throws Exception {
    // 이 객체는 데이터를 읽는 일을 하지 않는다.
    // 다만 연결된 부품을 사용하여 데이터를 읽어서
    // 중간에서 String 객체로 가공하는 일을 한다.
    // => 이런 일을 하는 객체가 "Decorator" 이다.
    // => 자바에서는 이런 일을 하는 입출력 클래스를
    // "Data Processing Stream Class"라고 부른다.
    //
    int size = 연결부품.read();
    byte[] bytes = new byte[size];
    연결부품.read(bytes);
    return new String(bytes, "UTF-8");
  }

  public int readInt() throws Exception {
    // 연결된 부품을 통해 읽은 데이터를
    // int 값으로 가공하는 일을 한다.
    //
    int value = 0;

    value = 연결부품.read() << 24;
    value += 연결부품.read() << 16;
    value += 연결부품.read() << 8;
    value += 연결부품.read();
    return value;
  }

  public long readLong() throws Exception {
    // 연결된 부품을 통해 읽은 데이터를
    // long 값으로 가공하는 일을 한다.
    //
    long value = 0;
    value += (long) 연결부품.read() << 56;
    value += (long) 연결부품.read() << 48;
    value += (long) 연결부품.read() << 40;
    value += (long) 연결부품.read() << 32;
    value += (long) 연결부품.read() << 24;
    value += (long) 연결부품.read() << 16;
    value += (long) 연결부품.read() << 8;
    value += 연결부품.read();
    return value;
  }

  public boolean readBoolean() throws Exception {
    // 연결된 부품을 통해 읽은 데이터를
    // boolean 값으로 가공하는 일을 한다.
    //
    if (연결부품.read() == 1)
      return true;
    else
      return false;
  }
}



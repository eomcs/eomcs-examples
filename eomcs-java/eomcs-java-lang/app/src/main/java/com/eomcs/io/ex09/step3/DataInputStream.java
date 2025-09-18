// DataInputStream을 FileInputStream이나 ByteArrayInputStream에 붙일 수 있도록
// 기능을 변경한다. 즉 DataInputStream을 장신구(decorator)로 만든다.
package com.eomcs.io.ex09.step3;

import java.io.InputStream;

public class DataInputStream extends DecoratorInputStream {

  public DataInputStream(InputStream in) {
    super(in); // 다른 장식품과 연결하기 위해 수퍼 클래스의 생성자를 호출한다.
  }

  public String readUTF() throws Exception {
    // 이 객체는 데이터를 읽는 일을 하지 않는다.
    // 다만 연결된 부품을 사용하여 데이터를 읽어서
    // 중간에서 String 객체로 가공하는 일을 한다.
    // => 이런 일을 하는 객체가 "Decorator" 이다.
    // => 자바에서는 이런 일을 하는 입출력 클래스를
    // "Data Processing Stream Class"라고 부른다.
    //
    int size = 연결된부품.read();
    byte[] bytes = new byte[size];
    연결된부품.read(bytes);
    return new String(bytes, "UTF-8");
  }

  public int readInt() throws Exception {
    // 연결된 부품을 통해 읽은 데이터를
    // int 값으로 가공하는 일을 한다.
    //
    int value = 0;

    value = 연결된부품.read() << 24;
    value += 연결된부품.read() << 16;
    value += 연결된부품.read() << 8;
    value += 연결된부품.read();
    return value;
  }

  public long readLong() throws Exception {
    // 연결된 부품을 통해 읽은 데이터를
    // long 값으로 가공하는 일을 한다.
    //
    long value = 0;
    value += (long) 연결된부품.read() << 56;
    value += (long) 연결된부품.read() << 48;
    value += (long) 연결된부품.read() << 40;
    value += (long) 연결된부품.read() << 32;
    value += (long) 연결된부품.read() << 24;
    value += (long) 연결된부품.read() << 16;
    value += (long) 연결된부품.read() << 8;
    value += 연결된부품.read();
    return value;
  }

  public boolean readBoolean() throws Exception {
    // 연결된 부품을 통해 읽은 데이터를
    // boolean 값으로 가공하는 일을 한다.
    //
    if (연결된부품.read() == 1)
      return true;
    else
      return false;
  }
}



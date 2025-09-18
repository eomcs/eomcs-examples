package com.eomcs.io.ex09.step3;

import java.io.IOException;
import java.io.InputStream;

public abstract class DecoratorInputStream extends InputStream {
  InputStream 연결된부품;

  public DecoratorInputStream(InputStream 부품) {
    // 장식품처럼 붙였다 뗐다 할 수 있는 기능을 수행하는 객체는
    // 다른 장식품과 연결할 수 있도록 생성자에서 그 객체의 주소를 받아야 한다.
    this.연결된부품 = 부품;
  }

  @Override
  public int read() throws IOException {
    // read() 메서드가 호출되면,
    // 이 장식품과 연결된 다른 부품의 read()를 실행한다.
    return 연결된부품.read();
  }

  @Override
  public void close() throws IOException {
    연결된부품.close();
  }
}

package com.eomcs.io.ex09.step2;

import java.io.IOException;
import java.io.InputStream;

// Generalization
// - DataInputStream과  BufferedInputStream의 공통 분모를 뽑아 수퍼 클래스로 정의한다.
// - 이 클래스를 서브 클래스에게 공통 분모를 상속해주는 역할을 한다.
// - 직접 이 클래스를 사용하지는 않기 때문에 추상 클래스로 선언한다.
public abstract class DecoratorInputStream extends InputStream {

  InputStream 연결부품;

  public DecoratorInputStream(InputStream 부품) {
    // 장식품처럼 붙였다 뗐다 할 수 있는 기능을 수행하는 객체는
    // 다른 장식품과 연결할 수 있도록 생성자에서 그 객체의 주소를 받아야 한다.
    this.연결부품 = 부품;
  }

  @Override
  public int read() throws IOException {
    // read() 메서드가 호출되면,
    // 이 장식품과 연결된 다른 부품의 read()를 실행한다.
    return 연결부품.read();
  }

  @Override
  public void close() throws IOException {
    연결부품.close();
  }
}

package com.eomcs.io.ex06;

import java.io.FileOutputStream;
import java.io.IOException;

public class BufferedFileOutputStream extends FileOutputStream {
  byte[] buf = new byte[8192];
  int cursor;

  public BufferedFileOutputStream(String filename) throws Exception {
    super(filename);
  }

  // 오버라이딩: 상속 받은 메서드를 서브 클래스의 역할에 맞춰서 재정의 한다.
  // 즉 버퍼를 사용하는 특징에 맞춰서 데이터를 파일에 출력하도록 변경한다.
  @Override
  public void write(int b) throws IOException {
    // 1바이트 출력하라고 하면 일단 버퍼에 저장할 것이다.
    buf[cursor++] = (byte) b;

    if (cursor == buf.length) { // 버퍼가 다차면
      super.write(buf); // 버퍼에 들어있는 데이터를 한 번에 출력한다.
      cursor = 0; // 다시 커서를 초기화시킨다.
    }
  }

  // 버퍼를 사용할 때는 특히 주의해야 한다.
  // 버퍼가 꽉 찼을 때만 파일로 내보내기 때문에
  // 버퍼에 잔여 데이터가 남아 있을 수 있다.
  // 버퍼의 잔여 데이터를 강제로 출력하도록 상속 받은 다음 메서드를 재정의 한다.
  @Override
  public void flush() throws IOException {
    if (cursor > 0) {
      this.write(buf, 0, cursor);
      cursor = 0;
    }
  }

  // 항상 입출력 스트림을 사용한 다음에는 자원 해제를 위해 close()를 호출해야 한다.
  // close()가 호출될 때 버퍼의 잔여 데이터를 내보내도록 상속 받은 메서드를 재정의 한다.
  @Override
  public void close() throws IOException {
    this.flush();
    super.close();
  }

}



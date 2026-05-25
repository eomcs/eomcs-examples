package com.eomcs.cleancode.ch05.exam02;

import java.io.IOException;
import java.io.InputStream;

// 예제 4: 가짜 범위 (Dummy Scopes)
public class BadAndGood4 {

  private BadAndGood4() {}

  // Bad: 세미콜론이 while 문의 본문이다.
  // - 세미콜론이 같은 줄 끝에 붙어 있어 실수인지 의도인지 구분하기 어렵다.
  static class BadInputDrainer {
    void drainInput(InputStream input) throws IOException {
      while (input.read() != -1);
    }
  }

  // Good1: 세미콜론을 새 행에 들여써서 눈에 띄게 한다.
  // - 빈 본문임을 명시적으로 보여준다.
  static class GoodInputDrainer1 {
    void drainInput(InputStream input) throws IOException {
      while (input.read() != -1)
        ;
    }
  }

  // Good2: 빈 블록으로 표현하면 세미콜론 실수를 방지할 수 있다.
  // - 하지만 여전히 의도가 약하다.
  static class GoodInputDrainer2 {
    void drainInput(InputStream input) throws IOException {
      while (input.read() != -1) {
      }
    }
  }

  // Good3: 의미 있는 함수로 추출하면 의도가 가장 명확해진다.
  // - hasMoreInput(), discardCurrentInput() 이름만으로 동작이 이해된다.
  static class GoodInputDrainer3 {
    void drainInput(InputStream input) throws IOException {
      while (hasMoreInput(input)) {
        discardCurrentInput(input);
      }
    }

    private boolean hasMoreInput(InputStream input) throws IOException {
      return input.read() != -1;
    }

    private void discardCurrentInput(InputStream input) {
      // intentionally empty — read already consumed the byte
    }
  }

  // Good4: 정말 빈 블록이 필요하다면 주석으로 의도를 명시한다.
  // - 읽는 사람이 누락이 아니라 의도임을 바로 알 수 있다.
  static class GoodInputDrainer4 {
    void drainInput(InputStream input) throws IOException {
      while (input.read() != -1) {
        // intentionally empty
      }
    }
  }
}

package com.eomcs.cleancode.ch04.exam04;

import java.io.IOException;

public class BadAndGood1 {

  // Bad: 주절거리는 주석 (Mumbling)
  // - "No properties means defaults"라는 주석만으로는
  //   기본값이 무엇인지, 어디서 로드되는지 전혀 알 수 없다.
  // - 주석을 이해하려면 다른 코드를 찾아봐야 한다.
  static class BadPropertyLoader {
    void loadProperties() throws IOException {}

    void load() {
      try {
        loadProperties();
      } catch (IOException e) {
        // No properties means defaults
      }
    }
  }

  // Good: 주석 대신 의도를 드러내는 메서드를 호출한다.
  // - loadDefaultProperties()라는 이름이 catch 블록의 동작을 명확히 설명한다.
  // - 읽는 사람이 별도 탐색 없이 흐름을 바로 이해할 수 있다.
  static class GoodPropertyLoader {
    void loadProperties() throws IOException {}
    void loadDefaultProperties() { System.out.println("기본 설정 로드"); }

    void load() {
      try {
        loadProperties();
      } catch (IOException e) {
        loadDefaultProperties();
      }
    }
  }
}

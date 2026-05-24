package com.eomcs.cleancode.ch04.exam04;

public class BadAndGood16 {

  // Bad: 모호한 관계 (Inobvious Connection)
  // - "// add extra space"는 왜 공백을 추가하는지 설명하지 않는다.
  // - 주석이 코드와의 관계를 명확히 드러내지 못한다.
  // - PNG 배열 계산식에서 filter bytes, 200의 의미를 주석이 충분히 설명하지 못한다.
  // - 주석 자체가 다시 설명을 요구하게 된다면, 그 주석은 나쁜 주석이다.
  static class BadImageProcessor {
    int width = 100;
    int height = 100;
    byte[] pngBytes;

    void buildLine(String text) {
      // add extra space
      String line = text + " ";
      System.out.println(line);
    }

    void allocateBuffer() {
      /*
       * start with an array that is big enough to hold all the pixels
       * (plus filter bytes), and an extra 200 bytes for header info
       */
      pngBytes = new byte[((width + 1) * height * 3) + 200];
    }
  }

  // Good: 변수 이름과 메서드 이름이 주석 대신 관계를 설명한다.
  // - appendDelimiterSpace()라는 이름이 공백을 추가하는 이유(구분자 역할)를 표현한다.
  // - filterBytes, headerBytes 변수가 각 숫자의 의미를 명확히 드러낸다.
  // - 주석 없이도 코드를 읽으면 의도를 바로 파악할 수 있다.
  static class GoodImageProcessor {
    int width = 100;
    int height = 100;
    byte[] pngBytes;

    void buildLine(String text) {
      String line = appendDelimiterSpace(text);
      System.out.println(line);
    }

    private String appendDelimiterSpace(String text) {
      return text + " ";
    }

    void allocateBuffer() {
      int filterBytes = (width + 1) * height * 3;
      int headerBytes = 200;
      pngBytes = new byte[filterBytes + headerBytes];
    }
  }
}

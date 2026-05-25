package com.eomcs.cleancode.ch05.exam02;

// 예제 1: 가로 공백과 밀집도 (Horizontal Openness and Density)
public class BadAndGood1 {

  private BadAndGood1() {}

  static class LineWidthHistogram {
    void addLine(int lineSize, int lineCount) {
      System.out.println("lineSize=" + lineSize + ", lineCount=" + lineCount);
    }
  }

  // Bad: 공백이 없거나 잘못된 위치에 있다.
  // - 연산자와 값이 붙어 있어 읽기 어렵다: lineSize=line.length(), totalChars+=lineSize
  // - 함수 이름과 괄호 사이에 불필요한 공백이 있어 무관해 보인다: addLine (lineSize, lineCount)
  // - 인자 사이에 공백이 없어 인자 구분이 어렵다: addLine (lineSize,lineCount)
  static class BadLineMeasurer {
    int lineCount = 0;
    int totalChars = 0;
    LineWidthHistogram lineWidthHistogram = new LineWidthHistogram();

    void measureLine(String line) {
      lineCount++;
      int lineSize=line.length();
      totalChars+=lineSize;
      lineWidthHistogram.addLine (lineSize,lineCount);
      recordWidestLine (lineSize);
    }

    void recordWidestLine(int lineSize) {
      System.out.println("widest: " + lineSize);
    }
  }

  // Good: 공백이 관계의 강도를 표현한다.
  // - 할당 연산자 앞뒤에 공백을 줘서 강조한다: lineSize = line.length()
  // - 함수 이름과 괄호는 붙여 써서 한 단위임을 나타낸다: addLine(lineSize, lineCount)
  // - 인자 사이에 공백을 줘서 각 인자가 별개임을 명확히 한다: addLine(lineSize, lineCount)
  static class GoodLineMeasurer {
    int lineCount = 0;
    int totalChars = 0;
    LineWidthHistogram lineWidthHistogram = new LineWidthHistogram();

    void measureLine(String line) {
      lineCount++;
      int lineSize = line.length();
      totalChars += lineSize;
      lineWidthHistogram.addLine(lineSize, lineCount);
      recordWidestLine(lineSize);
    }

    void recordWidestLine(int lineSize) {
      System.out.println("widest: " + lineSize);
    }
  }
}

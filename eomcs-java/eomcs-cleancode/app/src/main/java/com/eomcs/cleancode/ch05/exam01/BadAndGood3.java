package com.eomcs.cleancode.ch05.exam01;

// 예제 3: 세로 밀집도 (Vertical Density)
public class BadAndGood3 {

  private BadAndGood3() {}

  // Bad: 관련 있는 필드 사이에 불필요한 주석이 끼어 있다.
  // - 주석이 관련 필드들을 떨어뜨려 놓는다.
  // - 코드가 실제보다 더 복잡해 보인다.
  static class BadReportConfig {

    // report title
    private String title;

    // report format
    private String format;

    public BadReportConfig(String title, String format) {
      this.title = title;
      this.format = format;
    }

    @Override
    public String toString() {
      return title + " (" + format + ")";
    }
  }

  // Good: 관련 있는 필드가 주석 없이 가까이 붙어 있다.
  // - 불필요한 주석이 제거되어 구조가 한눈에 들어온다.
  // - 관련 코드끼리 밀집시켜 인지 부담을 줄인다.
  static class GoodReportConfig {

    private String title;
    private String format;

    public GoodReportConfig(String title, String format) {
      this.title = title;
      this.format = format;
    }

    @Override
    public String toString() {
      return title + " (" + format + ")";
    }
  }
}

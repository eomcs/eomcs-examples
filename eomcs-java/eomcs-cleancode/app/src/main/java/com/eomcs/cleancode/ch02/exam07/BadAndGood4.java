package com.eomcs.cleancode.ch02.exam07;

public class BadAndGood4 {

  // Bad
  // - 'r' 이 무엇인지 독자가 외워야 하고, 수십 줄 뒤의 i, j 가 무엇인지 기억해야 한다.
  void bad(String url, int[][] data) {

    String r = url.toLowerCase().replaceAll("https?://", "").split("/")[0];
    // r은 '스키마와 호스트를 제거한 소문자 URL'이라는 걸 기억해야 한다.
    connect(r);

    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        // 수십 줄이 지난 후, i와 j가 각각 무엇을 뜻하는지 기억하는가?
        process(data[i][j]);
      }
    }
  }

  // Good
  // - 변수 이름이 의미를 드러내고, 루프 변수도 역할을 담는다.
  static final int BOARD_ROWS = 10;
  static final int BOARD_COLS = 10;

  void example4(String url, int[][] data) {

    String hostWithoutScheme = url.toLowerCase().replaceAll("https?://", "").split("/")[0];
    connect(hostWithoutScheme);

    for (int row = 0; row < BOARD_ROWS; row++) {
      for (int col = 0; col < BOARD_COLS; col++) {
        process(data[row][col]);
      }
    }
  }

  // ---- 컴파일을 위한 더미 메서드 ----
  void process(int v) {}

  void connect(String s) {}
}

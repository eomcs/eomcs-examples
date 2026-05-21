package com.eomcs.cleancode.ch02.exam07;

public class BadAndGood1 {

  // Bad
  // - r, c 가 row/column 인지 독자가 기억하고 해석해야 한다.
  // - 이름이 짧고 추상적이어서 읽는 사람이 머릿속에서 매핑 작업을 해야 한다.
  void bad() {
    int r = 10;
    int c = 20;

    for (int i = 0; i < r; i++) {
      for (int j = 0; j < c; j++) {
        processCell(i, j);
      }
    }
  }

  // Good
  // - rowCount, columnCount, rowIndex, columnIndex → 해석 없이 즉시 이해 가능.
  // - 이름만 읽어도 즉시 의미를 알 수 있어 해석이 필요 없다.
  void good() {
    int rowCount = 10;
    int columnCount = 20;

    for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
      for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
        processCell(rowIndex, columnIndex);
      }
    }
  }

  // ---- 컴파일을 위한 더미 메서드 ----
  void processCell(int row, int col) {}
}

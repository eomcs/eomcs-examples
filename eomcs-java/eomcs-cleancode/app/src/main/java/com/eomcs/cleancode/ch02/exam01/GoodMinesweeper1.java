package com.eomcs.cleancode.ch02.exam01;

import java.util.ArrayList;
import java.util.List;

// 의도가 드러나는 좋은 예: 이름 개선
class GoodMinesweeper1 {

  // 이름만으로 단위와 측정 대상을 알 수 있다
  int elapsedTimeInDays;
  int daysSinceCreation;
  int daysSinceModification;
  int fileAgeInDays;

  List<int[]> gameBoard;
  int STATUS_VALUE = 0;
  int FLAGGED = 4;

  public List<int[]> getFlaggedCells() {
    List<int[]> flaggedCells = new ArrayList<>();

    for (int[] cell : gameBoard) {
      if (cell[STATUS_VALUE] == FLAGGED) {
        flaggedCells.add(cell);
      }
    }
    return flaggedCells;
  }
}

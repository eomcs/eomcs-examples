package com.eomcs.cleancode.ch02.exam01;

import java.util.ArrayList;
import java.util.List;

// 의도가 드러나는 좋은 예: Cell 클래스 도입으로 의도를 완전히 드러냄
class Cell {
  private boolean flagged = false;
  private int countOfAdjacentMines;

  public boolean isFlagged() {
    return this.flagged;
  }
}

class GoodMinesweeper2 {
  List<Cell> gameBoard;

  public List<Cell> getFlaggedCells() {
    List<Cell> flaggedCells = new ArrayList<>();

    for (Cell cell : gameBoard) {
      if (cell.isFlagged()) {
        flaggedCells.add(cell);
      }
    }
    return flaggedCells;
  }
}

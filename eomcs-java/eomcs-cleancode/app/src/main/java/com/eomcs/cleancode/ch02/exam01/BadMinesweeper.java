package com.eomcs.cleancode.ch02.exam01;

import java.util.ArrayList;
import java.util.List;

// 의도가 드러나지 않는 나쁜 예:
public class BadMinesweeper {

  // - 주석이 필요하다면 의도를 분명히 드러내지 못했다는 의미다.
  int d; // 경과 시간(단위: 날짜)

  List<int[]> theList;

  // 이 코드가 무슨 역할인지 알 수 있는가?
  public List<int[]> getThem() {
    List<int[]> list1 = new ArrayList<int[]>();

    for (int[] x : theList) {
      if (x[0] == 4) {
        list1.add(x);
      }
    }
    return list1;
  }
}

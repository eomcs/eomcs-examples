package com.eomcs.cleancode.ch02.exam05;

public class BadAndGood {

  // Bad: 검색하기 어려운 이름
  // - 한 글자 변수명과 매직 넘버는 IDE에서 검색이 불가능하다.
  class Bad {

    // e는 너무 짧아 검색 불가능, 7은 코드 전체에 너무 많이 존재한다.
    int e = 7;

    int[] t = new int[34];

    // j, t, 34, 4, 5 가 각각 무엇을 의미하는지 알 수 있는가?
    public int calculate() {
      int s = 0;
      for (int j = 0; j < 34; j++) {
        s += (t[j] * 4) / 5;
      }
      return s;
    }
  }

  // Good: 검색하기 쉬운 이름
  // - 의미 있는 이름과 명명된 상수를 사용하면 IDE에서 언제든 검색할 수 있다.
  class Good {

    // maxRetryCount → 프로젝트 전체에서 검색 가능, 의미도 명확하다.
    int maxRetryCount = 7;

    static final int TOTAL_WORK_DAYS = 34;
    static final int WORK_HOURS_PER_DAY = 4;
    static final int WORK_DAYS_PER_WEEK = 5;

    int[] taskHours = new int[TOTAL_WORK_DAYS];

    public int calculate() {
      int sum = 0;
      for (int dayIndex = 0; dayIndex < TOTAL_WORK_DAYS; dayIndex++) {
        sum += (taskHours[dayIndex] * WORK_HOURS_PER_DAY) / WORK_DAYS_PER_WEEK;
      }
      return sum;
    }
  }
}

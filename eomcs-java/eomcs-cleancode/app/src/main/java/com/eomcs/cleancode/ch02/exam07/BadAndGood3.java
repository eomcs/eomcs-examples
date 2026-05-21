package com.eomcs.cleancode.ch02.exam07;

public class BadAndGood3 {

  // Bad
  // - 한 글자 변수는 절대 금지 — 주석 없이는 의미를 알 수 없다.
  void bad() {
    int d; // days?
    int m; // months?
    int y; // years?
  }

  // Good
  // - 의미가 명확한 이름 → 주석 없이도 역할이 드러난다.
  void good() {
    int elapsedDays;
    int createdMonth;
    int birthYear;
  }

  // 예외: 범위가 매우 작고 의미가 명확한 단순 루프는 i 를 써도 무방하다.
  void good2(int n) {
    for (int i = 0; i < n; i++) {
      process(i);
    }
  }

  // ---- 컴파일을 위한 더미 메서드 ----
  void process(int v) {}
}

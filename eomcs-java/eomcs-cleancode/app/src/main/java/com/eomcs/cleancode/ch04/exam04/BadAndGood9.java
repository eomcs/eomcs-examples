package com.eomcs.cleancode.ch04.exam04;

public class BadAndGood9 {

  // Bad: 위치를 표시하는 주석 (Position Markers)
  // - "// ================== Actions ==================" 같은 배너 주석은 시각적 잡음이다.
  // - 배너를 자주 사용하면 독자가 흔한 잡음으로 여겨 무시하게 된다.
  // - 메서드가 충분히 작고 이름이 명확하다면 구분선이 필요 없다.
  static class BadUserService {
    private String name;

    // ================== Getters ==================
    String getName() { return name; }

    // ================== Actions ==================
    void save() { System.out.println("저장"); }
    void delete() { System.out.println("삭제"); }
  }

  // Good: 이름이 명확한 메서드가 배너 없이도 스스로를 설명한다.
  // - 배너 주석을 제거해도 코드 구조를 이해하는 데 전혀 지장이 없다.
  // - 꼭 필요한 경우(정말 긴 파일에서 드물게)에만 절제하여 사용한다.
  static class GoodUserService {
    private String name;

    String getName() { return name; }

    void save() { System.out.println("저장"); }
    void delete() { System.out.println("삭제"); }
  }
}

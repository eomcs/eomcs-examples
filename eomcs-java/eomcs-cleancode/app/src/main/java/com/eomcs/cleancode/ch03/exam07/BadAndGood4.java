package com.eomcs.cleancode.ch03.exam07;

public class BadAndGood4 {

  // Bad: 외부에서 전달된 객체를 직접 변경하는 부작용
  // - appendFooter(Report) → 인자로 받은 외부 객체의 상태를 변경한다.
  // - 호출자가 report 객체가 바뀐다는 사실을 함수 시그니처만으로 알기 어렵다.
  static class BadReport {
    String content;
    BadReport(String content) { this.content = content; }
  }

  static class BadFooterAppender {
    void appendFooter(BadReport report) {
      report.content += "\n--- End ---"; // 외부 객체 변경 (부작용)
    }
  }

  // Good: 객체가 자신의 상태를 변경하는 것은 자연스럽다.
  // - report.appendFooter() → 자신(this.content)을 변경하므로 부작용이 아니다.
  // - 메서드가 자기 자신의 상태를 바꾸는 것은 OOP에서 당연한 동작이다.
  static class GoodReport {
    private String content;

    GoodReport(String content) { this.content = content; }

    void appendFooter() {
      this.content += "\n--- End ---"; // 자기 자신 변경 → 허용
    }

    String getContent() { return content; }
  }

  static class GoodUsage {
    void demo() {
      GoodReport report = new GoodReport("주문 내역");
      report.appendFooter();
      System.out.println(report.getContent());
    }
  }
}

package com.eomcs.cleancode.ch04.exam04;

public class BadAndGood4 {

  // Bad: 의무적으로 다는 주석 (Mandated Comments)
  // - 규칙 때문에 억지로 작성한 @param 주석이다.
  // - "user name"은 파라미터 이름 name이 이미 말하는 내용이다.
  // - 의미 없는 주석이 코드를 길게 만들고 가독성을 떨어뜨린다.
  static class BadUser {
    private String name;

    /**
     * @param name user name
     */
    public void setName(String name) {
      this.name = name;
    }

    String getName() { return name; }
  }

  // Good: 자명한 세터에는 Javadoc이 필요 없다.
  // - 파라미터 이름과 메서드 이름만으로 역할이 충분히 전달된다.
  // - 주석은 코드만으로 표현하기 어려운 정보가 있을 때만 작성한다.
  static class GoodUser {
    private String name;

    public void setName(String name) {
      this.name = name;
    }

    String getName() { return name; }
  }
}

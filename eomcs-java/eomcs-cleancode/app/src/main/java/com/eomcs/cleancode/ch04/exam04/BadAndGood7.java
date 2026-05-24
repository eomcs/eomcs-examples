package com.eomcs.cleancode.ch04.exam04;

public class BadAndGood7 {

  // Bad: 무서운 잡음 (Scary Noise)
  // - 형식만 갖춘 무의미한 Javadoc이다.
  // - "/** The name. */"은 필드명 name이 이미 말하는 내용을 반복한다.
  // - 문서를 제공해야 한다는 잘못된 욕심에서 비롯된 잡음일 뿐이다.
  // - 오히려 읽는 사람의 집중을 방해한다.
  static class BadPerson {
    /** The name. */
    private String name;

    /** The age. */
    private int age;

    BadPerson(String name, int age) { this.name = name; this.age = age; }
    String getName() { return name; }
    int getAge() { return age; }
  }

  // Good: 자명한 필드에는 Javadoc을 달지 않는다.
  // - 필드 이름만으로 충분히 의미가 전달된다.
  // - 불필요한 주석을 제거하면 코드가 더 읽기 쉬워진다.
  static class GoodPerson {
    private String name;
    private int age;

    GoodPerson(String name, int age) { this.name = name; this.age = age; }
    String getName() { return name; }
    int getAge() { return age; }
  }
}

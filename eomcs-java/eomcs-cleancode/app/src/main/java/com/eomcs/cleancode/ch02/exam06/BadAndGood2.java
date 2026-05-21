package com.eomcs.cleancode.ch02.exam06;

// 멤버 접두어
public class BadAndGood2 {

  // Bad: m_ 접두어 사용
  void bad() {
    class User {
      private String m_name;
      private int m_age;
    }

    class Part {
      private String m_dsc; // 설명 텍스트

      void setName(String name) {
        m_dsc = name;
      }
    }
  }

  // Good: 클래스가 충분히 작으면 m_ 불필요
  void good() {
    class User {
      private String name;
      private int age;
    }

    class Part {
      private String description;

      void setDescription(String description) {
        this.description = description;
      }
    }
  }
}

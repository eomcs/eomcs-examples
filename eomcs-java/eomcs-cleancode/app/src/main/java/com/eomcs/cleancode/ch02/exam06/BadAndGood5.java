package com.eomcs.cleancode.ch02.exam06;

// 복합 인코딩
public class BadAndGood5 {

  // Bad: 접두어 + 타입 인코딩
  class Bad {
    String m_strUserName;
  }

  // Good
  class Good {
    String userName;
  }
}

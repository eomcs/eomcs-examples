package com.eomcs.cleancode.ch02.exam06;

// 헝가리안 표기법
public class BadAndGood1 {

  // Bad: 타입을 이름에 인코딩
  void bad() {
    String strName;
    int iAge;
    boolean bIsActive;
  }

  // Good: Java는 강타입 언어, IDE가 타입을 알려준다
  void good() {
    String name;
    int age;
    boolean isActive;
  }
}

package com.eomcs.cleancode.ch02.exam04;

import java.util.Date;

// 발음하기 쉬운 이름을 사용하라
public class BadAndGood {

  // Bad: 이름이 짧아 의미를 알 수 없다.
  class DtaRcrd102 {
    private Date genymdhms; // generation: year, month, day, hour, minute, second
    private Date modymdhms; // modification: year, month, day, hour, minute, second
    private final String pszqint = "102";
  }

  String usrNm;
  int cnt;

  // Good: 읽기 쉽고 말하기 쉬운 이름을 사용해야 한다.
  class Customer {
    private Date generationTimestamp;
    private Date modificationTimestamp;
    private final String recordId = "102";
  }

  String userName;
  int count;
}

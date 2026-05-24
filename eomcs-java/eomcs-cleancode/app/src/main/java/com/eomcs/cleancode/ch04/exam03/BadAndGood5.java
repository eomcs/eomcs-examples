package com.eomcs.cleancode.ch04.exam03;

import java.text.SimpleDateFormat;

public class BadAndGood5 {

  // Bad: SimpleDateFormat을 정적 필드로 공유한다.
  // - SimpleDateFormat은 스레드 안전하지 않다.
  // - 여러 스레드가 동시에 사용하면 파싱 오류나 예측 불가한 결과가 발생한다.
  // - 경고 주석이 없으면 다른 개발자가 이 위험을 모르고 그대로 사용한다.
  static class BadDateFormatter {
    private static final SimpleDateFormat FORMATTER =
        new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");

    String format(java.util.Date date) {
      return FORMATTER.format(date);
    }
  }

  // Good: 결과를 경고하는 주석 (Warning of Consequences)
  // - SimpleDateFormat의 스레드 안전 문제를 경고하여 다른 개발자의 실수를 예방한다.
  // - 호출마다 새 인스턴스를 생성하는 이유를 명확히 알려준다.
  // - 성능을 높이려고 정적 필드로 바꾸려는 시도를 미리 막는다.
  static class GoodDateFormatter {
    // SimpleDateFormat is not thread-safe.
    // Create a new instance for each call.
    static SimpleDateFormat createStandardDateFormat() {
      return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
    }

    String format(java.util.Date date) {
      return createStandardDateFormat().format(date);
    }
  }
}

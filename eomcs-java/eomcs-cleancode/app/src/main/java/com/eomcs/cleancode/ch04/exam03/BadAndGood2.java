package com.eomcs.cleancode.ch04.exam03;

import java.util.regex.Pattern;

public class BadAndGood2 {

  // Bad: 정규식의 형식을 주석 없이 정의한다.
  // - 정규식 자체만으로는 어떤 날짜/시간 형식을 표현하는지 즉시 알기 어렵다.
  // - 이 패턴이 유효한 입력 예를 머릿속으로 그려야 한다.
  static class BadTimeParser {
    private static final Pattern TIME_PATTERN =
        Pattern.compile("\\d{2}:\\d{2}:\\d{2} \\w{3}, \\w{3} \\d{2}, \\d{4}");

    boolean isValid(String input) {
      return TIME_PATTERN.matcher(input).matches();
    }
  }

  // Good: 정보를 제공하는 주석 (Informative Comments)
  // - 정규식이 어떤 형식을 나타내는지 짧은 주석 하나로 명확하게 전달한다.
  // - 코드를 읽는 사람이 예시를 머릿속에 바로 떠올릴 수 있다.
  static class GoodTimeParser {
    // kk:mm:ss EEE, MMM dd, yyyy 형식이다. (예: 08:30:00 Mon, Jan 01, 2026)
    private static final Pattern TIME_PATTERN =
        Pattern.compile("\\d{2}:\\d{2}:\\d{2} \\w{3}, \\w{3} \\d{2}, \\d{4}");

    boolean isValid(String input) {
      return TIME_PATTERN.matcher(input).matches();
    }
  }
}

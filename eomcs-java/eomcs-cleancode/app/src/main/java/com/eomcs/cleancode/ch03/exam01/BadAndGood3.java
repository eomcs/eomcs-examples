package com.eomcs.cleancode.ch03.exam01;

import java.util.List;

public class BadAndGood3 {

  // Bad
  // - while 블록 안에 중첩된 null 체크와 빈 문자열 체크가 뒤섞여 들여쓰기가 깊어진다.
  // - 조건 로직과 처리 로직이 한 곳에 섞여 의도를 파악하기 어렵다.
  static class BadLineProcessor {
    void process(List<String> lines) {
      int index = 0;
      while (index < lines.size()) {
        String line = lines.get(index++);
        if (line != null) {
          if (!line.isEmpty()) {
            processLine(line);
          }
        }
      }
    }
    private void processLine(String line) {
      System.out.println("처리: " + line);
    }
  }

  // Good
  // - while 블록 안에는 함수 호출 하나만 존재해 들여쓰기가 얕다.
  // - 유효성 검사(isValidLine)와 처리(processLine)를 명확히 분리한다.
  // - 각 함수의 이름이 역할을 설명해 전체 흐름을 한눈에 이해할 수 있다.
  static class GoodLineProcessor {
    void process(List<String> lines) {
      int index = 0;
      while (index < lines.size()) {
        processIfValidLine(lines.get(index++));
      }
    }

    private void processIfValidLine(String line) {
      if (isValidLine(line)) {
        processLine(line);
      }
    }

    private boolean isValidLine(String line) {
      return line != null && !line.isEmpty();
    }

    private void processLine(String line) {
      System.out.println("처리: " + line);
    }
  }
}

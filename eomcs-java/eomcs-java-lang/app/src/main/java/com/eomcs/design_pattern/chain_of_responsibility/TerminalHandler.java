package com.eomcs.design_pattern.chain_of_responsibility;

public class TerminalHandler extends AbstractHandler {
  @Override
  public void handle(int a, int b, String op) {
    // 이 핸들러는 연결의 끝을 표현하기 위해 만든 것이다. 
    System.out.println("해당 연산자를 지원하지 않습니다.");
  }
}

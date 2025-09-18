// 예외 처리 후 마무리 작업 - try-with-resources 실행 순서
package com.eomcs.exception.ex3;

public class Exam0641 {

  static class B implements AutoCloseable {

    public void m(int value) throws Exception {
      if (value < 0) {
        throw new Exception("음수입니다!");
      }
      System.out.println("m() 호출!");
    }

    @Override
    public void close() throws Exception {
      System.out.println("close() 호출!");
    }
  }

  public static void main(String[] args) {
    try (B obj = new B()/* 마지막 문장에는 세미콜론을 붙이지 않아도 된다. */) {
      System.out.println("try 블록 실행...시작");
      obj.m(-100);
      // 예외가 발생하면 try{} 블록을 나가기 전에 close()가 먼저 호출된다.

      System.out.println("try 블록 실행...종료");

    } catch (Exception e) {
      // close()가 호출된 후 catch 블록이 실행된다.
      System.out.println("예외 발생!: " + e.getMessage());
    }
  }
}

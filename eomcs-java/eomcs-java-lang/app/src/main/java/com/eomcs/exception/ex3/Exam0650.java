// 예외 처리 후 마무리 작업 - try-with-resources
package com.eomcs.exception.ex3;

public class Exam0650 {

  static class A {
  }

  static class B implements AutoCloseable {
    @Override
    public void close() throws Exception {
      System.out.println("B 클래스의 자원을 해제하였습니다.");
    }
  }

  public static void main(String[] args) throws Exception {
    B obj2 = null;

    try (
        obj2 = new B(); // 컴파일 오류!
        // 이유: 변수 선언은 반드시 괄호 안에 해야 한다.

        B obj3 = new B(); // OK
        ) {
      System.out.println("try 블록 실행...");
    }
  }
}

// 멀티 스레드 적용 전 - 멀티 태스킹을 적용하기 전
package com.eomcs.concurrent.ex1;

public class Exam0110 {

  public static void main(String[] args) {

    // 일반적으로 코드는 위에서 아래로 순서대로 실행한다.
    // 작업이 완료할 때까지 다음 줄로 가지 않는다.
    for (int i = 0; i < 1000; i++) {
      System.out.println("==> " + i);
    }

    for (int i = 0; i < 1000; i++) {
      System.out.println(">>>>>>>>> " + i);
    }
  }
}

// 자바는 main() 메서드를 실행하는 한 개의 "실행 흐름"이 있다.
// 실행 흐름에 따라 순서대로 코드가 실행된다.



// 현재의 실행 라인을 알아내기
package com.eomcs.concurrent.ex2;

public class Exam0110 {

  public static void main(String[] args) {
    // JVM은 여러 개의 스레드를 실행한다.
    // main() 호출도 별도의 스레드가 실행한다.
    // 확인해보자!

    // 이 순간 실행 중인 흐름이 무엇인지 알고 싶다면?
    Thread t = Thread.currentThread();

    System.out.println("실행 흐름명 = " + t.getName());

    // 실행 흐름을 전문적인 용어로 "Thread(실 타래)"라 부른다.
    // JVM이 실행될 때 main() 메서드를 호출하는 실행 흐름(스레드)의 이름은 "main"이다.
  }
}
// JVM의 스레드 계층도:
// main(T)

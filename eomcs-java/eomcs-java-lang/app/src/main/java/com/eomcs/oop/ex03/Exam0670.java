// 스태틱 초기화 블록(static initializer) - 변수 초기화 문장(variable initializer)
package com.eomcs.oop.ex03;

public class Exam0670 {

  public static class A {
    static int a = 100;

    // 위 문장 해석:
    // 1) "클래스가 로딩될 때" int 타입의 메모리를 만들고 그 메모리의 이름을 'a'라고 하라.
    // 2) 'a'라는 변수가 생성된 후 100으로 초기화시켜라.

    // 변수 초기화 문장(variable initializer)?
    // - 변수를 만들자마자 어떤 값으로 초기화시켜야 하는지 명령하는 문장.
    // - 변수 선언문에 할당 연산자(=)를 붙여서 초기화할 값을 지정한다.
    //
    // 변수 초기화 문장을 컴파일 할 때,
    // - 스태틱 초기화 블록이 없으면 컴파일러가 자동으로 삽입한다.
    // - 스태틱 초기화 블록에 a 에 100을 할당하는 문장을 삽입한다.
    // - 위의 문장은 다음 문장으로 바뀐다.
    //
    //    static int a;
    //    static {
    //      a = 100;
    //    }
    // - 바이트 코드(Exam0670$A.class)를 확인해 보라!
  }

  public static void main(String[] args) throws Exception {

    System.out.println(A.a);
  }
}



package com.eomcs.lang.ex03;

//# 논리값 리터럴
//- 참, 거짓을 표현할 때 사용한다.
//
public class Exam0500 {
  public static void main(String[] args) {
    
    //참을 표현할 때는 true, 거짓을 표현할 때는 'false'이다.
    //자바는 대소문자를 구분하기 때문에 반드시 소문자로 작성해야 한다.
    System.out.println(true);
    System.out.println(false);
    
    //비교 연산의 결과는 논리값이다.
    System.out.println(4 < 5); 
    System.out.println(4 > 5); 

    //논리 연산의 결과는 논리값이다.
    System.out.println(true && false);
    System.out.println(true || false);
    
    // 작은 따옴표가 알려준 유니코드 값 비교하기
    System.out.println(65 == 'A');
    System.out.println(65 == 'B');

    // 숫자를 true/false로 변환할 수 없다.
    // System.out.println((boolean)1); // 컴파일 오류!
  }
}

//# 논리 값을 메모리에 저장할 때 크기
//- 4바이트 int 메모리에 저장한다. (JVM 명세서 참조)
//- 배열 값인 경우 1바이트 메모리에 저장한다. (JVM 명세서 참조)
//- true는 1, false는 0 값으로 저장한다.
//- 그렇다고 직접 정수 값을 지정해서는 안된다.
//- c언어의 경우 자바의 true, false 처럼 논리 값을 표현하는 키워드가 없다.
//
//

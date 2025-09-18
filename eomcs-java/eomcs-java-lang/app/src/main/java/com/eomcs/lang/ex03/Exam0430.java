package com.eomcs.lang.ex03;

//# 문자의 리터럴 - 작은따옴표('')와 문자 코드
//- 문자 리터럴을 표현할 때 사용하는 작은 따옴표는 문자의 코드 값을 알려주는 도구이다.
//

public class Exam0430 {
  public static void main(String[] args) {

    //유니코드를 직접 넘겨주기
    //단, 유니코드임을 알려주기 위해 (char)를 앞에 붙인다.
    System.out.println((char)65);

    //작은 따옴표를 사용하여 문자 코드를 넘겨주기
    //(char)붙일 필요가 없다.
    System.out.println('A');

    //작은 따옴표가 알려준 유니코드를 그대로 출력하기
    //정수를 의미하는 (int)를 앞에 붙인다.
    System.out.println((int)'A');

    // '각' ==> 0xac01(44033) 을 리턴한다.
    // 따라서 다음과 같이 다른 숫자와 연산을 수행할 수 있다.
    System.out.println('각' + 1);
    System.out.println('각' - 1);
    // 문자 코드에 정수 값을 연산하는 순간 
    // 그 결과는 더이상 문자 코드가 아니라 일반적인 정수가 된다.
    // 그래서 println()은 그 값에 대한 문자를 출력하는 것이 아니라
    // 그냥 정수를 출력하는 것이다.

    // 해당 계산 결과를 문자 코드로 바꾸고 싶다면,
    // 이전에 했던대로 (char) 명령을 붙여라
    System.out.println((char)('각' + 1));
    System.out.println((char)('각' - 1));
  }
}
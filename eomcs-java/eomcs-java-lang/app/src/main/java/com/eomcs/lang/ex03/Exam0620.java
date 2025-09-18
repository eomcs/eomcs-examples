package com.eomcs.lang.ex03;

//# 문자열 리터럴과 + 연산자
//- 문자열 연결 연산자(concatenation): +
//  + 를 이용하여 문자열과 문자열을 연결한다.
//  이때 두 개의 문자열이 연결된 새 문자열이 생성된다.
//  예) "홍길동" + "입니다." => "홍길동입니다."
//- 문자열과 다른 종류의 값을 연결하기
//  문자열과 다른 종류의 값을 연결할 수 있다.
//  연결되기 전에 다른 종류의 값이 먼저 문자열로 바뀐 다음에 연결된다.
//
public class Exam0620 {
  public static void main(String[] args) {
    // 두 문자열을 합쳐 새 문자열이 생성된다.
    // "Hello," + "world!" = "Hello,world!"
    System.out.println("Hello," + "world!");

    // 새 문자열을 만들어 숫자를 붙인다.
    // "나이:" + 20 = "나이:20"
    System.out.println("나이:" + 20);

    // 새 문자열을 만들어 논리 값을 붙인다.
    // false + "<== 재직자 여부" = "false<== 재직자 여부"
    System.out.println(false + "<== 재직자 여부");

    // 새 문자열을 만들어 문자를 붙인다.
    // "성별:" + '여' = "성별:여"
    System.out.println("성별:" + '여');

    // 새 문자열을 만들어 부동소수점을 붙인다.
    // "키:" + 170.5f = "키:170.5" <== 부동소수점 접미사 f 는 새로 만든 문자열에 붙지 않는다.
    System.out.println("키:" + 170.5f);
  }
}


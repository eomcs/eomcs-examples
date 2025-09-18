package com.eomcs.lang.ex05;

//# 증감 연산자 : 후위(post-fix) 증감 연산자 응용 I
//
public class Exam0630 {
  public static void main(String[] args) {
    int i = 7;

    int j = i++;

    // 위 문장은 컴파일될 때 다음과 같은 형태로 변환된다.
    // int temp = i;
    // i = i + 1;
    // int j = temp;
    //
    // 소스 코드 그대로 설명하면 다음과 같이 설명할 수 있다.
    // 그러나 가능한 컴파일 후에 변환된 코드를 그대로 이해하는 게 더 낫다.
    // 1) i 값을 그자리에 놓는다.
    //    => j = 7;
    //    => 7 값을 j에 저장할 수 없다.
    //    => 왜? 아직 = 연산자 오른쪽의 모든 식이 수행되지 않았다.
    // 2) ++ 연산 실행
    //    => 즉 i = i + 1 실행
    //    => i = 8 이 되었다.
    // 3) 할당 연산자 실행
    //    => j <=== 7 실행

    System.out.println(j);
    System.out.println(i);
  }
}

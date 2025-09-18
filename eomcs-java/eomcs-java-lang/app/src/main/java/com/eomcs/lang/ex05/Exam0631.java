package com.eomcs.lang.ex05;

//# 증감 연산자 : 후위(post-fix) 증감 연산자 응용 I
//
public class Exam0631 {
  public static void main(String[] args) {
    int i = 7;

    i = i++;
    // 위 문장은 다음과 같이 실행된다.
    //int temp = i;
    //i = i + 1;
    //i = temp;
    //
    // 1) i 값을 그자리에 놓는다.
    //    => i = 7;
    //    => 7 값을 i에 저장할 수 없다.
    //    => 왜? 아직 = 연산자 오른쪽의 모든 식이 수행되지 않는다.
    // 2) ++ 연산 실행
    //    => 즉 i = i + 1 실행
    //    => i = 8 이 되었다.
    // 3) 할당 연산자 실행
    //    => i <===== 7 실행
    //    => 다시 i는 8에서 7로 바뀌었다.

    System.out.println(i);

  }
}

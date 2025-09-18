package com.eomcs.lang.ex05;

//# 증감 연산자 : 전위(pre-fix) 증감 연산자 응용 I
//
public class Exam0670 {
  public static void main(String[] args) {
    int a = 5;
    int r = ++a;
    // 위의 문장은 다음 두 문장으로 변경된다.
    // a = a + 1;
    // int r = a;
    
    System.out.printf("%d, %d\n", a, r);
    // r = ++a 연산 순서
    // 1) ++a 연산을 먼저 수행 => a 변수의 값을 1 증가시킨다.
    // 2) a 값을 리턴한다. => r = 6;
    // 3) = 연산자 수행 => r 변수에 6 값을 넣는다.
    // 결론!
    // => 전위 연산자는 먼저 변수의 값을 -- 하거나 ++ 한 후에 
    //    그 자리에 변수의 값을 놓는다.
  }
}

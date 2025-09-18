package com.eomcs.lang.ex05;

//# 논리 연산자 : || vs |
//
public class Exam0340 {
  public static void main(String[] args) {
    boolean a = true;
    boolean b = false;
    boolean r = a || (b = true);
    // 계산 순서:
    // r = a || (b = true)
    // r = true || (b = true)
    // => || 왼쪽 값으로 이미 결과를 알 수 있기 때문에 
    //    || 오른쪽 문장은 실행하지 않는다.
    // r = true
    System.out.printf("a=%b, b=%b, r=%b\n", a, b, r);

    a = true;
    b = false;
    r = a | (b = true);
    // 계산 순서: 
    // r = a | (b = true)
    // r = true | (b = true)
    // => | 왼쪽 값으로 결과를 확정할 수 있더라도 무조건 오른쪽 문장을 실행한다.
    // r = true | (b 변수에 true를 저장)
    // r = true | true
    // r = true
    System.out.printf("a=%b, b=%b, r=%b\n", a, b, r);
  }
}








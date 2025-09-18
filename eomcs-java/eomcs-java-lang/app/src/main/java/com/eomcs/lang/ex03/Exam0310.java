package com.eomcs.lang.ex03;

//# 부동소수점 리터럴
//- 컴퓨터는 1과 0으로 데이터를 저장한다.
//- 실수 값을 부동소수점 방식으로 저장하려면 1과 0으로 변환해야 한다.
//- 자바는 부동소수점을 저장할 때 전기전자기술자협회(IEEE)에서 개발한 IEEE 754 명세에 따라 2진수로 변환한다.
//
public class Exam0310 {
  public static void main(String[] args) {
    
    //## 10진수 표기법
    System.out.println(3.14);
    
    //## Exponential 기호 사용하기
    //- e 기호를 사용하면 소수점의 위치를 조정할 수 있다.
    //- 소수점의 위치를 움직인다고 해서 "부동소수점(floating point)"이라 부른다. 
    //- 표기법
    //    숫자e지수 또는 숫자E지수 
    System.out.println(0.0314e2); // 0.0314 * 10의2승 = 3.14
    System.out.println(0.314e1); // 0.314 * 10의1승 = 3.14
    System.out.println(31.4e-1); // 31.4 * 10의-1승 = 3.14
    System.out.println(314e-2); // 314 * 10의-2승 = 3.14
  }
}
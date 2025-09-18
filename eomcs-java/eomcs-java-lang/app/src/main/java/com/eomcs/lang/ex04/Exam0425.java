package com.eomcs.lang.ex04;

//# 부동소수점 변수 - 변수와 리터럴의 크기 III
//
public class Exam0425 {
  public static void main(String[] args) {
    
    float f1 = 99988.88f;
    float f2 = 11.11111f;

    // 각 변수의 값이 개별적으로 옳은 부동소수점이라도,
    System.out.println(f1);
    System.out.println(f2);
    
    // 주의!
    // 연산 결과가 해당 타입의 크기를 벗어 난다면 그 결과 값이 짤린다.
    //     99988.88
    //   +    11.11111
    //  -----------------
    //     99999.99111  <== 계산 결과가 4바이트 유효자릿수를 넘어간다.   
    //     계산 결과는 유효자릿수 범위 내에서 반올림되거나 잘린다.
    float f3 = f1 + f2; 
    System.out.println(f3);
    
    // 그래서 부동소수점을 다룰 때는 가능한 float 보다 두 배 더 정밀한 double을 사용하라!
    // 문법에서도 double 리터럴을 기본 부동소수점으로 간주한다.
    // 즉 부동소수점 뒤에 d나 D 표시를 생략할 수 있다.
    double d1 = 99988.88;
    double d2 = 11.11111;
    double d3 = d1 + d2;
    System.out.println(d3);
  }
}

// 부동소수점 사용법
// - 유효자릿수 7자리 이하의 값을 단순이 저장하고 꺼내는 경우 float 타입을 사용하라!
//   예) float height; 
//       float weight;
// - 부동소수점에 대해 연산을 수행하는 경우 double 타입을 사용하라!
//   예) double average;
//       double area;

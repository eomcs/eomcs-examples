package com.eomcs.lang.ex04;

//# 값 저장과 메모리 크기 - 정수는 부동소수점 메모리에 저장할 수 있다.
//주의!
//- 부동소수점 메모리의 유효자릿수를 넘어가는 정수를 저장할 경우 값이 짤린다.
//- 그럼에도 컴파일 오류가 발생하지 않는다.

public class Exam0911 {
  public static void main(String[] args) {

    byte b = 100; // 1byte
    short s = 32767; // 2byte(-32768 ~ 32767)
    int i = 98765678; // 4byte(약 -21억 ~ +21억)
    long l = 18_2345_3456_4567_5678L; // 8byte(약 -922경 ~ 922경)

    float f;
    double d;

    // 주의!
    // - float의 자릿수가 넘어가는 정수를 저장하는 경우 값이 짤릴 수 있다.
    // - 그럼에도 불구하고 컴파일 오류가 발생하지 않는다.
    // - 그래서 정수 값을 부동소수점 메모리에 저장할 때 특히 주의해야 한다.

    f = b; // byte(1) ==> float(4). 값을 그대로 저장.
    System.out.println(f);

    f = s; // short(2) ==> float(4). 값을 그대로 저장. 
    System.out.println(f);

    f = i; // int(4) ==> float(4). 
    // 유효자릿수를 넘는 정수는 짤린다.
    System.out.println(f);

    f = l; // long(8) ==> float(4)
    // 유효자릿수를 넘는 정수는 짤린다.
    System.out.println(f);

    d = i; // int(4) ==> double(8)
    // 유효자릿수 범위의 정수 값이므로 int(32비트) 값을 그대로 저장할 수 있다.
    System.out.println(d);       

    d = l; 
    // 유효 범위를 넘어가는 정수인 경우 짤린다.
    // 주의! 컴파일 오류가 발생하지 않는다.
    System.out.println(d);       
  }
}

// 정리!
// - 정수 메모리의 값(byte, short, char, int, long)을 
//   부동소수점 메모리(float, double)에 저장할 때
//   주의해서 사용하라!
// - 유효자릿수를 넘어가는 정수 값인 경우 부동소수점 메모리에 저장될 때 짤릴 수 있다.
//   그럼에도 컴파일 오류가 발생하지 않기 때문에
//   개발자들이 놓치는 경우가 많다!
package com.eomcs.lang.ex05;

//# 산술 연산자 : 암시적 형변환(implicit type conversion; type casting)
//
public class Exam0150 {
  public static void main(String[] args) {
    byte b = 1;
    short s = 2;
    int i = 3;
    long l = 4;
    float f = 5.5f;
    double d = 6.6;
    boolean bool = true;
    char c = 7;

    // byte + byte = int
    // => 연산을 하기 전에 byte 값이 int로 암시적 형변환 된다.
    //    byte r1 = b + b; // 컴파일 오류!

    // short + short = int
    // => 연산을 하기 전에 short 값이 int로 암시적 형변환 된다.
    //    short r2 = s + s; // 컴파일 오류!

    // byte + short = int
    // => byte와 short 값은 int로 암시적 형변환 된다.
    //    short r3 = b + s; // 컴파일 오류!

    // byte + int = int
    // => byte가 int로 암시적 형변환 한 이후 연산을 수행한다.
    int r4 = b + i; // OK

    // short + int = int
    // => short가 int로 암시적 형변환 한 이후 연산을 수행한다.
    int r5 = s + i; // OK

    // int + long = long
    // => int가 long으로 암시적 형변환 한 이후에 연산을 수행한다.
    //    int r6 = i + l; // 컴파일 오류!

    // long + float = float
    // => long이 float으로 암시적 형변환 한 후에 연산을 수행한다.
    //    long r7 = l + f; // 컴파일 오류!

    // int + float = float
    // => 정수 타입의 값과 부동소수점 타입의 값을 연산하면
    //    정수 타입의 값이 암시적 형변환을 통해 부동소수점으로 바뀐다.
    //    int r8 = i + f; // 컴파일 오류!

    // float + double = double
    //    float r9 = f + d; // 컴파일 오류!

    // byte + short + int + long + float + double = double
    //long r10 = b + s + i + l + f + d; // 컴파일 오류!

    // float + int + long = float
    //    long r11 = f + i + l; // 컴파일 오류!

    // boolean + int = 컴파일 오류!
    // => 산술 연산자는 정수 타입(byte, short, char, int, long)과
    //    부동소수점 타입(float, double)에 대해서만 
    //    실행할 수 있다. 
    //    int r12 = bool + i; // 컴파일 오류!
  }
}

// 정리!
// - 연산은 항상 같은 타입끼리만 가능하다.
// - 다른 타입끼리 연산을 할 때는 둘 중 한개의 타입을 다른 타입을 
//   바꿔야 한다.
// - 타입을 바꾸는 것을 내부적인 규칙에 따라 자동으로 처리한다고 해서
//   "암시적 형변환(implicit type conversion)"이라 부른다.
// - 암시적 형변환 규칙
//   다음과 같이 오른쪽 타입의 값으로 자동 변환시킨다.
//   byte,short,char => int => long => float => double
//
// - 정수와 부동소수점에 대해서만 암시적 형변환이 일어난다.
//   그 외 다른 타입은 불가능하다!
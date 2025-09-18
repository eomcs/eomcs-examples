package com.eomcs.lang.ex04;

//# 형변환 - 명시적 형변환이 불가능한 경우 
// 
public class Exam0950 {
  public static void main(String[] args) {

    // 1) 정수 메모리끼리 형변환이 가능하다.
    // 2) 부동소수점을 정수로 형변환이 가능한다.
    // 3) 형변환 없이 정수는 부동소수점 메모리에 저장할 수 있다.
    // 4) 숫자를 문자 코드로 형변환 가능한다.
    // 그 외에는 형변환 불가!

    // 예) 정수, 부동소수점 ==/==> boolean
    boolean bool;
    //    bool = (boolean) 1; // 컴파일 오류!

    // 예) 문자열 ==/==> 정수, 부동소수점, 문자, boolean
    //    boolean v1 = (boolean) "true"; // 컴파일 오류!
    //    char v2 = (char) "가"; // 컴파일 오류!
    //    int v3 = (int) "123"; // 컴파일 오류!
    //    float v4 = (int) "3.14f"; // 컴파일 오류!

    // 예) boolean ==/==> 정수
    bool = true;
    //    byte b1 = (byte) bool;
    //    short s1 = (byte) bool;
    //    int i1 = (byte) bool;
    //    long l1 = (byte) bool;
    //    char c1 = (byte) bool;

    // 예) bool ==/==> 부동소수점
    //    float f1 = (byte) bool;
    //    double d1 = (byte) bool;

  }
}

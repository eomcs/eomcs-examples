package com.eomcs.lang.ex04;

//# 배열 - 배열 인스턴스 메모리의 기본 값
//
public class Exam0560 {
  public static void main(String[] args) {

    // 로컬 변수는 값을 저장하지 않고 사용할 수 없다.
    int v;
    //    System.out.println(v); // 컴파일 오류!

    // 로컬 변수와 달리 new 명령으로 확보된 메모리는 종류에 상관없이 기본 값으로 자동 초기화 된다.
    // 따라서 배열 메모리 또한 생성되는 순간 기본 값으로 자동 초기화 된다.
    // - 정수 배열(byte[], short[], int[], long[]) : 0
    // - 부동소수점 배열(float[], double[]) : 0.0
    // - 논리 배열(boolean[]) : false
    // - 문자 배열(char[]) : '\u0000'
    // - 주소 변수(Object[]) : null
    //

    int[] arr1 = new int[5];
    float[] arr2 = new float[5];
    boolean[] arr3 = new boolean[5];
    char[] arr4 = new char[5];
    Object[] arr5 = new Object[5];

    System.out.println(arr1[0]);
    System.out.println(arr2[0]);
    System.out.println(arr3[0]);
    System.out.println(arr4[0]);
    System.out.println(arr5[0]);
  }
}
package com.eomcs.lang.ex04;

//# 배열 - 배열 레퍼런스와 배열 인스턴스 III
//
public class Exam0550 {
  public static void main(String[] args) {

    int[] arr1;
    arr1 = new int[5];
    
    // 언제든 배열 인스턴스의 주소를 다른 레퍼런스에 담을 수 있다.
    int[] arr2 = arr1;
    
    // arr2와 arr1은 같은 배열 인스턴스를 가리킨다.
    arr2[0] = 100;
    
    // arr2를 통해 값을 저장한 후, arr1을 통해 값을 꺼낼 수 있다.
    System.out.println(arr1[0]);
  }
}
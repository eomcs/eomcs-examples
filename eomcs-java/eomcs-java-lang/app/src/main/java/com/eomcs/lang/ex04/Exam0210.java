package com.eomcs.lang.ex04;

//# 블록 안에서 변수 선언 위치  
//
public class Exam0210 {
  
  public static void main(String[] args) {
    //사용할 변수를 블록({}) 시작 부분에 미리 선언해 놓아도 된다.
    int age;
    int weight;
    int height;
    
    age = 20;
    weight = 100;
    height = 200;
    
    //변수가 필요할 때 블록({}) 중간 부분에 언제든 선언할 수 있다.
    int score;
    
    score = 100;
  }
}

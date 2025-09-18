package com.eomcs.lang.ex02;

//# 주석 - 일반 주석
//
//- 코드에 대한 이해를 돕기 위해 붙이는 설명이다.
//- 컴파일할 때 무시된다. 즉 .class 파일에 존재하지 않는다. 
//

/*
## 여러 줄 주석(traditional comment)
- 주석의 끝 표시를 만날 때까지 주석으로 간주된다.
- 여러 줄의 설명을 붙일 때 유용한다.
- C, C++ 프로그래밍 언어에서 사용하는 주석 문법과 같다.
*/

//## 한 줄 주석(end-of-line comment)
//- 줄이 끝날 때 까지 주석으로 간주한다.
//- C++ 프로그래밍에서 사용하는 주석 문법과 같다.


public class Exam0100 {
  public static void main(String[] args/* 이 주석은 코드 중간에 삽입할 때 유용하다.*/) {
    System.out.println("일반주석!");
  }
}

//## 실습
//1) 컴파일하기
//- $ javac -d bin/main -encoding UTF-8 src/main/java/com/eomcs/basic/ex02/Exam0100.java
//
//2) 실행하기
//- $ java -cp bin/main com.eomcs.basic.ex02.Exam0100
//
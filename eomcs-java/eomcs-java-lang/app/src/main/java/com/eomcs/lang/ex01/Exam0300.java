package com.eomcs.lang.ex01;

//# 소스 파일의 인코딩 지정하기
//
//- 컴파일 할 때 소스 파일의 문자집합을 지정하는 방법
//
//## 컴파일할 때 소스 파일의 인코딩 문자집합을 지정하기
//- 컴파일할 때 소스 파일의 인코딩 문자집합을 지정하지 않으면, 운영체제의 기본 문자 집합으로 저장되어 있다고 간주하고 컴파일한다.
//- 만약 소스 파일의 인코딩 문자집합이 운영체제의 기본 문자표(character set)와 다르다면, 컴파일 오류가 발생한다.
//- 해결책? 다음과 같이 컴파일할 때 옵션을 지정한다.
//- 컴파일 옵션
// javac -encoding [문자집합] [소스파일명]
//

class Exam0300 {
  public static void main(String[] args) {
    System.out.println("안녕하세요!");
  }
}

// ## 실습
// 1) 컴파일하기
// - $ javac -encoding [문자집합] [소스파일명]
// - 예) $ javac -d bin/main -encoding UTF-8
// src/main/java/com/eomcs/lang/ex01/Exam0300.java
//
// 2) 실행하기
// - $ java -cp bin/main com.eomcs.lang.ex01.Exam0300

// Java SE 18 버전부터는 기본 인코딩을 UTF-8 가정한다.
// 따라서 Windows OS에서 -encoding 옵션을 붙일 필요가 없다.
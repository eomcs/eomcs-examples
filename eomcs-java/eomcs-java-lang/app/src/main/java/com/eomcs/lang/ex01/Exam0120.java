package com.eomcs.lang.ex01;

//# 클래스 블록과 컴파일 II
//- 한 소스 파일에 여러 개의 클래스 블록이 있을 경우 각 블록 당 한 개의 클래스 파일(.class)이 생성된다.
//

class A {}
class B {}
class C {}
 
//## 실습
//1) 컴파일하기
//- $ javac -d bin/main -encoding UTF-8 src/main/java/com/eomcs/lang/ex01/Exam1_2.java
//
//2) 생성된 클래스 파일 확인하기
//- bin/main/com/eomcs/lang/ex01 디렉토리에 A.class, B.class, C.class 파일이 생성된다
//







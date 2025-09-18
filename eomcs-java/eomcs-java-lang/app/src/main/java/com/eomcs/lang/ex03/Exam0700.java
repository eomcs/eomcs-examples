package com.eomcs.lang.ex03;

//# 이스케이프 문자(escape character) = 문자 제어 코드
//- 화면에 출력하는 문자가 아니라 문자 출력을 제어하는 문자이다.
//- 제어 문자
//  \n - Line Feed(LF), 0x0a
//  \r - Carrage Return(CR), 0x0d
//  \f - Form Feed, 0x0c
//  \t - Tab, 0x09
//  \b - Backspace, 0x08
//  \' - Single Quote, 0x27
//  \" - Double Quote, 0x22
//  \\ - Backslash, 0x5c
//
public class Exam0700 {
  public static void main(String[] args) {
    System.out.println("Hello,world!"); 
    System.out.println("Hello,\nworld!"); // 줄바꿈 문자
    System.out.println("Hello,\rabc"); // 커서(cursor)를 처음으로 돌리는 문자
    // 커서란? 문자를 출력할 위치를 가리키는 것.

    System.out.println("Hello,\b\b\bworld!"); // 커서를 뒤로 한 칸 이동시키는 문자
    System.out.println("Hello,\tworld!"); // 탭 공간을 추가시키는 문자
    System.out.println("Hello,\fworld!"); // 
    System.out.println("Hello,\"w\"orld!"); // " 문자를 출력시키는 문자
    System.out.println("Hello,'w'orld!"); //"" 안에서 ' 문자는 그냥 적는다.
    System.out.println('\''); // ' 문자를 출력시키는 문자
    System.out.println('"'); // '' 안에서 " 문자는 그냥 적는다.
    System.out.println("c:\\Users\\user\\git"); // \ 문자를 출력시키는 문자
  }
}

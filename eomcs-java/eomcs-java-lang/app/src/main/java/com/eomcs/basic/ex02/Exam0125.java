// StringBuffer - 문자열 비교 II
package com.eomcs.basic.ex02;

public class Exam0125 {
  public static void main(String[] args) {

    StringBuffer b1 = new StringBuffer("Hello");
    StringBuffer b2 = new StringBuffer("Hello");

    // StringBuffer 에 들어 있는 문자열을 비교하려면?
    // - StringBuffer에서 String을 꺼내 비교하라!
    //
    // String s1 = b1.toString();
    // String s2 = b2.toString();
    // System.out.println(s1.equals(s2));
    //
    System.out.println(b1.toString().equals(b2.toString()));
  }
}



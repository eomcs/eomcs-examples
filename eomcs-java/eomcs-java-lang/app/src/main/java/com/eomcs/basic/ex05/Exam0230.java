// Deque의 Iterator 와 for(:) 문 
package com.eomcs.basic.ex05;

import java.util.ArrayDeque;

public class Exam0230 {

  public static void main(String[] args) {

    ArrayDeque<String> stack = new ArrayDeque<>();

    stack.push("aaa");
    stack.push("bbb");
    stack.push("ccc");

    // Iterator 를 통해 데이터를 조회하고 싶다면,
    // 다음과 같이 for(:) 문을 사용하는 것이 더 낫다.
    for (String s : stack) {
      System.out.println(s);
    }
  }

}












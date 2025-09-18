// Stack 클래스 사용법 - search()
package com.eomcs.basic.ex05;

import java.util.Stack;

public class Exam0140 {

  public static void main(String[] args) {
    Stack<String> stack = new Stack<>();
    
    // push() - 스택의 맨 마지막에 값을 추가한다.
    stack.push("aaa");
    stack.push("bbb");
    stack.push("ccc");
    
    // search() - 스택의 맨 위에서부터 해당 값을 찾는다. 위치는 맨 위가 1이다.
    System.out.println(stack.search(new String("ccc"))); // 1
    System.out.println(stack.search(new String("bbb"))); // 2
    System.out.println(stack.search(new String("aaa"))); // 3
    System.out.println(stack.search(new String("xxx"))); // -1
    
  }

}


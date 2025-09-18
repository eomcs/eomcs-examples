// Stack 클래스 사용법 - peek()
package com.eomcs.basic.ex05;

import java.util.Stack;

public class Exam0130 {

  public static void main(String[] args) {
    Stack<String> stack = new Stack<>();
    
    // push() - 스택의 맨 마지막에 값을 추가한다.
    stack.push("aaa");
    stack.push("bbb");
    stack.push("ccc");
    
    // peek() - 맨 위의 값을 꺼낸다. 단 pop()과는 달리 제거하지 않는다.
    System.out.println(stack.peek());
    System.out.println(stack.peek());
    System.out.println(stack.peek());
    
  }

}


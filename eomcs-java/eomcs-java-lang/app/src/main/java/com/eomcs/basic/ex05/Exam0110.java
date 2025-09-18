// Stack 클래스 사용법 
package com.eomcs.basic.ex05;

import java.util.Stack;

public class Exam0110 {

  public static void main(String[] args) {
    Stack<String> stack = new Stack<>();

    // push() - 스택의 맨 마지막에 값을 추가한다.
    stack.push("aaa");
    stack.push("bbb");
    stack.push("ccc");
    //stack.push(new Date());  // 타입 파라미터에 전달한 타입과 일치하지 않으면 오류!  

    // pop() - 스택의 맨 마지막에 입력된 값부터 꺼낸다.
    // => 그래서 스택은 LIFO(Last In First Out) 방식으로 데이터를 다룬다.
    System.out.println(stack.pop());
    System.out.println(stack.pop());
    System.out.println(stack.pop());

    // 값이 없는데 꺼내려 하면 EmptyStackException 예외가 발생한다.
    System.out.println(stack.pop());
  }

}


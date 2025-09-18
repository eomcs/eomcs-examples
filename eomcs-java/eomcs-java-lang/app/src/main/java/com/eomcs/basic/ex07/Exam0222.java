// Iterator 의 사용
package com.eomcs.basic.ex07;

import java.util.Iterator;
import java.util.Stack;

public class Exam0222 {
  public static void main(String[] args) {

    Stack<String> stack = new Stack<>();
    stack.push("aaa");
    stack.push("bbb");
    stack.push("ccc");
    stack.push("ddd");
    stack.push("eee");

    Iterator<String> 컬렉션에서값을꺼내주는객체 = stack.iterator();
    while (컬렉션에서값을꺼내주는객체.hasNext()) {
      System.out.print(컬렉션에서값을꺼내주는객체.next() + ", ");
    }
    System.out.println();

    // Iterator 를 사용하면,
    // 컬렉션의 종류에 상관없이 일관된 방식으로 값을 조회할 수 있다.
  }
}

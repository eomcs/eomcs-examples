package com.eomcs.oop.ex11.overview.step1;

public class Test2 {
  public static void main(String[] args) {
    MyStack myStack = new MyStack();
    myStack.push("홍길동");
    myStack.push("임꺽정");
    myStack.push("유관순");
    myStack.push("안중근");
    myStack.push("윤봉길");
    myStack.push("김구");

    while (myStack.size() > 0) {
      System.out.println(myStack.pop());
    }
    System.out.println(myStack.size());
  }
}

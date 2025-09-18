package com.eomcs.oop.ex11.overview.step7;

public class Test2 {
  public static void main(String[] args) {
    MyStack myStack = new MyStack();
    myStack.push("홍길동");
    myStack.push("임꺽정");
    myStack.push("유관순");
    myStack.push("안중근");
    myStack.push("윤봉길");
    myStack.push("김구");

    Iterator iterator = myStack.iterator();
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }
  }
}

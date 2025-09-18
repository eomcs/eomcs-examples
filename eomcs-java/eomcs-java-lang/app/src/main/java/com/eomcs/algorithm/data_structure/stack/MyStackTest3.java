package com.eomcs.algorithm.data_structure.stack;

public class MyStackTest3 {
  public static void main(String[] args) throws Exception {
    MyStack<String> stack = new MyStack<>();
    stack.push("aaa");
    stack.push("bbb");
    stack.push("ccc");
    stack.push("ddd");
    stack.push("eee");
    //stack.push(Integer.valueOf(100)); // 컴파일 오류!
    print(stack);

    System.out.println("---------------------------");

    MyStack<String> stack2 = stack.clone();
    stack2.pop();
    stack2.pop();
    print(stack2);
    System.out.println("---------------------------");

    while (!stack2.empty()) {
      System.out.println(stack2.pop());
    }

    System.out.println("---------------------------");

    print(stack);
    System.out.println("---------------------------");
  }

  static void print(MyStack<String> stack) {
    for (int i = 0; i < stack.size(); i++) {
      System.out.print(stack.get(i) + ",");
    }
    System.out.println();
  }
}

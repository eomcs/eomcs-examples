package com.eomcs.algorithm.data_structure.stack;

public class MyStackTest2 {
  public static void main(String[] args) throws Exception {
    MyStack stack = new MyStack();
    stack.push("aaa");
    stack.push("bbb");
    stack.push("ccc");
    stack.push("ddd");
    stack.push("eee");
    print(stack);

    System.out.println("---------------------------");

    MyStack stack2 = stack.clone();
    stack2.pop();
    stack2.pop();
    print(stack2);
    System.out.println("---------------------------");

    print(stack);
    System.out.println("---------------------------");
  }

  static void print(MyStack stack) {
    for (int i = 0; i < stack.size(); i++) {
      System.out.print(stack.get(i) + ",");
    }
    System.out.println();
  }
}

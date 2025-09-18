package com.eomcs.algorithm.data_structure.queue;

public class MyQueueTest3 {
  public static void main(String[] args) throws Exception {
    MyQueue<String> queue = new MyQueue<>();
    queue.offer("aaa");
    queue.offer("bbb");
    queue.offer("ccc");
    queue.offer("ddd");
    queue.offer("eee");
    print(queue);
    System.out.println("--------------------------");

    MyQueue<String> queue2 = queue.clone();
    queue2.poll();
    queue2.poll();
    print(queue2);
    System.out.println("--------------------------");

    print(queue);
    System.out.println("--------------------------");
  }

  static void print(MyQueue<String> queue) {
    for (int i = 0; i < queue.size(); i++) {
      System.out.print(queue.get(i) + ",");
    }
    System.out.println();
  }
}

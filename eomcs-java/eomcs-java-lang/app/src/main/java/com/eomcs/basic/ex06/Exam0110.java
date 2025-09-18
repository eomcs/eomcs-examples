// Queue 구현과 사용
package com.eomcs.basic.ex06;

import java.util.concurrent.ArrayBlockingQueue;

public class Exam0110 {

  public static void main(String[] args) {
    String s1 = new String("aaa");
    String s2 = new String("bbb");
    String s3 = new String("ccc");
    String s4 = new String("ddd");
    String s5 = new String("eee");

    ArrayBlockingQueue queue = new ArrayBlockingQueue(10);
    queue.offer(s1); // aaa,
    queue.offer(s2); // aaa, bbb,
    queue.offer(s3); // aaa, bbb, ccc,
    print(queue);

    // Queue
    // - FIFO(First In First Out) 방식으로 데이터를 꺼낸다.
    // 
    System.out.println("==>" + queue.poll()); // bbb, ccc,
    System.out.println("==>" + queue.poll()); // ccc,
    print(queue);

    queue.offer(s4); // ccc, ddd,
    queue.offer(s5); // ccc, ddd, eee,
    print(queue);

    System.out.println("------------------------");

    String value;
    while ((value = (String) queue.poll()) != null) {
      System.out.println(value);
    }
  }

  static void print(ArrayBlockingQueue queue) {
    Object[] arr = queue.toArray();
    for (int i = 0; i < arr.length; i++) {
      System.out.print(arr[i] + ", ");
    }
    System.out.println();
  }
}






















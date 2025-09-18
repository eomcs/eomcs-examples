// Iterator 의 사용
package com.eomcs.basic.ex07;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

public class Exam0224 {
  public static void main(String[] args) {

    ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(100);
    queue.offer("aaa");
    queue.offer("bbb");
    queue.offer("ccc");
    queue.offer("ddd");
    queue.offer("eee");

    Iterator<String> 컬렉션에서값을꺼내주는객체 = queue.iterator();

    while (컬렉션에서값을꺼내주는객체.hasNext()) {
      System.out.print(컬렉션에서값을꺼내주는객체.next() + ", ");
    }
    System.out.println();

  }
}

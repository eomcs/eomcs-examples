package com.eomcs.oop.ex11.overview.step3;

public class Test3 {
  public static void main(String[] args) {
    MyQueue myQueue = new MyQueue();
    myQueue.offer("홍길동");
    myQueue.offer("임꺽정");
    myQueue.offer("유관순");
    myQueue.offer("안중근");
    myQueue.offer("윤봉길");
    myQueue.offer("김구");

    Iterator iterator = myQueue.iterator();
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }
  }
}

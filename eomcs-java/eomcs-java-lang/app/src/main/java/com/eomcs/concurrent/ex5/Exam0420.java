// sychronized 스태틱 메서드 - 적용 후
package com.eomcs.concurrent.ex5;

public class Exam0420 {
  public static void main(String[] args) {
    Worker w1 = new Worker("홍길동");
    Worker w2 = new Worker("임꺽정");

    w1.start();
    w2.start();

  }

  synchronized static void play(String threadName) throws Exception {
    System.out.println(threadName);
    Thread.sleep(10000);
  }

  static class Worker extends Thread {
    public Worker(String name) {
      super(name);
    }

    @Override
    public void run() {
      try {
        play(getName());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}

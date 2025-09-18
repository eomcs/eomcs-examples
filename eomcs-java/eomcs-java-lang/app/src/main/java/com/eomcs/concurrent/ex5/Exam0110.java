// 멀티 스레딩(비동기 프로그래밍)의 문제점 - 사례 1 
package com.eomcs.concurrent.ex5;

public class Exam0110 {

  static class MyList {
    int[] values = new int[100];
    int size;

    public void add(int value) {
      // 여러 스레드가 동시에 이 메서드에 진입하면 
      // 배열의 값을 덮어쓰는 문제가 발생한다.
      // 이렇게 여러 스레드가 동시에 접근했을 때 문제가 발생하는 코드 부분을
      // "Critical Section" 또는 "Critical Region" 이라 부른다.
      if (size >= values.length) {
        delay();
        return;
      }
      delay();
      values[size] = value;
      delay();
      size = size + 1;
      delay();
    }

    public void print() {
      for (int i = 0; i < size; i++) {
        System.out.printf("%d:  %d\n", i, values[i]);
      }
    }

    public void delay() {
      int count = (int)(Math.random() * 1000);
      for (int i = 0; i < count; i++) {
        Math.atan(34.1234);
      }
    }
  }

  static class Worker extends Thread {
    MyList list;
    int value;

    public Worker(MyList list, int value) {
      this.list = list;
      this.value =  value;
    }

    @Override
    public void run() {
      for (int i = 0; i < 20; i++) {
        list.add(value);
      }
    }
  }

  public static void main(String[] args) throws Exception {
    MyList list = new MyList();

    Worker w1 = new Worker(list, 111);
    Worker w2 = new Worker(list, 222);
    Worker w3 = new Worker(list, 333);

    w1.start();
    w2.start();
    w3.start();

    Thread.sleep(10000);

    list.print();
  }

}

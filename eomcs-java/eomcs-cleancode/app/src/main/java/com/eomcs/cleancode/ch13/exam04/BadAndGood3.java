package com.eomcs.cleancode.ch13.exam04;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// 예제 3: 생산자-소비자 큐 - LinkedList 기반 Queue는 thread-safe하지 않다
public class BadAndGood3 {

  private BadAndGood3() {}

  static class Job {

    private final String name;

    Job(String name) {
      this.name = name;
    }

    String getName() {
      return name;
    }
  }

  // Bad: LinkedList 기반 Queue는 thread-safe하지 않다
  //   - 생산자 스레드와 소비자 스레드가 동시에 접근하면 안전하지 않다
  //   - 큐가 비었을 때 기다리는 기능이 없어 직접 wait()/notify()를 작성해야 한다
  static class BadJobQueue {

    private final Queue<Job> queue = new LinkedList<>();

    public void add(Job job) {
      queue.add(job);
    }

    public Job take() {
      return queue.poll();
    }
  }

  // Good: BlockingQueue는 생산자-소비자 구조에 적합하다
  //   - 큐가 비어 있으면 take()가 자동으로 기다린다
  //   - 직접 wait() / notify()를 작성하지 않아도 된다
  //   - thread-safe하게 설계되어 있다
  static class JobQueue {

    private final BlockingQueue<Job> queue = new LinkedBlockingQueue<>();

    public void add(Job job) {
      queue.add(job);
    }

    public Job take() throws InterruptedException {
      return queue.take();
    }
  }
}

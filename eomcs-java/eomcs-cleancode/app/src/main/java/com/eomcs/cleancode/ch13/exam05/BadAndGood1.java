package com.eomcs.cleancode.ch13.exam05;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// 예제 1: Producer-Consumer - 생산자는 작업을 만들고, 소비자는 작업을 처리한다
public class BadAndGood1 {

  private BadAndGood1() {}

  static class Job {

    private final String name;

    Job(String name) {
      this.name = name;
    }

    void execute() {
      System.out.println("execute: " + name);
    }
  }

  // Bad: LinkedList는 thread-safe하지 않다
  //   - 생산자와 소비자가 동시에 접근하면 안전하지 않다
  //   - 큐가 비었을 때 기다리지 않아 직접 wait() / notify()를 작성해야 한다
  static class BadJobQueue {

    private final Queue<Job> queue = new LinkedList<>();

    public void add(Job job) {
      queue.add(job);
    }

    public Job take() {
      return queue.poll();
    }
  }

  // Good: Producer는 작업을 만들어 BlockingQueue에 넣는다
  //   - 큐가 가득 차면 put()이 자동으로 대기한다
  static class Producer implements Runnable {

    private final BlockingQueue<Job> queue;

    Producer(BlockingQueue<Job> queue) {
      this.queue = queue;
    }

    @Override
    public void run() {
      try {
        for (int i = 1; i <= 5; i++) {
          queue.put(new Job("job-" + i));
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  // Good: Consumer는 BlockingQueue에서 작업을 꺼내 처리한다
  //   - 큐가 비어 있으면 take()가 자동으로 대기한다
  //   - 직접 wait() / notify()를 작성하지 않아도 된다
  static class Consumer implements Runnable {

    private final BlockingQueue<Job> queue;

    Consumer(BlockingQueue<Job> queue) {
      this.queue = queue;
    }

    @Override
    public void run() {
      try {
        while (true) {
          Job job = queue.take();
          job.execute();
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  // Good: BlockingQueue가 생산자와 소비자 사이를 조정한다
  //   - ArrayBlockingQueue(10): 큐 크기가 10으로 제한된 Bound Resource
  //   - 큐가 가득 차면 put()은 기다린다
  //   - 큐가 비면 take()는 기다린다
  static class ProducerConsumerExample {

    public void run() {
      BlockingQueue<Job> queue = new ArrayBlockingQueue<>(10);

      new Thread(new Producer(queue)).start();
      new Thread(new Consumer(queue)).start();
    }
  }
}

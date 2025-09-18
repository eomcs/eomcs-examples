// "system" 스레드 그룹에 소속된 스레드들
package com.eomcs.concurrent.ex2;

public class Exam0170 {

  public static void main(String[] args) {
    Thread main = Thread.currentThread();
    ThreadGroup mainGroup = main.getThreadGroup();
    ThreadGroup systemGroup = mainGroup.getParent();

    Thread[] arr = new Thread[100];
    int count = systemGroup.enumerate(arr, false);

    System.out.println("system 스레드 그룹에 소속된 스레드들:");
    for (int i = 0; i < count; i++) {
      System.out.println("   =>" + arr[i].getName());
    }
  }
}

// JVM의 스레드 계층도:
// system(TG)
// => Reference Handler(T)
// => Finalizer(T)
// => Signal Dispatcher(T)
// => Attach Listener(T)
// => Notification Thread(T)
// => main(TG)
// ...=> main(T) : main() 메서드를 호출한다.
// => InnocuousThreadGroup(TG)

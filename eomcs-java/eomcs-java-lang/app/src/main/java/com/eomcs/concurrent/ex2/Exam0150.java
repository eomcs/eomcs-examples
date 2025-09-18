// 스레드 그룹의 부모 그룹
package com.eomcs.concurrent.ex2;

public class Exam0150 {

  public static void main(String[] args) {
    Thread main = Thread.currentThread();
    ThreadGroup mainGroup = main.getThreadGroup();

    // 스레드 그룹의 부모 그룹을 알고 싶다면?
    ThreadGroup parentGroup = mainGroup.getParent();
    System.out.printf("main 스레드 그룹의 부모: %s\n", parentGroup.getName());


    // "system" 그룹의 부모 그룹은?
    ThreadGroup grandparentGroup = parentGroup.getParent();
    if (grandparentGroup != null) {
      System.out.printf("%s 스레드 그룹의 부모: %s\n", 
          parentGroup.getName(), 
          grandparentGroup.getName());
    }
  }
}

// JVM의 스레드 계층도:
// system(TG)
// => main(TG)
// ...=> main(T)

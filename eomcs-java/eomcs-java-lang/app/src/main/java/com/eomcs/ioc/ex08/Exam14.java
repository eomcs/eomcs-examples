// IoC 컨테이너 만들기 - 의존 객체 자동 주입
package com.eomcs.ioc.ex08;

public class Exam14 {
  public Exam14() {
    System.out.println("===> Exam14()");
  }

  public static void main(String[] args) throws Exception {
    ApplicationContext9 iocContainer = new ApplicationContext9("step19.ex8");
    Car2 car2 = (Car2) iocContainer.getBean("step19.ex8.Car2");
    car2.move();
  }
}







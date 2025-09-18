// IoC 컨테이너 만들기 - 5) 클래스 이름으로 Class 객체를 만든다.
package com.eomcs.ioc.ex05;

import java.io.File;
import java.util.List;

// => 클래스 이름으로 해당 클래스를 로딩한 후 Class 객체를 가져온다.
//    왜? Class 객체가 있으면 언제라도 인스턴스를 생성할 수 있기 때문이다.
public class Exam08 {

  public static void main(String[] args) throws Exception {
    ApplicationContext6 appContext = new ApplicationContext6("step19.ex1");
    List<Class> classes = appContext.getClasses();
    for (Class clazz : classes) {
      System.out.println(clazz.getName());
    }
  }



}






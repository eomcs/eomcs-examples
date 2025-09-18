// 프로퍼티 에디터 - String ==> java.sql.Date ; 변환기 사용하여 처리하기
package com.eomcs.spring.ioc.ex07.c;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.eomcs.spring.ioc.SpringUtils;

public class Exam01 {

  public static void main(String[] args) {
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex07/c/application-context.xml");

    SpringUtils.printBeanList(iocContainer);

    System.out.println(iocContainer.getBean("c1"));

    // IoC 컨테이너는 application-context.xml 에 설정된 대로 다음과 같이 객체를 만든다.
    /*
    Car c1 = new Car();
    c1.setModel("티코");
    c1.setMaker("비트자동차");
    c1.setCc(Integer.valueOf("890"));
    c1.setAuto(Boolean.valueOf("true"));

    CustomDateEditor cde = new CustomDateEditor();
    cde.setAsText("2021-6-1");

    c1.setCreatedDate((Date)cde.getValue());

    System.out.println(c1);
     */
  }
}



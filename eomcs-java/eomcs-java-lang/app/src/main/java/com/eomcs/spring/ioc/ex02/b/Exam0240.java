// 객체 생성 - bean의 별명을 알아내기: 별명만 여러 개인 경우
package com.eomcs.spring.ioc.ex02.b;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.eomcs.spring.ioc.SpringUtils;

public class Exam0240 {

  public static void main(String[] args) {
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex02/b/application-context.xml");

    // 빈의 id와 클래스명을 출력하기
    SpringUtils.printBeanList(iocContainer);

    String[] aliases = iocContainer.getAliases("c91");
    System.out.println("[별명]");
    for (String alias : aliases) {
      System.out.println(alias);
    }
    // 첫 번째 별명이 id로 사용된다.
    // 나머지 별명이 별명으로 사용된다.
  }

}



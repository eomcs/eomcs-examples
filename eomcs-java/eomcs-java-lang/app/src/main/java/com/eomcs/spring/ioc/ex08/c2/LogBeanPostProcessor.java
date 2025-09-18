package com.eomcs.spring.ioc.ex08.c2;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

// 역할:
// => IoC 컨테이너가 빈을 생성할 때 마다 로그를 출력한다.
//
public class LogBeanPostProcessor implements BeanPostProcessor {

  static {
    System.out.println("LogBeanPostProcessor 클래스 로딩!");
  }

  public LogBeanPostProcessor() {
    System.out.println("LogBeanPostProcessor()");
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    System.out.printf("===> %s:%s\n",
        beanName,
        bean.getClass().getName());
    return null;
  }
}

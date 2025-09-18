package com.eomcs.spring.ioc.ex08.c1;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

// Spring IoC 컨테이너에 새 기능을 추가하는 예:
// => 새 기능이 IoC 컨테이너가 생성한 객체를 사용해야 한다면,
//    객체 생성 후에 그 작업을 수행하면 된다.
// => 이렇게 개발자가 컨테이너의 기능을 확장할 수 있도록
//    BeanPostProcessor라는 규칙을 제공한다.
//
// 즉 빈 생성 후에 어떤 작업을 수행할 객체를 만들고 싶다면?
// => BeanPostProcessor 규칙에 따라 클래스를 만들라!
// => 옵저버 디자인 패턴이 적용된 대표적인 예이다.
//
// BeanPostProcessor
// => 스프링 IoC 컨테이너는 빈을 생성하고 세터를 모두 호출한 난 후
//    이 구현체에게 보고한다.
//
public class MyBeanPostProcessor implements BeanPostProcessor {

  public MyBeanPostProcessor() {
    System.out.println("MyBeanPostProcessor()");
  }

  @Override
  public Object postProcessBeforeInitialization( 
      Object bean, String beanName) throws BeansException {
    // XML 설정에서 init-method 속성에 지정된 메서드가 호출되기 전에
    // 이 메서드가 먼저 호출된다.
    //
    System.out.println("postProcessBeforeInitialization()");
    System.out.printf("    => %s : %s\n", //
        beanName, //
        bean.getClass().getName());
    System.out.printf("    => %s\n", bean.toString());
    return bean;
  }


  @Override
  public Object postProcessAfterInitialization(
      Object bean, String beanName) throws BeansException {
    // XML 설정에서 init-method 속성에 지정된 메서드가 호출된 후에
    // 이 메서드가 호출된다.
    //
    System.out.println("postProcessAfterInitialization()");
    System.out.printf("    => %s : %s\n", //
        beanName, //
        bean.getClass().getName());
    System.out.printf("    => %s\n", bean.toString());
    return bean;
  }

}

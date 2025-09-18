package com.eomcs.spring.ioc;

import org.springframework.context.ApplicationContext;

public class SpringUtils {

  public static void printBeanList(ApplicationContext iocContainer) {
    // IoC 컨테이너에 들어있는 객체 알아내기
    // 빈(bean) = 객체(object) = 인스턴스(instance)
    // '자바 객체 생성 규칙'에 따라 만든 인스턴스를 부르는 말이었다.
    // 그러나 일반적으로 객체를 부를 때도 '빈'이라는 용어를 사용한다.
    System.out.println("--------------------------------");
    int count = iocContainer.getBeanDefinitionCount();
    System.out.printf("빈 개수: %d\n", count);

    String[] beanNames = iocContainer.getBeanDefinitionNames();
    for (String name : beanNames) {
      System.out.printf("%s = %s\n",
          name, iocContainer.getBean(name).getClass().getName());
    }
    System.out.println("--------------------------------");
  }

  public static void printBeanAliases(
      ApplicationContext iocContainer, String beanName) {
    System.out.printf("['%s' 빈의 별명 목록]\n", beanName);
    String[] aliases = iocContainer.getAliases(beanName);
    for (String alias : aliases) {
      System.out.println("  => " + alias);
    }
  }

  public static void printBeanNames(ApplicationContext iocContainer) {
    System.out.println("[생성된 빈의 이름 목록]");
    String[] names = iocContainer.getBeanDefinitionNames();
    for (String name : names) {
      System.out.println(name);
    }
    System.out.println("-----------------------------------");
  }
}








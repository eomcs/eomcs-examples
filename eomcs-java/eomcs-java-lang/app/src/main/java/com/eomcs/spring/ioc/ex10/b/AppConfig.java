package com.eomcs.spring.ioc.ex10.b;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.eomcs.spring.ioc.ex10.Car;

//@Configuration
// AppConfig 클래스가 스프링 설정 정보를 갖고 있는 클래스임을 선포한다!
// 그러면 AnnotationConfigApplicationContext 에서
// 이 클래스를 찾아 적절한 작업을 수행할 것이다.
//
// => AnnotationConfigApplicationContext 컨테이너에
// Java config 클래스를 직접 지정할 경우에는
// 굳이 @Configuration 애노테이션을 붙일 필요가 없다.
// 예) ApplicationContext iocContainer =
//       new AnnotationConfigApplicationContext(AppConfig1.class);
//
// => 그런데 다음과 같이 컨테이너에
// Java config 클래스를 직접 알려주지 않을 경우에는,
// 예) ApplicationContext iocContainer =
//       new AnnotationConfigApplicationContext("com.eomcs.spring.ioc.ex10");
//
// 이 클래스가 Java config 클래스임을 표시해야만 컨테이너가 알 수 있다.
// Java config 클래스임을 표시할 때 붙이는 애노테이션이
// 바로 @Configuration 이다.
//
@Configuration
public class AppConfig {
  @Bean
  public Car car2() {
    Car c = new Car(null);
    c.setMaker("비트자동차");
    c.setModel("티코");
    c.setCc(890);
    c.setAuto(true);
    return c;
  }

  @Bean
  public Car car3() {
    Car c = new Car(null);
    c.setMaker("비트자동차");
    c.setModel("티코");
    c.setCc(890);
    c.setAuto(true);
    return c;
  }

}



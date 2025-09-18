package com.eomcs.spring.ioc.ex10.a;

import org.springframework.context.annotation.Bean;
import com.eomcs.spring.ioc.ex10.Car;

public class AppConfig {

  // @Component와 같은 애노테이션을 사용할 수 없는 경우
  // Java Config 에서 수동으로 객체를 생성할 수 있다.
  // 방법:
  // 1) 객체를 생성하여 리턴하는 메서드를 정의한다.
  // 2) 그리고 그 메서드에 @Bean 애노테이션을 붙인다.
  //
  // @Bean 애노테이션을 붙이면,
  // 스프링 IoC 컨테이너(AnnotationConfigApplicationContext)는
  // 해당 메서드를 호출하고,
  // 그 메서드가 리턴한 객체를 컨테이너에 보관한다.
  // 컨테이너에 보관할 때 사용할 객체 이름은
  // @Bean(객체이름) 애노테이션에 설정된 이름을 사용한다.
  // 만약 @Bean 애노테이션에 이름이 없으면,
  // 메서드 이름을 객체 이름으로 사용한다.
  //
  @Bean("car") // 애노케이션에 지정한 이름으로 리턴 값을 보관한다.
  public Car getCar2() {
    Car c = new Car(null);
    c.setMaker("비트자동차");
    c.setModel("티코");
    c.setCc(890);
    c.setAuto(true);
    return c;
  }

  @Bean // 이름을 지정하지 않으면 메서드 이름을 사용하여 저장한다.
  public Car getCar() {
    Car c = new Car(null);
    c.setMaker("비트자동차");
    c.setModel("티코");
    c.setCc(890);
    c.setAuto(true);
    return c;
  }

  // 실무에서는 스프링 설정용으로 사용하는 클래스에서
  // 객체를 리턴하는 메서드를 만들 때
  // 그 메서드의 이름을 객체 이름과 같게 짓는다.
  // => 보통 어떤 값을 리턴할 때는 getXxx()라는 이름으로 메서드를 만드는데,
  //    이처럼 객체이름으로 사용할 수 있도록 메서드를 만드는 경우도 있으니
  //    당황하지 말라!
  @Bean
  public Car car2() {
    Car c = new Car(null);
    c.setMaker("비트자동차");
    c.setModel("티코");
    c.setCc(890);
    c.setAuto(true);
    return c;
  }
}



package com.eomcs.spring.ioc.ex06.d;

import org.springframework.beans.factory.FactoryBean;
import com.eomcs.spring.ioc.ex06.Car;

// 스프링 IoC 컨테이너가 정한 규칙에 따라 공장 클래스를 만들면,
// 구현할 때 복잡한 면이 있다.
// 하지만 빈 생성을 설정할 때는 기존 방식 보다 쉽다.
//
// 스프링에서 공장 클래스를 만들 때 제안한 규칙?
// => org.springframework.beans.factory.FactoryBean 인터페이스
//
public class CarFactory implements FactoryBean<Car> {

  String model;

  public CarFactory() {
    System.out.println("CarFactory() 생성자 호출됨.");
  }

  public void setModel(String model) {
    System.out.println("CarFactory.setModel() 호출됨.");
    this.model = model;
  }

  @Override
  public Car getObject() throws Exception {
    System.out.println("CarFactory.getObject() 호출됨.");
    // 객체를 생성해서 리턴하는 팩토리 메서드이다.
    // 스프링 IoC 컨테이너는 이 메서드를 호출할 것이다.
    // 이 방식으로는 객체를 생성할 때 추가적으로 필요한 값을 파라미터로 받을 수 없기 때문에
    // 프로퍼티로 받도록 해야 한다.
    Car c = new Car();
    switch (model) {
      case "티코":
        c.setMaker("대우자동차");
        c.setModel("Tico");
        c.setCc(890);
        return c;
      case "소나타":
        c.setMaker("현대자동차");
        c.setModel("Sonata");
        c.setCc(1980);
        return c;
      case "SM5":
        c.setMaker("르노삼성자동차");
        c.setMaker("SM5");
        c.setCc(1990);
        return c;
      default:
        c.setMaker("비트자동차");
        c.setModel("자바휘웅");
        c.setCc(5000);
        return c;
    }
  }

  @Override
  public Class<?> getObjectType() {
    // getObject()가 생성해주는 객체의 타입 정보를 리턴한다.
    // 이 메서드는 Spring IoC 컨테이너가 타입으로 객체를 찾을 때 사용한다.
    System.out.println("CarFactory.getObjectType() 호출됨.");
    return Car.class;
  }
}



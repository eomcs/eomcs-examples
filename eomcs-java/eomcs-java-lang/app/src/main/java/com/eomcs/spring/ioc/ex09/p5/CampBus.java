package com.eomcs.spring.ioc.ex09.p5;

import org.springframework.stereotype.Component;
import com.eomcs.spring.ioc.ex09.Car;
import com.eomcs.spring.ioc.ex09.Engine;

@Component
public class CampBus extends Car {

  public CampBus(Engine engine) {
    super(engine);
  }
}

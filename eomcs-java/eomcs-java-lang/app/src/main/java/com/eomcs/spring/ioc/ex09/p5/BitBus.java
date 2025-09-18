package com.eomcs.spring.ioc.ex09.p5;

import org.springframework.stereotype.Component;
import com.eomcs.spring.ioc.ex09.Car;
import com.eomcs.spring.ioc.ex09.Engine;

@Component
public class BitBus extends Car {
  public BitBus(Engine engine) {
    super(engine);
  }
}

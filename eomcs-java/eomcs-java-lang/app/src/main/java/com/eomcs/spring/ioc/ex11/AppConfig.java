package com.eomcs.spring.ioc.ex11;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;


// @ComponentScan
// => <context:component-scan/> 태그와 같은 일을 한다.
//
// 사용법1:
// => 한 개의 패키지를 지정하기
//@ComponentScan(basePackages = {"com.eomcs.spring.ioc.ex11"})

// => 배열 항목이 한 개일 경우 중괄호({}) 생략 가능
//@ComponentScan(basePackages = "com.eomcs.spring.ioc.ex11")

// 사용법2:
// => 여러 개의 패키지 지정하기
//@ComponentScan(basePackages = {
//    "com.eomcs.spring.ioc.ex11.p1",
//    "com.eomcs.spring.ioc.ex11.p2",
//    "com.eomcs.spring.ioc.ex11.p3"
//})

// 사용법3:
// => 특정 패키지나 클래스 제외하기
@ComponentScan(
    basePackages = "com.eomcs.spring.ioc.ex11",
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = "com.eomcs.spring.ioc.ex11.p2.*"
            ),
        @ComponentScan.Filter(
            type = FilterType.ANNOTATION,
            value = org.springframework.stereotype.Controller.class
            )
    })
// 위의 설정은 XML에서 다음과 같다.
//<context:component-scan base-package="com.eomcs.spring.ioc.ex11">
//  <context:exclude-filter type="regex"
//    expression="com.eomcs.spring.ioc.ex11.p2.*"/>
//  <context:exclude-filter type="annotation"
//    expression="org.springframework.stereotype.Controller"/>
//</context:component-scan>


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
}



package com.eomcs.spring.ioc.ex10.c;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import com.eomcs.spring.ioc.ex10.Car;

@Configuration

// @PropertySource 애노테이션
// => .properties 파일을 데이터를 메모리에 로딩하는 일을 한다.
// => 파일 경로가 클래스 경로를 가리킨다면,
//    파일 경로 앞에 "classpath:" 접두어를 붙여라.
@PropertySource({
  "classpath:com/eomcs/spring/ioc/ex10/c/jdbc.properties",
  "classpath:com/eomcs/spring/ioc/ex10/c/jdbc2.properties"
})
public class AppConfig {

  // @PropertySource를 통해 로딩된 프로퍼티 값을 사용하고 싶다면
  // Environment 타입의 객체를 주입 받아라!
  @Autowired
  Environment env;  // Spring IoC 컨테이너는 Environment 객체를 주입해 준다.

  // @PropertySource를 통해 로딩된 프로퍼티 값 중에서
  // 특정 값만 필드로 주입 받을 수 있다.
  // => 필드 선언에 @Value 애노테이션을 붙인다.
  // => @Value("${프로퍼티이름}")
  @Value("${jdbc.url}")
  String jdbcUrl;

  @Value("${jdbc2.url}")
  String jdbc2Url;

  // Environment 객체를 통해 메모리에 보관된 .properties 의 값 가져오기
  @Bean
  public Car car1() {
    System.out.println("car1() 호출: ");
    System.out.println("  " + this.env.getProperty("jdbc.username"));
    System.out.println("  " + this.env.getProperty("jdbc2.username"));
    return new Car(null);
  }

  // @Value를 통해 필드로 주입 받은 .properties 값 꺼내기
  @Bean
  public Car car2() {
    System.out.println("car2() 호출: ");
    System.out.println("  " + this.jdbcUrl);
    System.out.println("  " + this.jdbc2Url);
    return new Car(null);
  }

  // @Value를 사용하여 파라미터로 .properties 값 주입 받기
  @Bean
  public Car car3(
      @Value("${jdbc.username}") String username,
      @Value("${jdbc2.username}") String username2) {

    System.out.println("car3() 호출 :");
    System.out.println("  " + username);
    System.out.println("  " + username2);
    return new Car(null);
  }

}


